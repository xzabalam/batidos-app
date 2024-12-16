package ec.gob.batidos.commons.exception;

import java.io.Serial;

public class GlobalException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private final String errorCode;

  public GlobalException(String message) {
    super(message);
    this.errorCode = "UNKNOWN";
  }

  public GlobalException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public GlobalException(String message, Throwable cause, String errorCode) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
