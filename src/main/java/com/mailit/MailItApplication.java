package com.mailit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mailit.enums.Provider;
import com.mailit.health.ProviderHealthCheck;
import com.mailit.mailer.Mailer;
import com.mailit.mailer.MailerFactory;
import com.mailit.resources.MailItResource;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import java.io.IOException;

/**
 * MailItApplication: The main application entry point class
 * @author Travis McChesney
 */
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
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new SwaggerBundle<MailItConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(MailItConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });

        // Bestow object mapping capabilities to the Unirest client
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void run(final MailItConfiguration config,
                    final Environment environment) {
        Mailer mailer = MailerFactory.getMailer(Provider.fromString(config.getMailProvider()), config);

        final MailItResource resource = new MailItResource(mailer);
        final ProviderHealthCheck healthCheck = new ProviderHealthCheck(mailer);

        environment.jersey().register(resource);
        environment.healthChecks().register("provider", healthCheck);
    }

}
