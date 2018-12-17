package com.mailit.mailer.impl;

import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SendgridMailerTest {
    private static final Mail mail = new Mail(
            "jack@example.com",
            "Jack",
            "noreply@paperstreetsoapco.com",
            "Tyler Durden",
            "A Message from Paper Street Soap Co.",
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
        HttpResponse r = mock(HttpResponse.class);

        when(hrwb.body(anyMap())).thenReturn(rbe);
        when(rbe.asJson()).thenReturn(r);
        when(r.getStatus()).thenReturn(200);

        Mailer mailer = new SendgridMailer(hrwb);

        mailer.mail(mail);

        verify(hrwb, times(1)).body(anyMap());
        verify(rbe, times(1)).asJson();
    }

    @Test(expected = WebApplicationException.class)
    public void testMailException() throws Exception {
        HttpRequestWithBody hrwb = mock(HttpRequestWithBody.class);
        RequestBodyEntity rbe = mock(RequestBodyEntity.class);

        when(hrwb.body(anyMap())).thenReturn(rbe);
        when(rbe.asJson()).thenThrow(new UnirestException("Something has gone wrong"));

        Mailer mailer = new SendgridMailer(hrwb);

        mailer.mail(mail);
    }

    @Test(expected = WebApplicationException.class)
    public void testMail400() throws Exception {
        HttpRequestWithBody hrwb = mock(HttpRequestWithBody.class);
        RequestBodyEntity rbe = mock(RequestBodyEntity.class);
        HttpResponse r = mock(HttpResponse.class);

        when(hrwb.body(anyMap())).thenReturn(rbe);
        when(rbe.asJson()).thenReturn(r);
        when(r.getStatus()).thenReturn(400);
        when(r.getBody()).thenReturn("There was a 400 error");

        Mailer mailer = new SendgridMailer(hrwb);

        mailer.mail(mail);
    }

    @Test
    public void testIsHealth() {
        Mailer mailer = new SendgridMailer("foo");

        assertThat(mailer.isHealthy()).isTrue();
    }
}