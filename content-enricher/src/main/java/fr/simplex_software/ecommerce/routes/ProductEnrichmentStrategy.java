package fr.simplex_software.ecommerce.routes;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.util.*;

@ApplicationScoped
@Named("productEnrichmentStrategy")
public class ProductEnrichmentStrategy implements AggregationStrategy
{
  @Override
  public Exchange aggregate(Exchange original, Exchange enrichment)
  {
    EnrichedOrder partiallyEnriched = original.getIn().getBody(EnrichedOrder.class);
    Map<String, ProductDetails> productMap = enrichment.getIn().getBody(Map.class);
    List<EnrichedOrderItem> fullyEnrichedItems = partiallyEnriched.enrichedItems().stream()
      .map(item -> new EnrichedOrderItem(
        item.orderItem(),
        productMap.get(item.orderItem().productId())
      ))
      .toList();
    EnrichedOrder fullyEnriched = new EnrichedOrder(
      partiallyEnriched.order(),
      partiallyEnriched.customerDetails(),
      fullyEnrichedItems
    );
    original.getIn().setBody(fullyEnriched);
    return original;
  }
}
