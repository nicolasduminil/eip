package fr.simplex_software.ecommerce.tests;

import fr.simplex_software.ecommerce.model.*;
import fr.simplex_software.ecommerce.model.Order;
import io.quarkus.test.junit.*;
import jakarta.inject.*;
import org.apache.camel.*;
import org.junit.jupiter.api.*;


import java.math.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TestEcommerceRoute
{
  @Inject
  CamelContext camelContext;

  @Inject
  ProducerTemplate producerTemplate;

  @Test
  void testContentEnricherDemo() throws Exception {
    Order testOrder = new Order(
      "BOOK-1820",
      "CUST-123",
      "123 Test St",
      LocalDateTime.now(),
      List.of(new OrderItem("BOOK-1", "Computer book",
        "SUPPLIER_BOOKS", 1, new BigDecimal(41.75)))
    );
    Exchange result = producerTemplate.request("direct:enrichOrder",
      exchange -> exchange.getIn().setBody(testOrder));
    EnrichedOrder enrichedOrder = result.getIn().getBody(EnrichedOrder.class);
    assertNotNull(enrichedOrder, "Enriched order should not be null");
    assertEquals("BOOK-1820", enrichedOrder.orderId());
    assertNotNull(enrichedOrder.customerDetails(), "Customer details should be enriched");
    assertFalse(enrichedOrder.enrichedItems().isEmpty(), "Items should be present");
    EnrichedOrderItem enrichedItem = enrichedOrder.enrichedItems().get(0);
    assertNotNull(enrichedItem.productDetails(), "Product details should be enriched");
  }
}
