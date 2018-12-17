package com.mailit.resources;

import com.codahale.metrics.annotation.Timed;
import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * MailItResource: Resource routing
 * @author Travis McChesney
 */
@Path("/email")
@Produces(MediaType.APPLICATION_JSON)
public class MailItResource {
    private final Mailer mailer;

    public MailItResource(Mailer mailer) {
        this.mailer = mailer;
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Mail mailIt(Mail mail) {
        mailer.mail(mail);

        return mail;
    }
}
