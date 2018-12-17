package com.mailit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class MailTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private static final Mail mail = new Mail(
            "fake@example.com",
            "Mr. Fake",
            "noreply@mybrightwheel.com",
            "Brightwheel",
            "A Message from Brightwheel",
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
        assertThat(mail.getEmailTo()).isEqualTo("Mr. Fake <fake@example.com>");
    }

    @Test
    public void testEmailFrom() throws Exception {
        assertThat(mail.getEmailFrom()).isEqualTo("Brightwheel <noreply@mybrightwheel.com>");
    }
}