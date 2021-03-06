package com.mailit.mailer.impl;

import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * SendgridMailer: Mailer implementation for the Sendgrid mail provider
 * @author Travis McChesney
 */
public class SendgridMailer implements Mailer {
    private HttpRequestWithBody client;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String url = "https://api.sendgrid.com/v3/mail/send";

    public SendgridMailer(HttpRequestWithBody client) {
        this.client = client;
    }

    public SendgridMailer(String apiKey) {
        this(Unirest.post(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey));
    }

    @Override
    public String getName() {
        return "Sendgrid";
    }

    @Override
    public void mail(Mail mail) {
        logger.debug("Mailing with Sendgrid");

        sendIt(mail);

        logger.debug("Mailed with Sendgrid");
    }

    @Override
    public boolean isHealthy() {
        return true;
    }

    /**
     * Send the email
     * @param mail The Mail to be sent
     */
    private void sendIt(Mail mail) {
        try {
            HttpResponse r = client
                    .body(toMap(mail))
                    .asJson();

            if (!Response.Status.Family.familyOf(r.getStatus()).equals(Response.Status.Family.SUCCESSFUL)) {
                throw new WebApplicationException(String.format("Could not send email: %s", r.getBody().toString()),
                        Response.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (UnirestException e) {
            logger.error("Error sending email", e);
            throw new WebApplicationException("Could not send email", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Convert a mail object to the Map of params required for Sendgrid. Let's just say JSON building isn't Java's
     * strong suit.
     * @param mail The Mail object to convert
     * @return A Map that can be used for input to Sendgrid's API
     */
    private Map<String, Object> toMap(Mail mail) {
        return ImmutableMap.<String, Object>builder()
                .put("personalizations", ImmutableList.<Map<String, Object>>builder()
                        .add(ImmutableMap.<String, Object>builder()
                                .put("to", ImmutableList.<Map<String, Object>>builder()
                                        .add(ImmutableMap.<String, Object>builder()
                                                .put("email", mail.getTo())
                                                .put("name", mail.getToName())
                                                .build())
                                        .build())
                                .put("subject", mail.getSubject())
                                .build())
                        .build())
                .put("from", ImmutableMap.<String, Object>builder()
                        .put("email", mail.getFrom())
                        .put("name", mail.getFromName())
                        .build())
                .put("content", ImmutableList.<Map<String, Object>>builder()
                        .add(ImmutableMap.<String, Object>builder()
                                .put("type", "text/plain")
                                .put("value", mail.getPlainBody())
                                .build())
                        .build())
                .build();
    }
}
