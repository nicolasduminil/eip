package fr.simplex_software.ecommerce.processor;

import fr.simplex_software.ecommerce.model.OrderItem;
import fr.simplex_software.ecommerce.model.Shipment;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named("shipmentAggregator")
public class ShipmentAggregator implements AggregationStrategy
{
  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange)
  {
    OrderItem newItem = newExchange.getIn().getBody(OrderItem.class);

    if (oldExchange == null)
    {
      List<OrderItem> items = new ArrayList<>();
      items.add(newItem);
      newExchange.getIn().setBody(items);
      return newExchange;
    }

    @SuppressWarnings("unchecked")
    List<OrderItem> existingItems = oldExchange.getIn().getBody(List.class);
    existingItems.add(newItem);

    return oldExchange;
  }

  public Shipment createShipment(List<OrderItem> items)
  {
    if (items.isEmpty())
      return null;

    OrderItem firstItem = items.get(0);
    return new Shipment(firstItem.supplierId(), firstItem.shippingAddress(), items);
  }
}