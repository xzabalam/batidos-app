package ec.gob.batidos.dataaccess.entity;

public class Ingredient {
  private String name;
  private double unitPrice; // Precio por unidad
  private double quantity; // Cantidad disponible
  private String measurementUnit; // Unidad de medida (litros, gramos, etc.)
  private boolean baseIngredient;

  // Constructor privado para el patrón Builder
  private Ingredient(Builder builder) {
    this.name = builder.name;
    this.unitPrice = builder.unitPrice;
    this.quantity = builder.quantity;
    this.measurementUnit = builder.measurementUnit;
    this.baseIngredient = builder.baseIngredient;
  }

  // Constructor público (opcional, por compatibilidad)
  public Ingredient() {}

  // Getters y Setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getMeasurementUnit() {
    return measurementUnit;
  }

  public void setMeasurementUnit(String measurementUnit) {
    this.measurementUnit = measurementUnit;
  }

  public boolean isBaseIngredient() {
    return baseIngredient;
  }

  public void setBaseIngredient(boolean baseIngredient) {
    this.baseIngredient = baseIngredient;
  }

  // Método para deducir una cantidad del inventario
  public void deduct(double amount) {
    if (amount > quantity) {
      throw new IllegalArgumentException("Not enough stock for " + name);
    }
    this.quantity -= amount;
  }

  // Método para calcular el costo total del inventario actual
  public double calculateTotalCost() {
    return quantity * unitPrice;
  }

  // Método para calcular el costo de una cantidad específica utilizada
  public double calculateCostForUsage(double usedQuantity) {
    if (usedQuantity > quantity) {
      throw new IllegalArgumentException("Not enough stock for " + name);
    }
    return usedQuantity * unitPrice;
  }

  // Método para actualizar la cantidad en inventario después de usar una cantidad
  public void updateQuantityAfterUsage(double usedQuantity) {
    if (usedQuantity > quantity) {
      throw new IllegalArgumentException("Not enough stock for " + name);
    }
    this.quantity -= usedQuantity;
  }

  // Método toString para representación del ingrediente
  @Override
  public String toString() {
    return String.format(
        "%s: %.2f %s @ $%.2f/%s (Total Cost: $%.2f)",
        name, quantity, measurementUnit, unitPrice, measurementUnit, calculateTotalCost());
  }

  // Implementación del patrón Builder
  public static class Builder {
    private String name;
    private double unitPrice;
    private double quantity;
    private String measurementUnit;
    private boolean baseIngredient;

    public Builder() {}

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder unitPrice(double unitPrice) {
      this.unitPrice = unitPrice;
      return this;
    }

    public Builder quantity(double quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder measurementUnit(String measurementUnit) {
      this.measurementUnit = measurementUnit;
      return this;
    }

    public Builder baseIngredient(boolean baseIngredient) {
      this.baseIngredient = baseIngredient;
      return this;
    }

    public Ingredient build() {
      return new Ingredient(this);
    }
  }
}
