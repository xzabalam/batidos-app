package ec.gob.batidos.domain.ingredients.strategy;

import ec.gob.batidos.commons.enumerations.BaseShakeVolumeEnum;
import ec.gob.batidos.commons.enumerations.IngredientNamesEnum;
import ec.gob.batidos.commons.enumerations.IngredientsBaseSizeEnum;
import ec.gob.batidos.dataaccess.Inventory;
import ec.gob.batidos.dataaccess.entity.Ingredient;
import ec.gob.batidos.domain.ingredients.IngredientStrategy;
import ec.gob.batidos.domain.ingredients.util.BaseIngredientUtils;
import java.util.List;

public class MixedStrawberryBananaIngredientStrategy implements IngredientStrategy {

  @Override
  public List<Ingredient> getIngredientsNeeded(int volume) {
    List<Ingredient> ingredients = BaseIngredientUtils.baseIngredientsForVolume(volume);
    Ingredient strawberryIngredient =
        Inventory.getInstance().getByName(IngredientNamesEnum.STRAWBERRY.getName());
    Ingredient bananaIngredient =
        Inventory.getInstance().getByName(IngredientNamesEnum.BANANA.getName());

    double bananaMl = IngredientsBaseSizeEnum.BANANA.getBaseQuantity();
    double strawberryMl = IngredientsBaseSizeEnum.STRAWBERRY.getBaseQuantity();
    double bananaMangoMl = bananaMl + strawberryMl;
    double factor = (double) volume / BaseShakeVolumeEnum.DEFAULT.getVolume();
    double totalFruit = (bananaMangoMl / 2) * factor;
    double bananaPart = totalFruit * (bananaMl / bananaMangoMl);
    double mangoPart = totalFruit * (strawberryMl / bananaMangoMl);

    ingredients.add(
        new Ingredient.Builder()
            .name(strawberryIngredient.getName())
            .quantity(mangoPart)
            .unitPrice(strawberryIngredient.getUnitPrice())
            .baseIngredient(false)
            .measurementUnit(strawberryIngredient.getMeasurementUnit())
            .build());

    ingredients.add(
        new Ingredient.Builder()
            .name(bananaIngredient.getName())
            .quantity(bananaPart)
            .unitPrice(bananaIngredient.getUnitPrice())
            .baseIngredient(false)
            .measurementUnit(bananaIngredient.getMeasurementUnit())
            .build());

    return ingredients;
  }
}