package fr.simplex_software.ecommerce.processor;

import fr.simplex_software.ecommerce.model.Order;
import fr.simplex_software.ecommerce.model.OrderItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.List;

@ApplicationScoped
@Named("orderSplitter")
public class OrderSplitter implements Processor
{
  @Override
  public void process(Exchange exchange) throws Exception
  {
    Order order = exchange.getIn().getBody(Order.class);

    List<OrderItem> enrichedItems = order.items().stream()
      .map(item -> item.withOrderContext(order.orderId(), order.shippingAddress()))
      .toList();

    exchange.getIn().setBody(enrichedItems);
  }
}