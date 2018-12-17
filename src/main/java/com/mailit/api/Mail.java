package com.mailit.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mailit.api.util.MailUtil;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Mailer: Domain object representing an email to be sent.
 * @author Travis McChesney
 */
public class Mail {
    @NotEmpty(message = "A valid 'to' address must be provided")
    private String to;
    @NotEmpty(message = "A valid 'to_name' must be provided")
    private String toName;
    @NotEmpty(message = "A valid 'from' address must be provided")
    private String from;
    @NotEmpty(message = "A valid 'from_name' must be provided")
    private String fromName;
    @NotEmpty(message = "A valid 'subject' must be provided")
    private String subject;
    @NotEmpty(message = "A valid 'body' must be provided")
    private String body;
    private String plainBody;

    public Mail() {
        // Jackson deserialization
    }

    @JsonCreator
    public Mail(@JsonProperty("to") String to,
                @JsonProperty("to_name") String toName,
                @JsonProperty("from") String from,
                @JsonProperty("from_name") String fromName,
                @JsonProperty("subject") String subject,
                @JsonProperty("body") String body) {
        this.to = to;
        this.toName = toName;
        this.from = from;
        this.fromName = fromName;
        this.subject = subject;
        this.body = body;
    }

    @JsonProperty
    @ApiModelProperty(value = "recipient email address")
    public String getTo() {
        return to;
    }

    @JsonProperty(value = "to_name")
    @ApiModelProperty(value = "recipient name")
    public String getToName() {
        return toName;
    }

    @JsonIgnore
    public String getEmailTo() {
        return String.format("%s <%s>", toName, to);
    }

    @JsonProperty
    @ApiModelProperty(value = "sender email address")
    public String getFrom() {
        return from;
    }

    @JsonProperty(value = "from_name")
    @ApiModelProperty(value = "sender name")
    public String getFromName() {
        return fromName;
    }

    @JsonIgnore
    public String getEmailFrom() {
        return String.format("%s <%s>", fromName, from);
    }

    @JsonProperty
    @ApiModelProperty(value = "subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty
    @ApiModelProperty(value = "email body; will be converted to plain text")
    public String getBody() {
        return body;
    }

    @JsonIgnore
    public String getPlainBody() {
        // Lazy eval
        if (plainBody == null) {
            plainBody = MailUtil.toPlainText(body);
        }

        return plainBody;
    }

    @Override
    public String toString() {
        return String.format(
                "from: %s, fromName: %s, to: %s, toName: %s, subject: %s, body: %s",
                from, fromName, to, toName, subject, body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Mail)) { return false; }

        Mail mail = (Mail) o;

        return body.equals(mail.body) &&
                from.equals(mail.from) &&
                fromName.equals(mail.fromName) &&
                subject.equals(mail.subject) &&
                to.equals(mail.to) &&
                toName.equals(mail.toName);
    }

    @Override
    public int hashCode() {
        int result = to == null ? 0 : to.hashCode();
        result = 31 * result + (toName == null ? 0 : toName.hashCode());
        result = 31 * result + (from == null ? 0 : from.hashCode());
        result = 31 * result + (fromName == null ? 0 : fromName.hashCode());
        result = 31 * result + (subject == null ? 0 : subject.hashCode());
        result = 31 * result + (body == null ? 0 : body.hashCode());
        return result;
    }
}
