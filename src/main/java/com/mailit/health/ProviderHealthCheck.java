package com.mailit.health;

import com.codahale.metrics.health.HealthCheck;
import com.mailit.mailer.Mailer;

/**
 * ProviderHealthCheck: A health check implementation to retrieve the health of the configured provider.
 * @author Travis McChesney
 */
public class ProviderHealthCheck extends HealthCheck {
    private final Mailer mailer;

    public ProviderHealthCheck(Mailer mailer) {
        this.mailer = mailer;
    }

    @Override
    protected Result check() throws Exception {
        if (!mailer.isHealthy()) {
            return HealthCheck.Result.unhealthy(
                    String.format("The %s mail provider is unhealthy", mailer.getName()));
        }

        return HealthCheck.Result.healthy();
    }
}
