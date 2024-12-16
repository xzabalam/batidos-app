package ec.gob.batidos.domain.delivery;

import ec.gob.batidos.commons.enumerations.ShakeSizeEnum;
import ec.gob.batidos.dataaccess.entity.Ingredient;
import ec.gob.batidos.domain.delivery.util.BasePriceUtil;
import java.util.List;

public interface PricingStrategy {
  double getPrice(ShakeSizeEnum size, List<Ingredient> ingredients);

  // Método default para la lógica común de cálculo del precio
  default double calculateDefaultPrice(
      ShakeSizeEnum size, List<Ingredient> ingredients, double percentage) {
    // Validar porcentaje
    if (percentage < 0) {
      throw new IllegalArgumentException("Percentage must be non-negative.");
    }

    // Calcular el costo total de los ingredientes
    double totalIngredientCost =
        ingredients.stream()
            .mapToDouble(ingredient -> ingredient.calculateCostForUsage(ingredient.getQuantity()))
            .sum();

    // Aplicar el margen porcentual
    double price = totalIngredientCost + (totalIngredientCost * (percentage / 100.0));

    // Multiplicar por el factor de tamaño y redondear a 2 decimales
    return Math.round(price * BasePriceUtil.sizeFactor(size) * 100.0) / 100.0;
  }
}
