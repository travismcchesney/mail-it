package com.mailit.mailer.impl;

import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MailgunMailerTest {
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
        Mailer mailer = new MailgunMailer("foo");

        assertThat(mailer.getName()).isEqualTo("Mailgun");
    }

    @Test
    public void testMail() throws Exception {
        HttpRequestWithBody hrwb = mock(HttpRequestWithBody.class);
        MultipartBody mpb = mock(MultipartBody.class);

        when(hrwb.field(anyString(), anyString())).thenReturn(mpb);
        when(mpb.field(anyString(), anyString())).thenReturn(mpb);

        Mailer mailer = new MailgunMailer(hrwb);

        mailer.mail(mail);

        verify(hrwb, times(1)).field(anyString(), anyString());
        verify(mpb, times(3)).field(anyString(), anyString());
        verify(mpb, times(1)).asJson();
    }

    @Test
    public void testIsHealth() {
        Mailer mailer = new MailgunMailer("foo");

        assertThat(mailer.isHealthy()).isTrue();
    }
}