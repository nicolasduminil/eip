package fr.simplex_software.ecommerce.processor;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;

import java.util.*;

@ApplicationScoped
public class ElectronicsProductGenerator implements ProductGenerator
{
  private final Random random = new Random();

  @Override
  public String generateProduct()
  {
    return """
      {
        "itemId": "ELEC001",
        "name": "Gaming Laptop",
        "cost": 1299.99,
        "specs": {"cpu": "Intel i7", "ram": "16GB"}
      }
      """;
  }

  @Override
  public SupplierType getSupplierType()
  {
    return SupplierType.ELECTRONICS;
  }
}
