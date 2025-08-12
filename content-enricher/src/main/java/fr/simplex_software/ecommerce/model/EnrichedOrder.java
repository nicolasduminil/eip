package fr.simplex_software.ecommerce.model;

import java.time.*;
import java.util.*;

public record EnrichedOrder(
  Order order,
  CustomerDetails customerDetails,
  List<EnrichedOrderItem> enrichedItems)
{
  public String orderId()
  {
    return order.orderId();
  }

  public String customerId()
  {
    return order.customerId();
  }

  public String shippingAddress()
  {
    return order.shippingAddress();
  }

  public LocalDateTime orderDate()
  {
    return order.orderDate();
  }

  public List<OrderItem> orderItems()
  {
    return order.items();
  }
}
