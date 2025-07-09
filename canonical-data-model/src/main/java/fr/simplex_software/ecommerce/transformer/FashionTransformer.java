package fr.simplex_software.ecommerce.transformer;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.util.*;

@ApplicationScoped
@Named("fashionTransformer")
public class FashionTransformer extends ProductTransformer
{
  @Override
  protected Product transform(Object sourceProduct)
  {
    FashionProduct fashion = (FashionProduct) sourceProduct;
    return new Product(
      fashion.sku(),
      fashion.title(),
      fashion.price(),
      "Fashion",
      Map.of("variants", fashion.variants()),
      "SUPPLIER_B"
    );
  }
}
