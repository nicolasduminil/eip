package fr.simplex_software.ecommerce.routes;

import fr.simplex_software.ecommerce.processor.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.builder.*;
<<<<<<< Updated upstream
import org.apache.camel.spi.annotations.*;
=======
>>>>>>> Stashed changes

@ApplicationScoped
public class EcommerceRoute extends RouteBuilder
{
  @Inject
  OrderGenerator orderGenerator;
  @Inject
  OrderEnrichmentStrategy orderEnrichmentStrategy;
  @Inject
  OrderItemEnrichmentStrategy orderItemEnrichmentStrategy;

  @Override
  public void configure() throws Exception
  {
    from("timer:orderGenerator?period=15000")
      .routeId("contentEnricherDemo")
      .autoStartup(false)
      .process(orderGenerator)
<<<<<<< Updated upstream
      .enrich("direct:getProductDetails", productEnrichmentStrategy)
      .enrich("direct:getCustomerDetails", customerEnrichmentStrategy)
=======
      .enrich("direct:getCustomerDetails", orderEnrichmentStrategy)
      .enrich("direct:getProductDetails", orderItemEnrichmentStrategy)
>>>>>>> Stashed changes
      .log("=== ENRICHED ORDER ===")
      .log("${body}")
      .to("log:content-enricher");
    from("direct:getCustomerDetails")
      .routeId("orderEnrichment")
      .process("orderEnrichmentService")
      .log("Customer details retrieved: ${body}");
    from("direct:getProductDetails")
      .routeId("orderItemEnrichment")
      .process("orderItemEnrichmentService")
      .log("Product details retrieved: ${body}");
  }
}
