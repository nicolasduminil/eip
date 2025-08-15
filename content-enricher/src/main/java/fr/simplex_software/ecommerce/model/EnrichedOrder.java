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
  public EnrichedOrder withCustomerDetails(CustomerDetails customerDetails)
  {
    return new EnrichedOrder(orderId, customerId, shippingAddress, orderDate, customerDetails, enrichedItems);
  }
}
