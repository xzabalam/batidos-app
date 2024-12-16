package ec.gob.batidos.domain.ingredients.strategy;

import ec.gob.batidos.commons.enumerations.BaseShakeVolumeEnum;
import ec.gob.batidos.commons.enumerations.IngredientNamesEnum;
import ec.gob.batidos.commons.enumerations.IngredientsBaseSizeEnum;
import ec.gob.batidos.dataaccess.Inventory;
import ec.gob.batidos.dataaccess.entity.Ingredient;
import ec.gob.batidos.domain.ingredients.IngredientStrategy;
import ec.gob.batidos.domain.ingredients.util.BaseIngredientUtils;
import java.util.List;

public class MangoIngredientStrategy implements IngredientStrategy {

  @Override
  public List<Ingredient> getIngredientsNeeded(int volume) {
    List<Ingredient> ingredients = BaseIngredientUtils.baseIngredientsForVolume(volume);
    Ingredient ingredient = Inventory.getInstance().getByName(IngredientNamesEnum.MANGO.getName());

    double factor = (double) volume / BaseShakeVolumeEnum.DEFAULT.getVolume();
    double newQuantity = IngredientsBaseSizeEnum.MANGO.getBaseQuantity() * factor;

    ingredients.add(
        new Ingredient.Builder()
            .name(ingredient.getName())
            .quantity(newQuantity)
            .unitPrice(ingredient.getUnitPrice())
            .baseIngredient(false)
            .measurementUnit(ingredient.getMeasurementUnit())
            .build());

    return ingredients;
  }
}
