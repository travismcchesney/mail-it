package com.mailit.mailer;

import com.mailit.MailItConfiguration;
import com.mailit.enums.Provider;
import com.mailit.mailer.impl.MailgunMailer;
import com.mailit.mailer.impl.SendgridMailer;
import com.mailit.mailer.impl.SpendgridMailer;

/**
 * MailerFactory: Create new Mailers based on the requested service provider
 * @author Travis McChesney
 */
public class MailerFactory {
    public static Mailer getMailer(Provider provider, MailItConfiguration config) {
        switch (provider) {
            case MAILGUN: return new MailgunMailer(config.getMailgunApiKey());
            case SPENDGRID: return new SpendgridMailer(config.getSpendgridApiKey());
            default:
                // default to sendgrid as our mail provider
                return new SendgridMailer(config.getSendgridApiKey());
        }
    }
}
