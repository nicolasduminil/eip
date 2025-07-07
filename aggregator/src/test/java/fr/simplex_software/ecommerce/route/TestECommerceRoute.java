package fr.simplex_software.ecommerce.route;

import fr.simplex_software.ecommerce.model.*;
import io.quarkus.test.junit.*;
import jakarta.inject.*;
import org.apache.camel.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;

@QuarkusTest
class TestECommerceRoute
{
  @Inject
  CamelContext camelContext;
  @Inject
  ProducerTemplate producerTemplate;

  @Test
  void testProcessOrderItems() throws Exception
  {
    OrderItem item = createOrderItem("LAPTOP-1",
      "SUPPLIER_ELECTRONICS", "123 Main St");
    producerTemplate.sendBody("direct:aggregateBySupplier", item);
    TimeUnit.SECONDS.sleep(1);
    assertThat(item.supplierId()).isEqualTo("SUPPLIER_ELECTRONICS");
    assertThat(item.shippingAddress()).isEqualTo("123 Main St");
  }

  @Test
  void testProcessShipment() throws Exception
  {
    Shipment shipment = new Shipment("SUPPLIER_ELECTRONICS", "123 Main St",
      List.of(createOrderItem("LAPTOP-1",
        "SUPPLIER_ELECTRONICS", "123 Main St")));
    producerTemplate.sendBody("direct:processShipment", shipment);
    TimeUnit.SECONDS.sleep(1);
    assertThat(shipment.totalValue()).isGreaterThan(BigDecimal.valueOf(100));
  }

  private OrderItem createOrderItem(String productId, String supplierId, String address)
  {
    return new OrderItem(productId, productId.split("-")[0], supplierId, 1,
      BigDecimal.valueOf(150.0), "ORD-123", address);
  }
}