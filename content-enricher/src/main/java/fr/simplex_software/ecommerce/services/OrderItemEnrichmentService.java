package fr.simplex_software.ecommerce.services;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.math.*;
import java.util.*;

@ApplicationScoped
@Named("orderItemEnrichmentService")
public class OrderItemEnrichmentService implements Processor
{
  @Override
  public void process(Exchange exchange) throws Exception
  {
    Order order = exchange.getIn().getBody(Order.class);
    Map<String, ProductDetails> productMap = Map.of(
      order.items().get(0).productId(), new ProductDetails("Gaming Laptop", new BigDecimal("1299.99"), "Electronics", 25),
      order.items().get(1).productId(), new ProductDetails("Java Guide", new BigDecimal("45.99"), "Books", 100),
      order.items().get(2).productId(), new ProductDetails("T-shirt", new BigDecimal("19.99"), "Fashion", 200)
    );
    exchange.getIn().setBody(productMap);
  }
}
