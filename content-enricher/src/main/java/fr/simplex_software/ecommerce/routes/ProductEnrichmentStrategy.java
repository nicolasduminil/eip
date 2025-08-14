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
    Order order = original.getIn().getBody(Order.class);
    Map<String, ProductDetails> productMap = enrichment.getIn().getBody(Map.class);
    List<EnrichedOrderItem> fullyEnrichedItems = order.items().stream()
      .filter(item -> productMap.containsKey(item.productId()))
      .map(item -> new EnrichedOrderItem(item, productMap.get(item.productId())))
      .toList();
    EnrichedOrder fullyEnriched = new EnrichedOrder(
      order.orderId(),
      order.customerId(),
      order.shippingAddress(),
      order.orderDate(),
      null,
      fullyEnrichedItems
    );
    original.getIn().setBody(fullyEnriched);
    return original;
  }
}
