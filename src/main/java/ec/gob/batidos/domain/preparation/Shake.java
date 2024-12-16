package ec.gob.batidos.domain.preparation;

import ec.gob.batidos.dataaccess.entity.Ingredient;
import ec.gob.batidos.domain.delivery.PricingStrategy;
import java.util.List;

public class Shake {
  private final RecipeDefinition recipeDefinition;

  public Shake(RecipeDefinition recipeDefinition) {
    this.recipeDefinition = recipeDefinition;
  }

  public List<Ingredient> getIngredientsNeeded(int volume) {
    return recipeDefinition.ingredientStrategy().getIngredientsNeeded(volume);
  }

  public PricingStrategy getPricingStrategy() {
    return recipeDefinition.pricingStrategy();
  }
}
