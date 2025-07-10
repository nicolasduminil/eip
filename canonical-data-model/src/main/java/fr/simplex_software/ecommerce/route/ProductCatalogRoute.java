package fr.simplex_software.ecommerce.route;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import org.apache.camel.builder.*;
import org.apache.camel.model.dataformat.*;

@ApplicationScoped
public class ProductCatalogRoute extends RouteBuilder
{
  @Override
  public void configure() throws Exception
  {
    from("timer:generator?period=15000")
      .routeId("dataGenerationRoute")
      .autoStartup(false)
      .process("productGenerator")
      .to("direct:processProduct");
    from("direct:processProduct")
      .routeId("dataProcessingRoute")
      .choice()
        .when(header("supplierType").isEqualTo("ELECTRONICS"))
          .unmarshal().json(JsonLibrary.Jackson, ElectronicsProduct.class)
          .process("electronicsTransformer")
        .when(header("supplierType").isEqualTo("FASHION"))
          .unmarshal().jacksonXml(FashionProduct.class)
          .process("fashionTransformer")
        .when(header("supplierType").isEqualTo("BOOKS"))
          .unmarshal().csv()
          .process("csvToBookTransformer")
          .process("bookTransformer")
      .end()
      .to("log:canonical-product?showBody=true");
  }
}
