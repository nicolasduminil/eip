package fr.simplex_software.ecommerce.model;

import java.math.*;

public record ProductDetails(
  String name,
  BigDecimal price,
  String category,
  int stockLevel
)
{
}
