package ec.gob.batidos.domain.preparation;

import ec.gob.batidos.domain.delivery.strategy.*;
import ec.gob.batidos.domain.ingredients.strategy.*;
import java.util.HashMap;
import java.util.Map;

public class ShakeFactory {
  private final Map<String, RecipeDefinition> recipes = new HashMap<>();

  public ShakeFactory() {
    // Registro de todas las recetas existentes dentro del constructor
    registerRecipe(
        "strawberry",
        new RecipeDefinition(
            new StrawberryIngredientStrategy(), new StrawberryPricingStrategy(20)));
    registerRecipe(
        "banana",
        new RecipeDefinition(new BananaIngredientStrategy(), new BananaPricingStrategy(10)));
    registerRecipe(
        "mango", new RecipeDefinition(new MangoIngredientStrategy(), new MangoPricingStrategy(15)));
    registerRecipe(
        "mixed-strawberry-banana",
        new RecipeDefinition(
            new MixedStrawberryBananaIngredientStrategy(),
            new MixedStrawberryBananaPricingStrategy(30)));
    registerRecipe(
        "mixed-banana-mango",
        new RecipeDefinition(
            new MixedBananaMangoIngredientStrategy(), new MixedBananaMangoPricingStrategy(30)));
    registerRecipe(
        "mixed-strawberry-mango",
        new RecipeDefinition(
            new MixedStrawberryMangoIngredientStrategy(),
            new MixedStrawberryMangoPricingStrategy(30)));
  }

  public void registerRecipe(String type, RecipeDefinition definition) {
    recipes.put(type, definition);
  }

  public Shake createShake(String type) {
    RecipeDefinition def = recipes.get(type);
    if (def == null) return null;
    return new Shake(def);
  }
}
