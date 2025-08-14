package fr.simplex_software.ecommerce.services;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.util.*;

@ApplicationScoped
@Named("orderEnrichmentService")
public class OrderEnrichmentService implements Processor
{
  @Override
  public void process(Exchange exchange) throws Exception
  {
    CustomerDetails customerDetails = new CustomerDetails(
      "John Doe",
      "john@example.com",
      "GOLD"
    );
    exchange.getIn().setBody(customerDetails);
  }
}
