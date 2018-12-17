package com.mailit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class MailTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private static final Mail mail = new Mail(
            "jack@example.com",
            "Jack",
            "noreply@paperstreetsoapco.com",
            "Tyler Durden",
            "A Message from Paper Street Soap Co.",
            "<h1>Your Bill</h1><p>$10</p>"
    );

    @Test
    public void testSerializesToJSON() throws Exception {
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/mail.json"), Mail.class));

        assertThat(MAPPER.writeValueAsString(mail)).isEqualTo(expected);
    }

    @Test
    public void testDeserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(fixture("fixtures/mail.json"), Mail.class))
                .isEqualTo(mail);
    }

    @Test
    public void testPlainBody() throws Exception {
        assertThat(mail.getPlainBody()).doesNotContain("<", ">");
    }

    @Test
    public void testEmailTo() throws Exception {
        assertThat(mail.getEmailTo()).isEqualTo("Jack <jack@example.com>");
    }

    @Test
    public void testEmailFrom() throws Exception {
        assertThat(mail.getEmailFrom()).isEqualTo("Tyler Durden <noreply@paperstreetsoapco.com>");
    }
}