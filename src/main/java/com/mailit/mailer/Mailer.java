package com.mailit.mailer;

import com.mailit.api.Mail;

/**
 * Mailer: Interface for an email provider
 * @author Travis McChesney
 */
public interface Mailer {
    // Get the name of the provider
    String getName();

    // Send an email
    void mail(Mail mail);

    // Check the health of the service
    boolean isHealthy();
}
