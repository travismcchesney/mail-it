package com.mailit.api.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * MailerUtil: Utility methods to work on Mail objects.
 * @author Travis McChesney
 */
public class MailUtil {
    public static String toPlainText(String body) {
        Document doc = Jsoup.parse(body);
        return doc.body().text();
    }
}
