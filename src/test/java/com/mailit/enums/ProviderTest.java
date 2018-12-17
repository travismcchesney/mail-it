package com.mailit.enums;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProviderTest {

    @Test
    public void testGetProvider() throws Exception {
        assertThat(Provider.SENDGRID.getProvider()).isEqualTo("sendgrid");
        assertThat(Provider.MAILGUN.getProvider()).isEqualTo("mailgun");
    }

    @Test
    public void testFromString() throws Exception {
        assertThat(Provider.fromString("sendgrid")).isEqualTo(Provider.SENDGRID);
        assertThat(Provider.fromString("SenDgRiD")).isEqualTo(Provider.SENDGRID);

        assertThat(Provider.fromString("mailgun")).isEqualTo(Provider.MAILGUN);
        assertThat(Provider.fromString("mAiLgUn")).isEqualTo(Provider.MAILGUN);
    }

    @Test
    public void testToString() throws Exception {
        assertThat(Provider.SENDGRID.toString()).isEqualTo("sendgrid");
        assertThat(Provider.MAILGUN.toString()).isEqualTo("mailgun");
    }
}