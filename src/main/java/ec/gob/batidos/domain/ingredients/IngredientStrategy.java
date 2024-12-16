package ec.gob.batidos.domain.ingredients;

import ec.gob.batidos.dataaccess.entity.Ingredient;
import java.util.List;

public interface IngredientStrategy {
  // Returns the required ingredients for a given volume in a map: ingredientName -> amountNeeded
  List<Ingredient> getIngredientsNeeded(int volume);
}
