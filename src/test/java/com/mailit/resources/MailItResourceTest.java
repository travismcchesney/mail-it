package com.mailit.resources;

import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MailItResourceTest {
    private static final Mail mail = new Mail(
            "fake@example.com",
            "Mr. Fake",
            "noreply@mybrightwheel.com",
            "Brightwheel",
            "A Message from Brightwheel",
            "<h1>Your Bill</h1><p>$10</p>"
    );

    @Test
    public void testMailIt() throws Exception {
        Mailer mailer = mock(Mailer.class);

        MailItResource mir = new MailItResource(mailer);

        mir.mailIt(mail);

        verify(mailer, times(1)).mail(mail);
    }
}