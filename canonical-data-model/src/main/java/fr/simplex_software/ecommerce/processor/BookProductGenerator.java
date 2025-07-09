package fr.simplex_software.ecommerce.processor;

import fr.simplex_software.ecommerce.model.*;
import jakarta.enterprise.context.*;

@ApplicationScoped
public class BookProductGenerator implements ProductGenerator
{
  @Override
  public String generateProduct()
  {
    return "978-0134685991,Effective Java,Joshua Bloch,45.99";
  }

  @Override
  public SupplierType getSupplierType()
  {
    return SupplierType.BOOKS;
  }
}
