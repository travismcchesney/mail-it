package com.mailit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Configuration: POJO representing the application's configuration.
 * @author Travis McChesney
 */
public class MailItConfiguration extends Configuration {
    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    private String mailProvider;
    private String sendgridApiKey;
    private String mailgunApiKey;

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @JsonProperty
    public String getMailProvider() {
        return mailProvider;
    }

    @JsonProperty
    public String getSendgridApiKey() {
        return sendgridApiKey;
    }

    @JsonProperty
    public String getMailgunApiKey() {
        return mailgunApiKey;
    }

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("jerseyClient")
    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
        this.jerseyClient = jerseyClient;
    }
}
