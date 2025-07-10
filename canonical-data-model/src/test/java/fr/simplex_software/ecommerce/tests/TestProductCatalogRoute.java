package fr.simplex_software.ecommerce.tests;

import fr.simplex_software.ecommerce.model.*;
import io.quarkus.test.junit.*;
import jakarta.inject.*;
import org.apache.camel.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TestProductCatalogRoute //extends CamelQuarkusTestSupport
{
  @Inject
  CamelContext camelContext;
  @Inject
  ProducerTemplate producerTemplate;

  @Test
  public void testTransformElectronicsProduct() throws Exception
  {
    String electronicsProduct = """
      {
        "itemId": "ELEC001",
        "name": "Gaming Laptop",
        "cost": 1299.99,
        "specs": {"cpu": "Intel i7", "ram": "16GB"}
      }
      """;
    Product result = producerTemplate.requestBodyAndHeader(
      "direct:processProduct",
      electronicsProduct,
      "supplierType", SupplierType.ELECTRONICS,
      Product.class
    );
    assertEquals("ELEC001", result.id());
    assertEquals("Electronics", result.category());
    assertEquals("SUPPLIER_A", result.supplierId());
  }

  @Test
  public void testTransformBookProduct() throws Exception
  {
    String bookProduct = "978-0134685991,Effective Java,Joshua Bloch,45.99";
    Product result = producerTemplate.requestBodyAndHeader(
      "direct:processProduct",
      bookProduct,
      "supplierType", SupplierType.BOOKS,
      Product.class
    );
    assertEquals("978-0134685991", result.id());
    assertEquals("Books", result.category());
    assertEquals("SUPPLIER_C", result.supplierId());
  }

  @Test
  public void testTransformFashionProduct()
  {
    String fashionProduct = """
      <product>
        <sku>FASH002</sku>
        <title>Designer Jacket</title>
        <price>299.50</price>
        <variants>
          <variant size="M" color="Blue"/>
        </variants>
      </product>
      """;
    Product result = producerTemplate.requestBodyAndHeader(
      "direct:processProduct",
      fashionProduct,
      "supplierType", SupplierType.FASHION,
      Product.class
    );
    assertEquals("FASH002", result.id());
    assertEquals("Fashion", result.category());
    assertEquals("SUPPLIER_B", result.supplierId());
  }
}
