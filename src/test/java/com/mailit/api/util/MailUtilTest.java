package com.mailit.api.util;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MailUtilTest {

    @Test
    public void testToPlainText() throws Exception {
        String html = "<h1>Your Bill</h1><p>$10</p>";

        String plain = MailUtil.toPlainText(html);

        assertThat(plain).doesNotContain("<", ">");
    }
}