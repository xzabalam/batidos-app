package ec.gob.batidos.commons.enumerations;

public enum IngredientsBaseSizeEnum {
  MANGO(140.0),
  BANANA(120.0),
  STRAWBERRY(100.0);

  private final double baseQuantity;

  IngredientsBaseSizeEnum(double baseQuantity) {
    this.baseQuantity = baseQuantity;
  }

  public double getBaseQuantity() {
    return baseQuantity;
  }
}
