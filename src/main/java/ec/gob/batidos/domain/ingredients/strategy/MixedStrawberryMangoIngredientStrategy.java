package ec.gob.batidos.domain.ingredients.strategy;

import ec.gob.batidos.commons.enumerations.BaseShakeVolumeEnum;
import ec.gob.batidos.commons.enumerations.IngredientNamesEnum;
import ec.gob.batidos.commons.enumerations.IngredientsBaseSizeEnum;
import ec.gob.batidos.dataaccess.Inventory;
import ec.gob.batidos.dataaccess.entity.Ingredient;
import ec.gob.batidos.domain.ingredients.IngredientStrategy;
import ec.gob.batidos.domain.ingredients.util.BaseIngredientUtils;
import java.util.List;

public class MixedStrawberryMangoIngredientStrategy implements IngredientStrategy {

  @Override
  public List<Ingredient> getIngredientsNeeded(int volume) {
    List<Ingredient> ingredients = BaseIngredientUtils.baseIngredientsForVolume(volume);
    Ingredient strawberryIngredient =
        Inventory.getInstance().getByName(IngredientNamesEnum.STRAWBERRY.getName());
    Ingredient mangoIngredient =
        Inventory.getInstance().getByName(IngredientNamesEnum.MANGO.getName());

    double mangoMl = IngredientsBaseSizeEnum.MANGO.getBaseQuantity();
    double strawberryMl = IngredientsBaseSizeEnum.STRAWBERRY.getBaseQuantity();
    double strawberryMangoMl = mangoMl + strawberryMl;
    double factor = (double) volume / BaseShakeVolumeEnum.DEFAULT.getVolume();
    double totalFruit = (strawberryMangoMl / 2) * factor;
    double mangoPart = totalFruit * (mangoMl / strawberryMangoMl);
    double strawberryPart = totalFruit * (strawberryMl / strawberryMangoMl);

    ingredients.add(
        new Ingredient.Builder()
            .name(strawberryIngredient.getName())
            .quantity(strawberryPart)
            .unitPrice(strawberryIngredient.getUnitPrice())
            .baseIngredient(false)
            .measurementUnit(strawberryIngredient.getMeasurementUnit())
            .build());

    ingredients.add(
        new Ingredient.Builder()
            .name(mangoIngredient.getName())
            .quantity(mangoPart)
            .unitPrice(mangoIngredient.getUnitPrice())
            .baseIngredient(false)
            .measurementUnit(mangoIngredient.getMeasurementUnit())
            .build());

    return ingredients;
  }
}
