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
    private String spendgridApiKey;
    private String snailgunApiKey;

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
    
    @JsonProperty
    public String getSpendgridApiKey() {
        return spendgridApiKey;
    }

    @JsonProperty
    public String getSnailgunApiKey() {
        return snailgunApiKey;
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
