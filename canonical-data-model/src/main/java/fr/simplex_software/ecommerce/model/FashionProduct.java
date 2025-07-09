package fr.simplex_software.ecommerce.model;

import java.math.*;
import java.util.*;

public record FashionProduct(
  String sku,
  String title,
  BigDecimal price,
  List<Variant> variants
)
{
  public record Variant(String size, String color) {}
}
