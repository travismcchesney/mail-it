package com.mailit;

import com.mailit.health.ProviderHealthCheck;
import com.mailit.mailer.Mailer;
import com.mailit.mailer.impl.MailgunMailer;
import com.mailit.mailer.impl.SendgridMailer;
import com.mailit.resources.MailItResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MailItApplication extends Application<MailItConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MailItApplication().run(args);
    }

    @Override
    public String getName() {
        return "mailit";
    }

    @Override
    public void initialize(final Bootstrap<MailItConfiguration> bootstrap) {
        // No initialization needed
    }

    @Override
    public void run(final MailItConfiguration config,
                    final Environment environment) {
        Mailer mailer = getMailer(config.getMailProvider(), config);

        final MailItResource resource = new MailItResource(mailer);
        final ProviderHealthCheck healthCheck = new ProviderHealthCheck(mailer);

        environment.jersey().register(resource);
        environment.healthChecks().register("provider", healthCheck);
    }

    // New up a Mailer based on the provided configuration
    private static Mailer getMailer(String mailProvider, MailItConfiguration config) {
        switch (mailProvider) {
            case "mailgun": return new MailgunMailer(config.getMailgunApiKey());
            default:
                // default to sendgrid as our mail provider
                return new SendgridMailer(config.getSendgridApiKey());
        }
    }
}
