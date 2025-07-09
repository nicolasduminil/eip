package fr.simplex_software.ecommerce.processor;

import fr.simplex_software.ecommerce.model.*;

public interface ProductGenerator
{
  String generateProduct();
  SupplierType getSupplierType();
}
