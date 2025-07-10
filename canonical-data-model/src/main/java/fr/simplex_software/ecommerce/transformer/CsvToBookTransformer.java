package fr.simplex_software.ecommerce.transformer;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.apache.camel.*;

import java.math.*;
import java.util.*;

@ApplicationScoped
@Named("csvToBookTransformer")
public class CsvToBookTransformer implements Processor
{
  @Override
  public void process(Exchange exchange) throws Exception
  {
    @SuppressWarnings("unchecked")
    List<List<String>> csvData = exchange.getIn().getBody(List.class);
    if (csvData != null && !csvData.isEmpty()) {
      List<String> row = csvData.get(0);
      BookProduct book = new BookProduct(
        row.get(0), // isbn
        row.get(1), // book_title
        row.get(2), // author
        new BigDecimal(row.get(3)) // retail_price
      );
      exchange.getIn().setBody(book);
    }
  }
}
