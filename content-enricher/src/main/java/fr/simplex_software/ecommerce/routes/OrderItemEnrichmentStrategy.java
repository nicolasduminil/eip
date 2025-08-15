package fr.simplex_software.ecommerce.routes;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.util.*;

@ApplicationScoped
@Named("orderItemEnrichmentStrategy")
public class OrderItemEnrichmentStrategy implements AggregationStrategy
{
  @Override
  public Exchange aggregate(Exchange original, Exchange enrichment)
  {
    Order order = original.getIn().getBody(Order.class);
    Map<String, ProductDetails> productMap = enrichment.getIn().getBody(Map.class);
    //
    // Transform order items to enriched order items:
    //   find matching product details for each item,
    //   create EnrichedOrderItem if match found,
    //   filter out items without matches
    //
    List<EnrichedOrderItem> enrichedItems = order.items().stream()
      .map(item -> findProductDetails(productMap, item.productId())
        .map(pd -> new EnrichedOrderItem(item, pd)))
      .filter(Optional::isPresent)
      .map(Optional::get)
      .toList();
    EnrichedOrder fullyEnriched = new EnrichedOrder(
      order.orderId(),
      order.customerId(),
      order.shippingAddress(),
      order.orderDate(),
      null,
      enrichedItems
    );
    original.getIn().setBody(fullyEnriched);
    return original;
  }

  private Optional<ProductDetails> findProductDetails(Map<String, ProductDetails> productMap, String productId)
  {
    //
    // Extract the product ID prefix
    //
    String productPrefix = productId.split("-")[0];
    //
    // Returns the `ProductDetails` instance which ket name starts
    // with the prefix ID prefix.
    //
    return productMap.entrySet().stream()
      .filter(entry -> entry.getKey().startsWith(productPrefix))
      .map(Map.Entry::getValue)
      .findFirst();
  }
}
