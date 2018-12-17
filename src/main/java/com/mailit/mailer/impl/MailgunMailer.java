package com.mailit.mailer.impl;

import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * MailGunMailer: Mailer implementation for the Mailgun mail provider
 * @author Travis McChesney
 */
public class MailgunMailer implements Mailer {
    private final com.mashape.unirest.request.HttpRequestWithBody client;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String url = "https://api.mailgun.net/v3/mail.travis.technology/messages";

    public MailgunMailer(HttpRequestWithBody client) {
        this.client = client;
    }

    public MailgunMailer(String apiKey) {
        this(Unirest.post(url)
                .header("accept", "application/json")
                .basicAuth("api", apiKey));
    }

    @Override
    public String getName() {
        return "Mailgun";
    }

    @Override
    public void mail(Mail mail) {
        logger.debug("Mailing with Mailgun");

        sendIt(mail);

        logger.debug("Mailed with Mailgun");
    }

    @Override
    public boolean isHealthy() {
        return true;
    }

    private void sendIt(Mail mail) {
        try {
            HttpResponse<JsonNode> resp = client
                    .field("to", mail.getEmailTo())
                    .field("from", mail.getEmailTo())
                    .field("subject", mail.getSubject())
                    .field("text", mail.getPlainBody())
                    .asJson();
        } catch (UnirestException e) {
            throw new WebApplicationException("Could not send email", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
