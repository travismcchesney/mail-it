package com.mailit.resources;

import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MailItResourceTest {
    private static final Mail mail = new Mail(
            "jack@example.com",
            "Jack",
            "noreply@paperstreetsoapco.com",
            "Tyler Durden",
            "A Message from Paper Street Soap Co.",
            "<h1>Your Bill</h1><p>$10</p>"
    );
    private static final Mailer mailer = mock(Mailer.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MailItResource(mailer))
            .build();

    @Test
    public void testPostMail() {
        assertThat(resources
                .client()
                .target("/email")
                .request()
                .post(Entity.json(mail))
                .getStatus())
                .isEqualTo(200);
    }

    @Test
    public void testPostMailWithInvalidEmailAddress() {
        Mail invalidTo = new Mail(
                "jack@",
                "Jack",
                "noreply@paperstreetsoapco.com",
                "Tyler Durden",
                "A Message from Paper Street Soap Co.",
                "<h1>Your Bill</h1><p>$10</p>"
        );

        Response r = resources
                .client()
                .target("/email")
                .request()
                .post(Entity.json(invalidTo));

        assertThat(r.getStatus()).isEqualTo(422);
        assertThat(r.readEntity(HashMap.class).size()).isEqualTo(1);

        Mail invalidFrom = new Mail(
                "jack@example.com",
                "Jack",
                "noreply@paperstreetsoapco",
                "Tyler Durden",
                "A Message from Paper Street Soap Co.",
                "<h1>Your Bill</h1><p>$10</p>"
        );

        r = resources
                .client()
                .target("/email")
                .request()
                .post(Entity.json(invalidTo));

        assertThat(r.getStatus()).isEqualTo(422);
        assertThat(r.readEntity(HashMap.class).size()).isEqualTo(1);
    }

    @Test
    public void testPostMailWithMissingFields() {
        Mail missingTo = new Mail(
                null,
                "Jack",
                "noreply@paperstreetsoapco.com",
                "Tyler Durden",
                "A Message from Paper Street Soap Co.",
                "<h1>Your Bill</h1><p>$10</p>"
        );

        Response r = resources
                .client()
                .target("/email")
                .request()
                .post(Entity.json(missingTo));

        assertThat(r.getStatus()).isEqualTo(422);
        assertThat(r.readEntity(HashMap.class).size()).isEqualTo(1);

        Mail missingToName = new Mail(
                "jack@example.com",
                null,
                "noreply@paperstreetsoapco.com",
                "Tyler Durden",
                "A Message from Paper Street Soap Co.",
                "<h1>Your Bill</h1><p>$10</p>"
        );

        r = resources
                .client()
                .target("/email")
                .request()
                .post(Entity.json(missingToName));

        assertThat(r.getStatus()).isEqualTo(422);
        assertThat(r.readEntity(HashMap.class).size()).isEqualTo(1);

        Mail missingFrom = new Mail(
                "jack@example.com",
                "Jack",
                null,
                "Tyler Durden",
                "A Message from Paper Street Soap Co.",
                "<h1>Your Bill</h1><p>$10</p>"
        );

        r = resources
                .client()
                .target("/email")
                .request()
                .post(Entity.json(missingFrom));

        assertThat(r.getStatus()).isEqualTo(422);
        assertThat(r.readEntity(HashMap.class).size()).isEqualTo(1);

        Mail missingFromName = new Mail(
                "jack@example.com",
                "Jack",
                "noreply@paperstreetsoapco.com",
                null,
                "A Message from Paper Street Soap Co.",
                "<h1>Your Bill</h1><p>$10</p>"
        );

        r = resources
                .client()
                .target("/email")
                .request()
                .post(Entity.json(missingFromName));

        assertThat(r.getStatus()).isEqualTo(422);
        assertThat(r.readEntity(HashMap.class).size()).isEqualTo(1);

        Mail missingSubject = new Mail(
                "jack@example.com",
                "Jack",
                "noreply@paperstreetsoapco.com",
                "Tyler Durden",
                null,
                "<h1>Your Bill</h1><p>$10</p>"
        );

        r = resources
                .client()
                .target("/email")
                .request()
                .post(Entity.json(missingSubject));

        assertThat(r.getStatus()).isEqualTo(422);
        assertThat(r.readEntity(HashMap.class).size()).isEqualTo(1);

        Mail missingBody = new Mail(
                "jack@example.com",
                "Jack",
                "noreply@paperstreetsoapco.com",
                "Tyler Durden",
                "A Message from Paper Street Soap Co.",
                null
        );

        r = resources
                .client()
                .target("/email")
                .request()
                .post(Entity.json(missingBody));

        assertThat(r.getStatus()).isEqualTo(422);
        assertThat(r.readEntity(HashMap.class).size()).isEqualTo(1);
    }

    @Test
    public void testMailIt() throws Exception {
        Mailer mailer = mock(Mailer.class);

        MailItResource mir = new MailItResource(mailer);

        mir.mailIt(mail);

        verify(mailer, times(1)).mail(mail);
    }
}