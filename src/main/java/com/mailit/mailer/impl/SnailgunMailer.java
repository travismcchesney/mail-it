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
 * SnailGunMailer: Mailer implementation for the Snailgun mail provider
 * @author Travis McChesney
 */
public class SnailgunMailer implements Mailer {
    private final com.mashape.unirest.request.HttpRequestWithBody client;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String url = "https://bw-interview-emails.herokuapp.com/snailgun/emails";

    public SnailgunMailer(HttpRequestWithBody client) {
        this.client = client;
    }

    public SnailgunMailer(String apiKey) {
        this(Unirest.post(url)
                .header("Content-Type", "application/json")
                .header("X-Api-Key", apiKey));
    }

    @Override
    public String getName() {
        return "Snailgun";
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
     * Convert a mail object to the Map of params required for Snailgun. Let's just say JSON building isn't Java's
     * strong suit.
     * @param mail The Mail object to convert
     * @return A Map that can be used for input to Snailgun's API
     */
    private Map<String, Object> toMap(Mail mail) {
        return ImmutableMap.<String, Object>builder()
                .put("from_email", mail.getFrom())
                .put("from_name", mail.getFromName())
                .put("to_email", mail.getTo())
                .put("to_name", mail.getToName())
                .put("subject", mail.getSubject())
                .put("body", mail.getPlainBody())
                .build();
    }
}
