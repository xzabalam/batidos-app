package ec.gob.batidos.ui;

import ec.gob.batidos.commons.enumerations.ShakeSizeEnum;
import ec.gob.batidos.dataaccess.Inventory;
import ec.gob.batidos.domain.preparation.Shake;
import ec.gob.batidos.domain.preparation.ShakeFactory;
import ec.gob.batidos.reports.SalesReport;
import java.util.Scanner;

/** Esta aplicación le permite a un vendedor vender sus batidos */
public class BatidosApp {
  public static void main(String[] args) {
    Inventory inventory = Inventory.getInstance();
    SalesReport salesReport = new SalesReport();
    ShakeFactory factory = new ShakeFactory();

    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    System.out.println("Welcome to the Fruit Shake Seller App!");

    while (running) {
      System.out.println("\nMenu:");
      System.out.println("1 - List current inventory");
      System.out.println("2 - Sell a shake");
      System.out.println("3 - Show daily sales report");
      System.out.println("4 - Exit");

      System.out.print("Please choose an option: ");
      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1":
          inventory.listInventory();
          inventory.showLowInventoryWarning();
          break;
        case "2":
          sellShakeFlow(scanner, inventory, salesReport, factory);
          break;
        case "3":
          salesReport.printReport();
          break;
        case "4":
          running = false;
          System.out.println("Exiting application. Goodbye!");
          break;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }

    scanner.close();
  }

  private static void sellShakeFlow(
      Scanner scanner, Inventory inventory, SalesReport salesReport, ShakeFactory factory) {
    System.out.println("Please choose shake type:");
    System.out.println(
        "Options: 'strawberry', 'banana', 'mango', 'mixed-strawberry-banana', 'mixed-banana-mango', 'mixed-strawberry-mango'");
    System.out.print("Type: ");
    String typeInput = scanner.nextLine().trim().toLowerCase();

    Shake shake = factory.createShake(typeInput);
    if (shake == null) {
      System.out.println("Invalid shake type. Sale canceled.");
      return;
    }

    System.out.println("Please choose size:");
    System.out.println("Options: 'small' (200 ml), 'medium' (300 ml), 'large' (500 ml)");
    System.out.print("Size: ");
    String sizeInput = scanner.nextLine().trim().toLowerCase();

    ShakeSizeEnum size = ShakeSizeEnum.fromString(sizeInput);
    if (size == null) {
      System.out.println("Invalid size. Sale canceled.");
      return;
    }

    double price =
        shake.getPricingStrategy().getPrice(size, shake.getIngredientsNeeded(size.getVolume()));

    if (inventory.sellShake(shake, size)) {
      salesReport.recordSale(typeInput, sizeInput, price);
      System.out.println("Sold one " + sizeInput + " " + typeInput + " shake for $" + price);
    } else {
      System.out.println("Not enough ingredients to sell this shake.");
    }
  }
}
