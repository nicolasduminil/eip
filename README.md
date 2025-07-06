# E-Commerce Order Processing with Apache Camel

This project demonstrates Apache Camel's **Splitter** and **Aggregator** patterns using a realistic e-commerce scenario.

## Scenario
An e-commerce platform processes orders that contain items from multiple suppliers. The system:
1. **Splits** orders into individual items
2. **Aggregates** items by supplier and shipping address to optimize shipments
3. Creates consolidated shipments for cost efficiency

## Architecture

### Flow
```
Order → Splitter → Individual Items → Aggregator → Optimized Shipments
```

### Key Components
- **OrderSplitter**: Breaks orders into individual items with context
- **ShipmentAggregator**: Groups items by supplier + shipping address
- **OrderGenerator**: Creates realistic sample orders

### Business Value
- **Cost Reduction**: Fewer shipments per supplier
- **Efficiency**: Consolidated deliveries
- **Scalability**: Handles multiple suppliers automatically

## Running the Application

```bash
mvn quarkus:dev
```

The application will:
- Generate sample orders every 10 seconds
- Split orders by supplier
- Aggregate items into optimized shipments
- Log the entire process

## Sample Output
```
=== Processing new order ===
Generated order: Order{orderId='ORD-123', customerId='CUST-456', items=5}
Processing item: OrderItem{productId='LAPTOP-1', supplierId='SUPPLIER_ELECTRONICS', quantity=2}
=== SHIPMENT CREATED ===
Shipment: Shipment{id='SHIP-SUPPLIER_ELECTRONICS-123', supplier='SUPPLIER_ELECTRONICS', items=2, value=250.50}
HIGH VALUE shipment (250.50€) - Priority processing
```

## Key Patterns Demonstrated
- **Splitter Pattern**: `split(body())` breaks orders into items
- **Aggregator Pattern**: Groups by `aggregationKey` (supplier + address)
- **Content-Based Router**: Routes high-value shipments differently