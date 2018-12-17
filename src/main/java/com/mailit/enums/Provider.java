package com.mailit.enums;

/**
 * Provider: Enumeration of that valid, supported email providers.
 * @author Travis McChesney
 */
public enum Provider {
    SENDGRID("sendgrid"),
    MAILGUN("mailgun");

    private String provider;

    Provider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public static Provider fromString(String provider) {
        for (Provider p : Provider.values()) {
            if (p.getProvider().equalsIgnoreCase(provider)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return provider;
    }
}
