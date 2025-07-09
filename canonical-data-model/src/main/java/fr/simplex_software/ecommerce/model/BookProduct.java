package fr.simplex_software.ecommerce.model;

import java.math.*;

public record BookProduct(
  String isbn,
  String bookTitle,
  String author,
  BigDecimal retailPrice
){}
