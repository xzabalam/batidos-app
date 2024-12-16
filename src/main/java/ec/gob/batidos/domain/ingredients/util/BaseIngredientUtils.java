package ec.gob.batidos.domain.ingredients.util;

import ec.gob.batidos.dataaccess.Inventory;
import ec.gob.batidos.dataaccess.entity.Ingredient;
import java.util.ArrayList;
import java.util.List;

public final class BaseIngredientUtils {

  // Base recipe per 100 ml
  public static final double FRUIT_JUICE_PER_100ML = 50.0;
  public static final double ICE_PER_100ML = 30.0;
  public static final double MILK_PER_100ML = 20.0;
  public static final double SUGAR_PER_100ML = 8.0;

  private BaseIngredientUtils() {
    // Prevent instantiation
  }

  public static List<Ingredient> baseIngredientsForVolume(int volume) {
    List<Ingredient> ingredients = new ArrayList<>();
    double factor = volume / 100.0;

    Inventory.getInstance()
        .getIngredients()
        .forEach(
            ingredient -> {
              double baseQuantity = getBaseQuantity(ingredient.getName(), factor);

              if (baseQuantity > 0) {
                // Calcular el costo total basado en la cantidad base
                double costForBaseQuantity = ingredient.calculateCostForUsage(baseQuantity);

                // Crear el nuevo ingrediente con la cantidad ajustada
                Ingredient adjustedIngredient =
                    new Ingredient.Builder()
                        .name(ingredient.getName())
                        .quantity(baseQuantity)
                        .unitPrice(costForBaseQuantity / baseQuantity)
                        .measurementUnit(ingredient.getMeasurementUnit())
                        .baseIngredient(true)
                        .build();

                ingredients.add(adjustedIngredient);
              }
            });

    return ingredients;
  }

  private static double getBaseQuantity(String name, double factor) {
    return switch (name) {
      case "mixed_fruit_juice" -> FRUIT_JUICE_PER_100ML * factor;
      case "ice" -> ICE_PER_100ML * factor;
      case "condensed_milk" -> MILK_PER_100ML * factor;
      case "sugar" -> SUGAR_PER_100ML * factor;
      default -> 0.0;
    };
  }
}
