package problem.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that represents a row of seats in a theater
 */
public class Row extends ArrayList<Seat> implements IRow {

  private static final String SEAT_DELIMITER = " ";
  private Integer rowNumber;
  private Boolean accessible;

  /**
   * Constructor that creates a new row object with specified row number and
   * wheelchair accessibility.
   *
   * @param rowNumber  - the number of a row as Integer type
   * @param accessible - indicates whether or not the row is wheelchair
   *                   accessible as Boolean type
   */
  public Row(Integer rowNumber, Boolean accessible) {
    this.rowNumber = rowNumber;
    this.accessible = accessible;
  }

  /**
   * Returns the number of available seats in a row as Integer
   *
   * @return the number of available seats in a row as Integer
   */
  @Override
  public Integer calculateEmptySeats() {
    Integer count = Integer.valueOf(0);
    for (Seat element : this) {
      if (element.getReservedFor() == null) {
        count++;
      }
    }
    return count;
  }

  /**
   * Returns the index of the first available seat of a List of Seats from left
   * to right, otherwise returns -1 to indicate no such seat.
   *
   * @return the index, as Integer, of the first available seat of a List of
   * Seats from left to right, otherwise returns -1 to indicate no such seat
   */
  @Override
  public Integer getEmptySeatIndex() {
    for (int i = 0; i < this.size(); i++) {
      if (this.get(i).getReservedFor() == null) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Sets all the seats of a row to be wheelchair accessible
   */
  @Override
  public void setAccessible() {
    for (int i = 0; i < this.size(); i++) {
      this.get(i).setAccessible();
    }
  }

  /**
   * Getter for the number of a row as Integer
   *
   * @return the number of a row as Integer
   */
  public Integer getRowNumber() {
    return this.rowNumber;
  }

  /**
   * Getter for the status of wheelchair accessibility of a row as Boolean
   *
   * @return the status of wheelchair accessibility of a row as Boolean
   */
  public Boolean isAccessible() {
    return this.accessible;
  }

  /**
   * Return a formatted string that represents a row.
   *
   * @return a formatted string that represents a row.
   */
  @Override
  public String toString() {
    String str = this.rowNumber.toString();
    for (Seat element : this) {
      str += (SEAT_DELIMITER + element.toString());
    }
    return str;
  }

  /**
   * Return whether or not the given object is the same as this object.
   *
   * @param obj - the object to be compared to for equality.
   * @return whether or not the given object is the same as this object.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Row other = (Row) obj;
    return Objects.equals(this.rowNumber, other.rowNumber)
        && Objects.equals(this.accessible, other.accessible);
  }

  /**
   * Return the hash value of this object.
   *
   * @return the hash value of this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.rowNumber, this.accessible);
  }
}
