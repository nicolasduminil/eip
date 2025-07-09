package fr.simplex_software.ecommerce.transformer;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import org.apache.camel.*;

@ApplicationScoped
public abstract class ProductTransformer implements Processor
{
  @Override
  public final void process(Exchange exchange) throws Exception
  {
    Object sourceProduct = exchange.getIn().getBody();
    Product canonicalProduct = transform(sourceProduct);
    exchange.getIn().setBody(canonicalProduct);
  }

  protected abstract Product transform(Object sourceProduct);
}
