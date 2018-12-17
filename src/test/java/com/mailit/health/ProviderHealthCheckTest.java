package com.mailit.health;

import com.mailit.mailer.Mailer;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class ProviderHealthCheckTest {

    @Test
    public void testCheckHealthy() throws Exception {
        Mailer mailer = mock(Mailer.class);
        when(mailer.isHealthy()).thenReturn(true);

        ProviderHealthCheck phc = new ProviderHealthCheck(mailer);

        assertThat(phc.check().isHealthy()).isTrue();
    }

    @Test
    public void testCheckUnhealthy() throws Exception {
        Mailer mailer = mock(Mailer.class);
        when(mailer.isHealthy()).thenReturn(false);

        ProviderHealthCheck phc = new ProviderHealthCheck(mailer);

        assertThat(phc.check().isHealthy()).isFalse();
    }
}