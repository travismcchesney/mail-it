package com.mailit.mailer.impl;

import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * SpendgridMailer: Mailer implementation for the Spendgrid mail provider
 * @author Travis McChesney
 */
public class SpendgridMailer implements Mailer {
    private final com.mashape.unirest.request.HttpRequestWithBody client;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String url = "https://bw-interview-emails.herokuapp.com/spendgrid/send_email";

    public SpendgridMailer(HttpRequestWithBody client) {
        this.client = client;
    }

    public SpendgridMailer(String apiKey) {
        this(Unirest.post(url)
                .header("Content-Type", "application/json")
                .header("X-Api-Key", apiKey));
    }

    @Override
    public String getName() {
        return "Spendgrid";
    }

    @Override
    public void mail(Mail mail) {
        sendIt(mail);
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
     * Convert a mail object to the Map of params required for Spendgrid. Let's just say JSON building isn't Java's
     * strong suit.
     * @param mail The Mail object to convert
     * @return A Map that can be used for input to Spendgrid's API
     */
    private Map<String, Object> toMap(Mail mail) {
        return ImmutableMap.<String, Object>builder()
                .put("recipient", mail.getEmailTo())
                .put("sender", mail.getEmailFrom())
                .put("subject", mail.getSubject())
                .put("body", mail.getPlainBody())
                .build();
    }
}
