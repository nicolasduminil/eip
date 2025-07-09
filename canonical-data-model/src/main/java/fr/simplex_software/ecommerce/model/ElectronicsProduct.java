package fr.simplex_software.ecommerce.model;

import java.math.*;
import java.util.*;

public record ElectronicsProduct(
  String itemId,
  String name,
  BigDecimal cost,
  Map<String, String> specs
){}
