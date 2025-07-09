package fr.simplex_software.ecommerce.model;

import java.math.*;
import java.util.*;

public record Product(
  String id,
  String name,
  BigDecimal price,
  String category,
  Map<String, Object> attributes,
  String supplierId
){}
