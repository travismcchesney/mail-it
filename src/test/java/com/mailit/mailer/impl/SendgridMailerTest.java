package com.mailit.mailer.impl;

import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SendgridMailerTest {
    private static final Mail mail = new Mail(
            "fake@example.com",
            "Mr. Fake",
            "noreply@mybrightwheel.com",
            "Brightwheel",
            "A Message from Brightwheel",
            "<h1>Your Bill</h1><p>$10</p>"
    );

    @Test
    public void testGetName() {
        Mailer mailer = new SendgridMailer("foo");

        assertThat(mailer.getName()).isEqualTo("Sendgrid");
    }

    @Test
    public void testMail() throws Exception {
        HttpRequestWithBody hrwb = mock(HttpRequestWithBody.class);
        RequestBodyEntity rbe = mock(RequestBodyEntity.class);

        when(hrwb.body(anyMap())).thenReturn(rbe);

        Mailer mailer = new SendgridMailer(hrwb);

        mailer.mail(mail);

        verify(hrwb, times(1)).body(anyMap());
        verify(rbe, times(1)).asJson();
    }

    @Test
    public void testIsHealth() {
        Mailer mailer = new SendgridMailer("foo");

        assertThat(mailer.isHealthy()).isTrue();
    }
}