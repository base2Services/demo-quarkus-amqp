package com.base2.demos.amqp.processor;

import java.util.UUID;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuoteGenerator {

   private static final Logger log = Logger.getLogger(QuoteGenerator.class);

    @Channel("quote-requests") 
    Emitter<String> quoteRequestEmitter;

    @Scheduled(every="10s")     
    public void createRequest() {
      UUID uuid = UUID.randomUUID();
      if(quoteRequestEmitter.hasRequests()) {
        quoteRequestEmitter.send(uuid.toString());
      } else {
        log.info("emitter not ready!!!!");
      }
  }
  
}
