package ec.gob.batidos.domain.preparation;

import ec.gob.batidos.domain.delivery.PricingStrategy;
import ec.gob.batidos.domain.ingredients.IngredientStrategy;

public record RecipeDefinition(
    IngredientStrategy ingredientStrategy, PricingStrategy pricingStrategy) {

  public RecipeDefinition {
    if (ingredientStrategy == null || pricingStrategy == null) {
      throw new IllegalArgumentException(
          "Both ingredient and pricing strategies must be provided.");
    }
  }
}
