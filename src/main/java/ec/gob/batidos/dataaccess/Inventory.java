package ec.gob.batidos.dataaccess;

import ec.gob.batidos.commons.enumerations.IngredientNamesEnum;
import ec.gob.batidos.commons.enumerations.ShakeSizeEnum;
import ec.gob.batidos.commons.exception.InventoryException;
import ec.gob.batidos.dataaccess.entity.Ingredient;
import ec.gob.batidos.domain.preparation.Shake;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Inventory {

  private final List<Ingredient> ingredients;

  // Constructor privado para inicializar el inventario
  private Inventory() {
    ingredients = new ArrayList<>();
    ingredients.add(
        new Ingredient.Builder()
            .name("strawberry")
            .quantity(2000.0)
            .unitPrice(0.5)
            .measurementUnit("grams")
            .baseIngredient(true)
            .build());
    ingredients.add(
        new Ingredient.Builder()
            .name("banana")
            .quantity(2000.0)
            .unitPrice(0.3)
            .measurementUnit("grams")
            .baseIngredient(true)
            .build());
    ingredients.add(
        new Ingredient.Builder()
            .name("mango")
            .quantity(2000.0)
            .unitPrice(0.6)
            .measurementUnit("grams")
            .baseIngredient(true)
            .build());
    ingredients.add(
        new Ingredient.Builder()
            .name("mixed_fruit_juice")
            .quantity(3000.0)
            .unitPrice(0.2)
            .measurementUnit("ml")
            .baseIngredient(true)
            .build());
    ingredients.add(
        new Ingredient.Builder()
            .name("ice")
            .quantity(2000.0)
            .unitPrice(0.1)
            .measurementUnit("grams")
            .baseIngredient(true)
            .build());
    ingredients.add(
        new Ingredient.Builder()
            .name("condensed_milk")
            .quantity(2000.0)
            .unitPrice(0.8)
            .measurementUnit("ml")
            .baseIngredient(true)
            .build());
    ingredients.add(
        new Ingredient.Builder()
            .name("sugar")
            .quantity(1000.0)
            .unitPrice(0.05)
            .measurementUnit("grams")
            .baseIngredient(true)
            .build());
  }

  // Método para obtener la instancia única del inventario (Singleton)
  public static Inventory getInstance() {
    return InstanceHolder.instance;
  }

  // Obtener todos los ingredientes
  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void listInventory() {
    System.out.println("Current inventory:");
    ingredients.forEach(System.out::println);
  }

  public boolean sellShake(Shake shake, ShakeSizeEnum size) {
    int volume = size.getVolume();
    List<Ingredient> needed = shake.getIngredientsNeeded(volume);

    // Check availability
    AtomicInteger lowIngredientsCount = new AtomicInteger();
    needed.forEach(
        ingredient -> {
          if (!hasEnough(ingredient.getName(), ingredient.getQuantity())) {
            lowIngredientsCount.getAndIncrement();
          }
        });

    if (lowIngredientsCount.get() > 0) {
      return false;
    }

    // Deduct
    needed.forEach(ingredient -> deductIngredient(ingredient.getName(), ingredient.getQuantity()));
    return true;
  }

  public void showLowInventoryWarning() {
    Inventory inventory = Inventory.getInstance();

    // Cantidades necesarias para los ingredientes
    double neededJuice = 600.0;
    double neededIce = 360.0;
    double neededMilk = 240.0;
    double neededSugar = 96.0;
    double neededStrawberry = 1200.0;
    double neededBanana = 1440.0;
    double neededMango = 1680.0;

    // Verificar si hay suficiente cantidad de cada ingrediente
    boolean isLowInventory =
        inventory.hasEnough(IngredientNamesEnum.MIXED_FRUIT_JUICE.getName(), neededJuice)
            || inventory.hasEnough(IngredientNamesEnum.ICE.getName(), neededIce)
            || inventory.hasEnough(IngredientNamesEnum.CONDENSED_MILK.getName(), neededMilk)
            || inventory.hasEnough(IngredientNamesEnum.SUGAR.getName(), neededSugar)
            || inventory.hasEnough(IngredientNamesEnum.STRAWBERRY.getName(), neededStrawberry)
            || inventory.hasEnough(IngredientNamesEnum.BANANA.getName(), neededBanana)
            || inventory.hasEnough(IngredientNamesEnum.MANGO.getName(), neededMango);

    // Mostrar advertencia si algún ingrediente está por debajo del nivel necesario
    if (isLowInventory) {
      System.out.println(
          "Warning: Some ingredients are below the level required for 4 more medium shakes.");
    }
  }

  // Verificar si hay suficiente cantidad de un ingrediente
  public boolean hasEnough(String name, double amount) {
    return ingredients.stream()
        .filter(ingredient -> ingredient.getName().equals(name))
        .noneMatch(ingredient -> ingredient.getQuantity() >= amount);
  }

  // Deducir una cantidad específica de un ingrediente
  public void deductIngredient(String name, double amount) {
    ingredients.stream()
        .filter(ingredient -> ingredient.getName().equals(name))
        .findFirst()
        .ifPresentOrElse(
            ingredient -> ingredient.deduct(amount),
            () -> {
              throw new IllegalArgumentException("Ingredient not found: " + name);
            });
  }

  // Calcular el costo total de una lista de ingredientes
  public double calculateTotalCost(List<Ingredient> neededIngredients) {
    return neededIngredients.stream()
        .mapToDouble(ingredient -> ingredient.calculateCostForUsage(ingredient.getQuantity()))
        .sum();
  }

  // Obtener un ingrediente por su nombre
  public Ingredient getByName(String name) {
    return ingredients.stream()
        .filter(
            ingredient ->
                name.trim()
                    .toLowerCase(Locale.ROOT)
                    .equals(ingredient.getName().trim().toLowerCase(Locale.ROOT)))
        .findFirst()
        .orElseThrow(() -> new InventoryException("Ingredient not found: " + name));
  }

  // Imprimir el inventario actual
  public void printInventory() {
    System.out.println("Current Inventory:");
    ingredients.forEach(System.out::println);
  }

  // Clase estática para mantener una única instancia de Inventory (Singleton)
  private static final class InstanceHolder {
    private static final Inventory instance = new Inventory();
  }
}
