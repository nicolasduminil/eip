package fr.simplex_software.ecommerce.processor;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;

@ApplicationScoped
public class FashionProductGenerator implements ProductGenerator
{
  @Override
  public String generateProduct()
  {
    return """
      <product>
        <sku>FASH002</sku>
        <title>Designer Jacket</title>
        <price>299.50</price>
        <variants>
          <variant size="M" color="Blue"/>
        </variants>
      </product>
      """;
  }

  @Override
  public SupplierType getSupplierType()
  {
    return null;
  }
}
