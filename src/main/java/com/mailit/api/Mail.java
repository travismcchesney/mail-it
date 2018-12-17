package com.mailit.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mailit.util.MailUtil;

/**
 * Mailer:
 * @author Travis McChesney
 */
public class Mail {
    private String to;
    private String toName;
    private String from;
    private String fromName;
    private String subject;
    private String body;
    private String plainBody;

    public Mail() {
        // Jackson deserialization
    }

    public Mail(String to, String toName, String from, String fromName, String subject, String body) {
        this.to = to;
        this.toName = toName;
        this.from = from;
        this.fromName = fromName;
        this.subject = subject;
        this.body = body;
    }

    @JsonProperty
    public String getTo() {
        return to;
    }

    @JsonProperty(value = "to_name")
    public String getToName() {
        return toName;
    }

    @JsonIgnore
    public String getEmailTo() {
        return String.format("%s <%s>", toName, to);
    }

    @JsonProperty
    public String getFrom() {
        return from;
    }

    @JsonProperty(value = "from_name")
    public String getFromName() {
        return fromName;
    }

    @JsonIgnore
    public String getEmailFrom() {
        return String.format("%s <%s>", fromName, from);
    }

    @JsonProperty
    public String getSubject() {
        return subject;
    }

    @JsonProperty
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
        int result = to.hashCode();
        result = 31 * result + toName.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + fromName.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}
