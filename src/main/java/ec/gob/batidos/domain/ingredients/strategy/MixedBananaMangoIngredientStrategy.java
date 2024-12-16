package ec.gob.batidos.domain.ingredients.strategy;

import ec.gob.batidos.commons.enumerations.BaseShakeVolumeEnum;
import ec.gob.batidos.commons.enumerations.IngredientNamesEnum;
import ec.gob.batidos.commons.enumerations.IngredientsBaseSizeEnum;
import ec.gob.batidos.dataaccess.Inventory;
import ec.gob.batidos.dataaccess.entity.Ingredient;
import ec.gob.batidos.domain.ingredients.IngredientStrategy;
import ec.gob.batidos.domain.ingredients.util.BaseIngredientUtils;
import java.util.List;

public class MixedBananaMangoIngredientStrategy implements IngredientStrategy {

  @Override
  public List<Ingredient> getIngredientsNeeded(int volume) {
    List<Ingredient> ingredients = BaseIngredientUtils.baseIngredientsForVolume(volume);
    Ingredient mangoIngredient =
        Inventory.getInstance().getByName(IngredientNamesEnum.MANGO.getName());
    Ingredient bananaIngredient =
        Inventory.getInstance().getByName(IngredientNamesEnum.BANANA.getName());

    double bananaMl = IngredientsBaseSizeEnum.BANANA.getBaseQuantity();
    double mangoMl = IngredientsBaseSizeEnum.MANGO.getBaseQuantity();
    double bananaMangoMl = bananaMl + mangoMl;
    double factor = (double) volume / BaseShakeVolumeEnum.DEFAULT.getVolume();
    double totalFruit = (bananaMangoMl / 2) * factor; // (120+140)/2=130g per 100ml
    double bananaPart = totalFruit * (bananaMl / bananaMangoMl);
    double mangoPart = totalFruit * (mangoMl / bananaMangoMl);

    ingredients.add(
        new Ingredient.Builder()
            .name(mangoIngredient.getName())
            .quantity(mangoPart)
            .unitPrice(mangoIngredient.getUnitPrice())
            .baseIngredient(false)
            .measurementUnit(mangoIngredient.getMeasurementUnit())
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
