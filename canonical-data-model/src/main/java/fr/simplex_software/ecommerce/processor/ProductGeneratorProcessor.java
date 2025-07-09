package fr.simplex_software.ecommerce.processor;

import jakarta.enterprise.context.*;
import jakarta.enterprise.inject.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.util.*;

@ApplicationScoped
@Named("productGenerator")
public class ProductGeneratorProcessor implements Processor
{
  @Inject
  Instance<ProductGenerator> generators;
  private final Random random = new Random();

  @Override
  public void process(Exchange exchange) throws Exception
  {
    List<ProductGenerator> generatorList = generators.stream().toList();
    if (generatorList.isEmpty())
      throw new IllegalStateException("No ProductGenerator implementations found");
    ProductGenerator selectedGenerator = generatorList.get(random.nextInt(generatorList.size()));
    exchange.getIn().setHeader("supplierType", selectedGenerator.getSupplierType());
    exchange.getIn().setBody(selectedGenerator.generateProduct());
  }
}
