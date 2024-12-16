package ec.gob.batidos.domain.delivery.strategy;

import ec.gob.batidos.commons.enumerations.ShakeSizeEnum;
import ec.gob.batidos.dataaccess.entity.Ingredient;
import ec.gob.batidos.domain.delivery.PricingStrategy;
import java.util.List;

public class MangoPricingStrategy implements PricingStrategy {
  private final double percentage; // Porcentaje adicional para el precio final

  // Constructor para definir el margen porcentual
  public MangoPricingStrategy(double percentage) {
    if (percentage < 0) {
      throw new IllegalArgumentException("Percentage must be non-negative.");
    }
    this.percentage = percentage;
  }

  @Override
  public double getPrice(ShakeSizeEnum size, List<Ingredient> ingredients) {
    // Delegar el cálculo al método default de la interfaz
    return calculateDefaultPrice(size, ingredients, percentage);
  }
}
