package ec.gob.batidos.reports;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalesReport {
  private final List<Sale> sales;

  public SalesReport() {
    this.sales = new ArrayList<>();
  }

  public void recordSale(String type, String size, double price) {
    sales.add(new Sale(type, size, price, LocalDateTime.now()));
  }

  public void printReport() {
    System.out.println("Daily Sales Report:");
    if (sales.isEmpty()) {
      System.out.println("No sales recorded today.");
      return;
    }

    double total = 0.0;
    for (Sale s : sales) {
      System.out.println(" - " + s.toString());
      total += s.price();
    }
    System.out.println("Total income: $" + total);
  }
}
