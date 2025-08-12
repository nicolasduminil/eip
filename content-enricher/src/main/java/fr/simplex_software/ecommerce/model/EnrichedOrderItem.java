package fr.simplex_software.ecommerce.model;

public record EnrichedOrderItem(
  OrderItem orderItem,
  ProductDetails productDetails
)
{
  public String orderId()
  {
    return orderItem.orderId();
  }

  @Override
  public OrderItem orderItem()
  {
    return orderItem;
  }
}
