package com.mailit.resources;

import com.codahale.metrics.annotation.Timed;
import com.mailit.api.Mail;
import com.mailit.mailer.Mailer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * MailItResource: Resource routing
 * @author Travis McChesney
 */
@Api(value = "Email operations")
@Path("/email")
@Produces(MediaType.APPLICATION_JSON)
public class MailItResource {
    private final Mailer mailer;
    private final Logger logger;
    private static final String VALIDATION_ERROR_MESSAGE = "The Mail object is malformed in some way";
    private static final String ERROR_MESSAGE = "Something has gone wrong internally that we need to look into";

    public MailItResource(Mailer mailer) {
        this.mailer = mailer;
        this.logger = LoggerFactory.getLogger(mailer.getClass());
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Send an email")
    @ApiResponses(
            {@ApiResponse(code = 422, message = VALIDATION_ERROR_MESSAGE),
                    @ApiResponse(code = 500, message = ERROR_MESSAGE)})
    public Mail mailIt(@ApiParam(
            value = "Email to send",
            required = true,
            examples = @Example(
                    value = @ExampleProperty(
                            mediaType = MediaType.APPLICATION_JSON,
                            value = "{to: someone, from: someone}")))
                       @Valid Mail mail) {
        logger.debug("Mailing with " + mailer.getName());
        mailer.mail(mail);
        logger.debug("Mailed with " + mailer.getName());

        return mail;
    }
}
