package com.mailit.mailer.impl;

import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;

/**
 * SendgridMailer: Mailer implementation for the Sendgrid mail provider
 * @author Travis McChesney
 */
public class SendgridMailer implements Mailer {
    private String apiKey;

    public SendgridMailer(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getName() {
        return "Sendgrid";
    }

    @Override
    public void mail(Mail mail) {
        System.out.println("Mailed using Sendgrid");
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}
