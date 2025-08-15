package fr.simplex_software.ecommerce.model;

public record EnrichedOrderItem(
  OrderItem orderItem,
  ProductDetails productDetails
)
{
}
