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
    Order order = original.getIn().getBody(Order.class);
    CustomerDetails customer = enrichment.getIn().getBody(CustomerDetails.class);
    List<EnrichedOrderItem> enrichedItems = order.items().stream()
      .map(orderItem -> new EnrichedOrderItem(orderItem, null))
      .toList();
    EnrichedOrder enrichedOrder = new EnrichedOrder(order, customer, enrichedItems);
    original.getIn().setBody(enrichedOrder);
    return original;
  }
}
