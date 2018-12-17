package com.mailit.factories;

import com.mailit.MailItConfiguration;
import com.mailit.enums.Provider;
import com.mailit.mailer.Mailer;
import com.mailit.mailer.MailerFactory;
import com.mailit.mailer.impl.MailgunMailer;
import com.mailit.mailer.impl.SendgridMailer;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class MailerFactoryTest {
    private static final MailItConfiguration config = mock(MailItConfiguration.class);

    @Before
    public void setUp() {
        when(config.getSendgridApiKey()).thenReturn("sg-api-key");
        when(config.getMailgunApiKey()).thenReturn("mg-api-key");
    }

    @Test
    public void testGetMailer() throws Exception {
        Mailer mailer = MailerFactory.getMailer(Provider.SENDGRID, config);

        assertThat(mailer).isInstanceOf(SendgridMailer.class);

        mailer = MailerFactory.getMailer(Provider.MAILGUN, config);

        assertThat(mailer).isInstanceOf(MailgunMailer.class);
    }
}