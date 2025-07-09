package fr.simplex_software.ecommerce.transformer;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.util.*;

@ApplicationScoped
@Named("electronicsTransformer")
public class ElectronicsTransformer extends ProductTransformer
{
  @Override
  protected Product transform(Object sourceProduct)
  {
    ElectronicsProduct electronics = (ElectronicsProduct) sourceProduct;
    return new Product(
      electronics.itemId(),
      electronics.name(),
      electronics.cost(),
      "Electronics",
      Map.of("specifications", electronics.specs()),
      "SUPPLIER_A"
    );
  }
}
