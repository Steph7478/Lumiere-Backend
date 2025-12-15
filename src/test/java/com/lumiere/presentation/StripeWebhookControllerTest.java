package com.lumiere.presentation;

import com.lumiere.application.ports.WebhookPort;
import com.lumiere.application.services.ratelimiter.RateLimiterService;
import com.lumiere.application.webhooks.WebhookEvent;
import com.lumiere.infrastructure.stripe.dispatcher.StripeWebhookEventDispatcher;
import com.lumiere.infrastructure.stripe.services.StripeEventConstructorService;
import com.lumiere.presentation.controllers.StripeWebhookController;
import com.lumiere.presentation.routes.Routes;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StripeWebhookController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@Import(StripeWebhookControllerTest.MockDependenciesConfig.class)
class StripeWebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StripeEventConstructorService eventConstructorService;
    @Autowired
    private StripeWebhookEventDispatcher dispatcher;
    @Autowired
    private WebhookPort webhookPort;
    @Autowired
    private RateLimiterService rateLimiterService;
    private final String API_VERSION_PREFIX = "/api/v1";

    private final String ENDPOINT = API_VERSION_PREFIX + Routes.PUBLIC.WEBHOOKS.STRIPE;

    private final String MOCK_PAYLOAD = "{\"id\":\"evt_test\"}";
    private final String MOCK_SIGNATURE = "t=1678886400,v1=valid_sig";

    private Event mockStripeEvent;
    private WebhookEvent mockInternalEvent;

    @TestConfiguration
    static class MockDependenciesConfig {
        @Bean
        public StripeEventConstructorService eventConstructorService() {
            return Mockito.mock(StripeEventConstructorService.class);
        }

        @Bean
        public StripeWebhookEventDispatcher dispatcher() {
            return Mockito.mock(StripeWebhookEventDispatcher.class);
        }

        @Bean
        public WebhookPort webhookPort() {
            return Mockito.mock(WebhookPort.class);
        }

        @Bean
        public RateLimiterService rateLimiterService() {
            return Mockito.mock(RateLimiterService.class);
        }
    }

    @BeforeEach
    void setup() {
        mockStripeEvent = Mockito.mock(Event.class);
        mockInternalEvent = Mockito.mock(WebhookEvent.class);

        Mockito.reset(eventConstructorService, dispatcher, webhookPort, rateLimiterService);
        Mockito.when(rateLimiterService.isAllowed(Mockito.anyString())).thenReturn(true);
    }

    @Test
    void handle_ShouldReturnOkAndProcessEvent_WhenSignatureIsValid() throws Exception {
        Mockito.when(eventConstructorService.constructEvent(
                Mockito.eq(MOCK_PAYLOAD),
                Mockito.eq(MOCK_SIGNATURE)))
                .thenReturn(mockStripeEvent);

        Mockito.when(dispatcher.dispatch(mockStripeEvent)).thenReturn(mockInternalEvent);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Stripe-Signature", MOCK_SIGNATURE)
                .content(MOCK_PAYLOAD))
                .andExpect(status().isOk());

        Mockito.verify(eventConstructorService, Mockito.times(1)).constructEvent(Mockito.anyString(),
                Mockito.anyString());
        Mockito.verify(dispatcher, Mockito.times(1)).dispatch(mockStripeEvent);
        Mockito.verify(webhookPort, Mockito.times(1)).handle(mockInternalEvent);
    }

    @Test
    void handle_ShouldReturnBadRequest_WhenSignatureIsInvalid() throws Exception {
        Mockito.when(eventConstructorService.constructEvent(
                Mockito.anyString(),
                Mockito.anyString()))
                .thenThrow(new SignatureVerificationException("Invalid signature", "sig_fail"));

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Stripe-Signature", "INVALID_SIG")
                .content(MOCK_PAYLOAD))
                .andExpect(status().isBadRequest());

        Mockito.verify(eventConstructorService, Mockito.times(1)).constructEvent(Mockito.anyString(),
                Mockito.anyString());

        Mockito.verify(dispatcher, Mockito.never()).dispatch(Mockito.any());
        Mockito.verify(webhookPort, Mockito.never()).handle(Mockito.any());
    }
}