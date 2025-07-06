package fr.simplex_software.ecommerce.model;

import java.math.BigDecimal;

public record OrderItem(
  String productId,
  String productName,
  String supplierId,
  int quantity,
  BigDecimal price,
  String orderId,
  String shippingAddress
)
{
  public OrderItem(String productId, String productName, String supplierId, int quantity, BigDecimal price)
  {
    this(productId, productName, supplierId, quantity, price, null, null);
  }

  public String getAggregationKey()
  {
    return supplierId + "|" + shippingAddress;
  }

  public OrderItem withOrderContext(String orderId, String shippingAddress)
  {
    return new OrderItem(productId, productName, supplierId, quantity, price, orderId, shippingAddress);
  }

  @Override
  public String toString()
  {
    return "OrderItem {productId = '%s', supplierId = '%s', quantity = %d}"
      .formatted(productId, supplierId, quantity);
  }
}