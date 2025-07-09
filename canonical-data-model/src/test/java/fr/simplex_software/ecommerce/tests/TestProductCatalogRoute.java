package fr.simplex_software.ecommerce.tests;

import fr.simplex_software.ecommerce.model.*;
import io.quarkus.test.junit.*;
import org.apache.camel.builder.*;
import org.apache.camel.quarkus.test.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TestProductCatalogRoute extends CamelQuarkusTestSupport
{
  @Test
  void testTransformElectronicsProduct() throws Exception
  {
    ElectronicsProduct electronics = new ElectronicsProduct(
      "ELEC001", "Gaming Laptop",
      new BigDecimal("1299.99"),
      Map.of("cpu", "Intel i7")
    );
    Product result = template.requestBodyAndHeader(
      "direct:test-electronics",
      electronics,
      "supplierType", SupplierType.ELECTRONICS,
      Product.class
    );
    assertEquals("ELEC001", result.id());
    assertEquals("Electronics", result.category());
    assertEquals("SUPPLIER_A", result.supplierId());
  }

  @Test
  void testTransformBookProduct() throws Exception
  {
    BookProduct book = new BookProduct(
      "978-0134685991", "Effective Java",
      "Joshua Bloch", new BigDecimal("45.99")
    );
    Product result = template.requestBodyAndHeader(
      "direct:test-books",
      book,
      "supplierType", SupplierType.BOOKS,
      Product.class
    );
    assertEquals("978-0134685991", result.id());
    assertEquals("Books", result.category());
    assertEquals("SUPPLIER_C", result.supplierId());
  }

  @Override
  protected RouteBuilder createRouteBuilder()
  {
    return new RouteBuilder()
    {
      @Override
      public void configure()
      {
        from("direct:test-electronics")
          .process("electronicsTransformer");

        from("direct:test-books")
          .process("bookTransformer");
      }
    };
  }
}
