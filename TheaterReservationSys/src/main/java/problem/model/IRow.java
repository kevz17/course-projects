package problem.model;

/**
 * An interface that represents public behaviors of a row of seats in a theater.
 */
public interface IRow {

  /**
   * Returns the number of available seats in a row as Integer
   *
   * @return the number of available seats in a row as Integer
   */
  Integer calculateEmptySeats();

  /**
   * Returns the index of the first available seat of a List of Seats from left
   * to right, otherwise returns -1 to indicate no such seat.
   *
   * @return the index, as Integer, of the first available seat of a List of
   * Seats from left to right, otherwise returns -1 to indicate no such seat
   */
  Integer getEmptySeatIndex();

  /**
   * Sets all the seats of a row to be wheelchair accessible
   */
  void setAccessible();
}
