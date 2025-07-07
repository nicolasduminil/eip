package fr.simplex_software.ecommerce.route;

import fr.simplex_software.ecommerce.model.*;
import fr.simplex_software.ecommerce.processor.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.builder.*;

import java.util.*;

@ApplicationScoped
public class ECommerceRoute extends RouteBuilder
{
  @Inject
  ShipmentAggregator shipmentAggregator;

  @Override
  public void configure() throws Exception
  {
    // Main order processing route
    from("timer:orderGenerator?period=10000")
      .routeId("orderProcessing")
      // Route should be started/stopped via Hawtio
      .autoStartup(false)
      .log("=== Processing new order ===")
      .process("orderGenerator")
      .log("Generated order: ${body}")

      // SPLITTER: Split order into individual items by supplier
      .process("orderSplitter")
      .split(body())
      .log("Processing item: ${body}")
      .to("direct:aggregateBySupplier")
      .end();

    // AGGREGATOR: Group items by supplier + shipping address
    from("direct:aggregateBySupplier")
      .routeId("shipmentAggregation")
      .aggregate(simple("${body.aggregationKey}"))
      .aggregationStrategy("shipmentAggregator")
      .completionTimeout(5000)
      .completionSize(10)
      .process(exchange ->
      {
        @SuppressWarnings("unchecked")
        List<OrderItem> items = exchange.getIn().getBody(List.class);
        Shipment shipment = shipmentAggregator.createShipment(items);
        exchange.getIn().setBody(shipment);
      })
      .log("=== SHIPMENT CREATED ===")
      .log("Shipment: ${body}")
      .to("direct:processShipment");

    // Shipment processing
    from("direct:processShipment")
      .routeId("shipmentProcessing")
      .choice()
      .when(simple("${body.totalValue} > 100"))
      .log("HIGH VALUE shipment (${body.totalValue}€) - Priority processing")
      .otherwise()
      .log("STANDARD shipment (${body.totalValue}€) - Normal processing")
      .end()
      .log("Shipment sent to supplier: ${body.supplierId}");
  }
}