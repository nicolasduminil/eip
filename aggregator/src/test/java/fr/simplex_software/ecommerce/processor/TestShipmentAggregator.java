package fr.simplex_software.ecommerce.processor;

import fr.simplex_software.ecommerce.model.*;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TestShipmentAggregator
{
  private ShipmentAggregator aggregator;
  private DefaultCamelContext camelContext;

  @BeforeEach
  void setUp()
  {
    aggregator = new ShipmentAggregator();
    camelContext = new DefaultCamelContext();
  }

  @Test
  void testAggregateFirstItem()
  {
    OrderItem item = createOrderItem("LAPTOP-1", "SUPPLIER_ELECTRONICS");
    Exchange newExchange = createExchange(item);
    Exchange result = aggregator.aggregate(null, newExchange);
    @SuppressWarnings("unchecked")
    List<OrderItem> items = result.getIn().getBody(List.class);
    assertThat(items).hasSize(1);
    assertThat(items.get(0)).isEqualTo(item);
  }

  @Test
  void testAggregateMultipleItems()
  {
    OrderItem item1 = createOrderItem("LAPTOP-1", "SUPPLIER_ELECTRONICS");
    OrderItem item2 = createOrderItem("MOUSE-1", "SUPPLIER_ELECTRONICS");
    Exchange oldExchange = createExchange(new ArrayList<>(List.of(item1)));
    Exchange newExchange = createExchange(item2);
    Exchange result = aggregator.aggregate(oldExchange, newExchange);
    @SuppressWarnings("unchecked")
    List<OrderItem> items = result.getIn().getBody(List.class);
    assertThat(items).hasSize(2);
    assertThat(items).contains(item1, item2);
  }

  private OrderItem createOrderItem(String productId, String supplierId)
  {
    return new OrderItem(productId, productId.split("-")[0], supplierId,
      1, BigDecimal.valueOf(150.0), "ORD-123", "123 Main St");
  }

  private Exchange createExchange(Object body)
  {
    Exchange exchange = new DefaultExchange(camelContext);
    exchange.getIn().setBody(body);
    return exchange;
  }
}