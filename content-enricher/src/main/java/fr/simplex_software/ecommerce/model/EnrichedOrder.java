package fr.simplex_software.ecommerce.model;

import java.time.*;
import java.util.*;

public record EnrichedOrder(
  String orderId,
  String customerId,
  String shippingAddress,
  LocalDateTime orderDate,
  CustomerDetails customerDetails,
  List<EnrichedOrderItem> enrichedItems)
{
<<<<<<< Updated upstream
  public EnrichedOrder withEnrichedItems(List<EnrichedOrderItem> enrichedItems)
=======
  /*public String orderId()
>>>>>>> Stashed changes
  {
    return new EnrichedOrder(orderId, customerId, shippingAddress, orderDate, customerDetails, enrichedItems);
  }

  public EnrichedOrder withCustomerDetails(CustomerDetails customerDetails)
  {
<<<<<<< Updated upstream
    return new EnrichedOrder(orderId, customerId, shippingAddress, orderDate, customerDetails, enrichedItems);
  }
=======
    return order.customerId();
  }

  public String shippingAddress()
  {
    return order.shippingAddress();
  }

  public LocalDateTime orderDate()
  {
    return order.orderDate();
  }

  public List<OrderItem> orderItems()
  {
    return order.items();
  }*/
>>>>>>> Stashed changes
}
