package fr.simplex_software.ecommerce.processor;

import fr.simplex_software.ecommerce.model.Order;
import fr.simplex_software.ecommerce.model.OrderItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@ApplicationScoped
@Named("orderGenerator")
public class OrderGenerator implements Processor
{
  private final Random random = new Random();
  private final String[] suppliers = {"SUPPLIER_ELECTRONICS", "SUPPLIER_BOOKS", "SUPPLIER_CLOTHING"};
  private final String[] addresses = {"123 Main St, Paris", "456 Oak Ave, Lyon", "789 Pine Rd, Marseille"};

  @Override
  public void process(Exchange exchange) throws Exception
  {
    String orderId = "ORD-" + System.currentTimeMillis();
    String customerId = "CUST-" + random.nextInt(1000);
    String address = addresses[random.nextInt(addresses.length)];

    List<OrderItem> items = List.of(
      createRandomItem("LAPTOP", suppliers[0]),
      createRandomItem("MOUSE", suppliers[0]),
      createRandomItem("JAVA_BOOK", suppliers[1]),
      createRandomItem("SHIRT", suppliers[2]),
      createRandomItem("PANTS", suppliers[2])
    );

    Order order = new Order(orderId, customerId, address, items);
    exchange.getIn().setBody(order);
  }

  private OrderItem createRandomItem(String productName, String supplierId)
  {
    return new OrderItem(
      productName + "-" + random.nextInt(100),
      productName,
      supplierId,
      random.nextInt(3) + 1,
      BigDecimal.valueOf(random.nextDouble() * 100 + 10)
    );
  }
}