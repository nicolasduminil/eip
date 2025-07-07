package fr.simplex_software.ecommerce.processor;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.util.*;

@ApplicationScoped
@Named("shipmentAggregator")
public class ShipmentAggregator implements AggregationStrategy
{
  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange)
  {
    OrderItem newItem = newExchange.getIn().getBody(OrderItem.class);
    @SuppressWarnings("unchecked")
    List<OrderItem> items = Optional.ofNullable(oldExchange)
      .map(ex -> (List<OrderItem>) ex.getIn().getBody(List.class))
      .orElse(new ArrayList<>());
    items.add(newItem);
    Exchange exchange = Optional.ofNullable(oldExchange).orElse(newExchange);
    exchange.getIn().setBody(items);
    return exchange;
  }

  public Shipment createShipment(List<OrderItem> items)
  {
    return items.stream()
      .findFirst()
      .map(first -> new Shipment(first.supplierId(), first.shippingAddress(), items))
      .orElse(null);
  }
}