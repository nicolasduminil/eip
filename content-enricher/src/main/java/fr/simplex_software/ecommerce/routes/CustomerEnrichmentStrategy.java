package fr.simplex_software.ecommerce.routes;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.util.*;

@ApplicationScoped
@Named("customerEnrichmentStrategy")
public class CustomerEnrichmentStrategy implements AggregationStrategy
{
  @Override
  public Exchange aggregate(Exchange original, Exchange enrichment)
  {
    EnrichedOrder enrichedOrder = original.getIn().getBody(EnrichedOrder.class);
    CustomerDetails customerDetails = enrichment.getIn().getBody(CustomerDetails.class);
    original.getIn().setBody(enrichedOrder.withCustomerDetails(customerDetails));
    return original;
  }
}
