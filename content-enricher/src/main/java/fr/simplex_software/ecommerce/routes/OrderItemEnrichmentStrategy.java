package fr.simplex_software.ecommerce.routes;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.util.*;

@ApplicationScoped
@Named("productEnrichmentStrategy")
public class OrderItemEnrichmentStrategy implements AggregationStrategy
{
  @Override
  public Exchange aggregate(Exchange original, Exchange enrichment)
  {
    EnrichedOrder partiallyEnriched = original.getIn().getBody(EnrichedOrder.class);
    Map<String, ProductDetails> productMap = enrichment.getIn().getBody(Map.class);
    System.out.println(">>> ProductMap contents:");
    productMap.forEach((key, value) -> System.out.println("\t" + key + " -> " + value));
    List<EnrichedOrderItem> fullyEnrichedItems = partiallyEnriched.enrichedItems().stream()
      .map(item -> {
        System.out.println (">>> Mapping " + item.orderItem().productId());
        return new EnrichedOrderItem(
          item.orderItem(),
          productMap.get(item.orderItem().productId())
        );
      })
      .toList();
    System.out.println (">>> Enriched Order Items: " + fullyEnrichedItems.size());
    fullyEnrichedItems.forEach(eoi -> System.out.println ("\t" + eoi.orderId() + " " + eoi.orderItem().productName() + " " + eoi.productDetails().name()));
    EnrichedOrder fullyEnriched = new EnrichedOrder(
      partiallyEnriched.order(),
      partiallyEnriched.customerDetails(),
      fullyEnrichedItems
    );
    System.out.println (">>> Enriched Order: " + fullyEnriched.order().orderId() + " " + fullyEnriched.customerDetails().name() + " " + fullyEnriched.enrichedItems().size());
    original.getIn().setBody(fullyEnriched);
    return original;
  }
}
