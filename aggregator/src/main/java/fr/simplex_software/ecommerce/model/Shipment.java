package fr.simplex_software.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record Shipment(
  String shipmentId,
  String supplierId,
  String shippingAddress,
  List<OrderItem> items,
  BigDecimal totalValue,
  LocalDateTime createdAt
)
{
  public Shipment(String supplierId, String shippingAddress, List<OrderItem> items)
  {
    this(
      generateShipmentId(supplierId),
      supplierId,
      shippingAddress,
      items,
      calculateTotalValue(items),
      LocalDateTime.now()
    );
  }

  private static String generateShipmentId(String supplierId)
  {
    return "SHIP-%s-%d".formatted(supplierId, System.currentTimeMillis());
  }

  private static BigDecimal calculateTotalValue(List<OrderItem> items)
  {
    return items.stream()
      .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  @Override
  public String toString()
  {
    return "Shipment {id = '%s', supplier = '%s', items = %d, value = %.2f}"
      .formatted(shipmentId, supplierId, items.size(), totalValue);
  }
}