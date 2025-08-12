package fr.simplex_software.ecommerce.services;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

@ApplicationScoped
@Named("customerEnrichmentService")
public class CustomerEnrichmentService implements Processor
{
  @Override
  public void process(Exchange exchange) throws Exception
  {
    CustomerDetails customer = new CustomerDetails(
      "John Doe",
      "john@example.com",
      "GOLD"
    );
    exchange.getIn().setBody(customer);
  }
}
