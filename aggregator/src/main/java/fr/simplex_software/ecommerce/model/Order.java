package fr.simplex_software.ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;

public record Order(
  String orderId,
  String customerId,
  String shippingAddress,
  LocalDateTime orderDate,
  List<OrderItem> items
)
{
  public Order(String orderId, String customerId, String shippingAddress, List<OrderItem> items)
  {
    this(orderId, customerId, shippingAddress, LocalDateTime.now(), items);
  }

  @Override
  public String toString()
  {
    return "Order {orderId = '%s', customerId = '%s', items = %d}"
      .formatted(orderId, customerId, items.size());
  }
}