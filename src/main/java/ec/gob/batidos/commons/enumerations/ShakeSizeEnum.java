package ec.gob.batidos.commons.enumerations;

import java.util.HashMap;
import java.util.Map;

public enum ShakeSizeEnum {
  SMALL(200),
  MEDIUM(300),
  LARGE(500);

  private static final Map<String, ShakeSizeEnum> sizeMap = new HashMap<>();

  static {
    sizeMap.put("small", SMALL);
    sizeMap.put("medium", MEDIUM);
    sizeMap.put("large", LARGE);
  }

  private final int volume;

  ShakeSizeEnum(int volume) {
    this.volume = volume;
  }

  public static ShakeSizeEnum fromString(String size) {
    return sizeMap.get(size);
  }

  public int getVolume() {
    return volume;
  }
}
