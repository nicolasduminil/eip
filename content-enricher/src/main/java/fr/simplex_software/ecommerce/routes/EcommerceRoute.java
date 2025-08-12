package fr.simplex_software.ecommerce.routes;

import fr.simplex_software.ecommerce.processor.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.builder.*;

import static org.apache.camel.builder.AggregationStrategies.*;

@ApplicationScoped
public class EcommerceRoute extends RouteBuilder
{
  @Inject
  OrderGenerator orderGenerator;
  @Inject
  CustomerEnrichmentStrategy customerEnrichmentStrategy;
  @Inject
  ProductEnrichmentStrategy productEnrichmentStrategy;

  @Override
  public void configure() throws Exception
  {
    from("timer:orderGenerator?period=15000")
      .routeId("contentEnricherDemo")
      .autoStartup(false)
      .process(orderGenerator)
      .enrich("direct:getCustomerDetails", customerEnrichmentStrategy)
      .enrich("direct:getProductDetails", productEnrichmentStrategy)
      .log("=== ENRICHED ORDER ===")
      .log("${body}")
      .to("log:content-enricher");
    from("direct:getCustomerDetails")
      .routeId("customerEnrichment")
      .process("customerEnrichmentService")
      .log("Customer details retrieved: ${body}");
    from("direct:getProductDetails")
      .routeId("productEnrichment")
      .process("productEnrichmentService")
      .log("Product details retrieved: ${body}");
  }
}
