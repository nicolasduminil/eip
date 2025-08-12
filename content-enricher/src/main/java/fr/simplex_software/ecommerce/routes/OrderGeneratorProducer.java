package fr.simplex_software.ecommerce.routes;

import fr.simplex_software.ecommerce.processor.*;
import jakarta.enterprise.context.*;
import jakarta.enterprise.inject.*;
import jakarta.inject.*;
import org.apache.camel.*;

@ApplicationScoped
public class OrderGeneratorProducer
{
  @Produces
  @Named("orderGenerator")
  public OrderGenerator orderGenerator()
  {
    return new OrderGenerator();
  }
}
