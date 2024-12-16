package ec.gob.batidos.commons.enumerations;

public enum IngredientNamesEnum {
  BANANA("banana"),
  STRAWBERRY("strawberry"),
  MANGO("mango"),
  MIXED_FRUIT_JUICE("mixed_fruit_juice"),
  ICE("ice"),
  CONDENSED_MILK("condensed_milk"),
  SUGAR("sugar");

  private final String name;

  IngredientNamesEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
