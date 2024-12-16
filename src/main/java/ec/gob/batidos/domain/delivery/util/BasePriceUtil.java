package ec.gob.batidos.domain.delivery.util;

import ec.gob.batidos.commons.enumerations.ShakeSizeEnum;

public final class BasePriceUtil {
  private BasePriceUtil() {
    // no implementation
  }

  public static double sizeFactor(ShakeSizeEnum size) {
    return switch (size) {
      case SMALL -> 0.7;
      case MEDIUM -> 1.0;
      case LARGE -> 1.5;
    };
  }
}
