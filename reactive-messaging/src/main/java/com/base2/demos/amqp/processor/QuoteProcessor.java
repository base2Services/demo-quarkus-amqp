package com.base2.demos.amqp.processor;

import java.util.Random;

import jakarta.enterprise.context.ApplicationScoped;


import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;


import io.smallrye.reactive.messaging.annotations.Blocking;
import org.jboss.logging.Logger;

import com.base2.demos.amqp.model.Quote;

/**
 * A bean consuming data from the "quote-requests" RabbitMQ queue and giving out a random quote.
 * The result is pushed to the "quotes" RabbitMQ exchange.
 */
@ApplicationScoped
public class QuoteProcessor {

  private static final Logger log = Logger.getLogger(QuoteProcessor.class);

    private Random random = new Random();

    @Incoming("requests")       
    @Outgoing("quotes")         
    @Blocking                   
    public Quote process(String quoteRequest) throws InterruptedException {
        // simulate some hard-working task
        log.info("got quote request:" + quoteRequest);
        return new Quote(quoteRequest, random.nextInt(100));
    }

    @Incoming("quotes")
    public void receiveQuotes(Quote quote) {
      log.info("got quote: " + quote);
    }
}