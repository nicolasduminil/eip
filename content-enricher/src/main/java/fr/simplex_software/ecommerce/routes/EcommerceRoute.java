package fr.simplex_software.ecommerce.routes;

import fr.simplex_software.ecommerce.processor.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.builder.*;

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
      .log("=== ORDER ===")
      .log("${body}")
      .to("log:content-enricher")
      .to("direct:enrichOrder");
    from("direct:enrichOrder")
      .routeId("doEnrichment")
      .enrich("direct:getProductDetails", orderItemEnrichmentStrategy)
      .enrich("direct:getCustomerDetails", orderEnrichmentStrategy)
      .log("=== ENRICHED ORDER ===")
      .log("${body}")
      .to("log:content-enricher");
    from("direct:getCustomerDetails")
      .routeId("orderEnrichment")
      .process("orderEnrichmentService")
      .log("\t >>> Customer details retrieved: ${body}");
    from("direct:getProductDetails")
      .routeId("orderItemEnrichment")
      .process("orderItemEnrichmentService")
      .log("\t >>> Product details retrieved: ${body}");
  }
}
