package ec.gob.batidos.commons.exception;

public class InventoryException extends GlobalException {

  public InventoryException(String message) {
    super(message);
  }

  public InventoryException(String message, String errorCode) {
    super(message, errorCode);
  }

  public InventoryException(String message, Throwable cause, String errorCode) {
    super(message, cause, errorCode);
  }
}
