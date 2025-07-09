package fr.simplex_software.ecommerce.transformer;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.util.*;

@ApplicationScoped
@Named("bookTransformer")
public class BookTransformer extends ProductTransformer
{
  @Override
  protected Product transform(Object sourceProduct)
  {
    BookProduct book = (BookProduct) sourceProduct;
    return new Product(
      book.isbn(),
      book.bookTitle(),
      book.retailPrice(),
      "Books",
      Map.of("author", book.author()),
      "SUPPLIER_C"
    );
  }
}
