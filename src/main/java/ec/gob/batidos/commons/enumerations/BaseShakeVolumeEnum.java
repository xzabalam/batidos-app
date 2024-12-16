package ec.gob.batidos.commons.enumerations;

public enum BaseShakeVolumeEnum {
  DEFAULT(100);

  private final int volume;

  BaseShakeVolumeEnum(int volume) {
    this.volume = volume;
  }

  public int getVolume() {
    return volume;
  }
}
