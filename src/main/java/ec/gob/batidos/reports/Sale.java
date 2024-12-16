package ec.gob.batidos.reports;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Sale(String type, String size, double price, LocalDateTime date) {
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");

  @Override
  public String toString() {
    return String.format("%s (%s): $%.2f on %s", type, size, price, date.format(FORMATTER));
  }
}
