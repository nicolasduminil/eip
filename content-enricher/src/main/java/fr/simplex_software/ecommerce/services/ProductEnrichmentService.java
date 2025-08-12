package fr.simplex_software.ecommerce.services;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.math.*;
import java.util.*;

@ApplicationScoped
@Named("productEnrichmentService")
public class ProductEnrichmentService implements Processor
{
  @Override
  public void process(Exchange exchange) throws Exception
  {
    Map<String, ProductDetails> productMap = Map.of(
      "LAPTOP-1", new ProductDetails("Gaming Laptop", new BigDecimal("1299.99"), "Electronics", 25),
      "BOOK-1", new ProductDetails("Java Guide", new BigDecimal("45.99"), "Books", 100),
      "FASHION-1", new ProductDetails("T-shirt", new BigDecimal("19.99"), "Fashion", 200)
    );
    exchange.getIn().setBody(productMap);
  }
}
