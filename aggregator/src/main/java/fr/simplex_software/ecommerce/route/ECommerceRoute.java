package fr.simplex_software.ecommerce.route;

import fr.simplex_software.ecommerce.model.OrderItem;
import fr.simplex_software.ecommerce.model.Shipment;
import fr.simplex_software.ecommerce.processor.ShipmentAggregator;
import org.apache.camel.builder.RouteBuilder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

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
      .choice()
      .when(simple("${body.totalValue} > 100"))
      .log("HIGH VALUE shipment (${body.totalValue}€) - Priority processing")
      .otherwise()
      .log("STANDARD shipment (${body.totalValue}€) - Normal processing")
      .end()
      .log("Shipment sent to supplier: ${body.supplierId}");
  }
}