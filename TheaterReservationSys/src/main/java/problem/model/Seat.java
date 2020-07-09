package problem.model;

import java.util.Objects;

/**
 * A class that represents a seat of a theater
 */
public class Seat implements ISeat {

  private static final String EMPTY_SEAT = "_";
  private static final String OCCUPIED_SEAT = "X";
  private static final String EMPTY_WHEELCHAIR_SEAT = "=";
  private String seatName;
  private String reservedFor;
  private Boolean accessible;

  /**
   * Constructor that creates a new seat object with specified name and a
   * property that indicates the name being reserved for.
   *
   * @param seatName - the name of the seat ranging from A to Z as String type
   */
  public Seat(String seatName) {
    this.seatName = seatName;
    this.reservedFor = null;
    this.accessible = false;  // Indicates the  wheelchair accessibility
  }

  /**
   * Sets the accessibility of wheelchair of a seat to be true
   */
  @Override
  public void setAccessible() {
    this.accessible = true;
  }

  /**
   * Getter for the accessible status of wheelchair as Boolean
   *
   * @return the accessible status of wheelchair as Boolean
   */
  public Boolean getAccessible() {
    return this.accessible;
  }

  /**
   * Getter for the name of the seat as String
   *
   * @return the name of the seat as String
   */
  public String getSeatName() {
    return this.seatName;
  }

  /**
   * Getter for the name being reserved for as String
   *
   * @return the name being reserved for as String
   */
  public String getReservedFor() {
    return this.reservedFor;
  }

  /**
   * Setter for the name a seat being reserved for
   *
   * @param reservedFor - the name a seat being reserved for as String
   */
  public void setReservedFor(String reservedFor) {
    this.reservedFor = reservedFor;
  }

  /**
   * Return a formatted string that that represents a seat.
   *
   * @return a formatted string that represents a seat.
   */
  @Override
  public String toString() {
    if (this.reservedFor == null && !this.accessible) {
      return EMPTY_SEAT;
    } else if (this.reservedFor == null && this.accessible) {
      return EMPTY_WHEELCHAIR_SEAT;
    }
    return OCCUPIED_SEAT;
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

    Seat other = (Seat) obj;
    if (this.reservedFor != null && other.reservedFor != null) {
      return Objects.equals(this.seatName, other.seatName)
          && Objects.equals(this.reservedFor, other.reservedFor)
          && Objects.equals(this.accessible, other.accessible);
    } else if (this.reservedFor == null && other.reservedFor == null){
      return Objects.equals(this.seatName, other.seatName)
          && Objects.equals(this.accessible, other.accessible);
    } else {
      return false;
    }
  }

  /**
   * Return the hash value of this object.
   *
   * @return the hash value of this object.
   */
  @Override
  public int hashCode() {
    if (this.reservedFor != null) {
      return Objects.hash(this.seatName, this.reservedFor, this.accessible);
    } else {
      return Objects.hash(this.seatName, this.accessible);
    }
  }
}
