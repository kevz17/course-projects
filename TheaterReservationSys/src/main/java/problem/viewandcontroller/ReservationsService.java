package problem.viewandcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import problem.model.Theater;

/**
 * A class that represents a reservations service for a theater, with
 * implemented functionalities defined in its interface.
 */
public class ReservationsService implements IReservationsService {

  private Theater theater;

  /**
   * Constructor that creates a new reservations service object with specified
   * theater.
   *
   * @param theater - the theater the reservations service built upon.
   */
  public ReservationsService(Theater theater) {
    this.theater = theater;
  }

  /**
   * Displays all the seats of a theater along with the number and wheelchair
   * accessibility of each row as well as the availability of each seat.
   */
  @Override
  public void displaySeats() {
    System.out.println(this.getTheater());
  }

  /**
   * Given the number of seats to reserve, the name being reserved for and
   * status that whether or not wheelchair accessible is needed, returns the
   * number of row being reserved as Integer. If reservation is impossible
   * return 0.
   *
   * @param numSeats   - the number of seats to reserve as Integer
   * @param name       - the name being reserved for
   * @param needAccess - whether or not wheelchair accessible is needed
   * @return the number of row being reserved as Integer, 0 when such row not
   * found
   */
  @Override
  public Integer reserve(
      Integer numSeats, String name, Boolean needAccess) {

    // Sorts the rows that are wheelchair accessible
    List<Integer> accessRowNums
        = this.bubbleSort(this.getTheater().getAccessibleRows());

    // Sorts the rows that are wheelchair inaccessible
    List<Integer> noAccessRowNums
        = this.bubbleSort(this.getTheater().getNoAccessRows());

    // Concatenates wheelchair inaccessible rows with wheelchair accessible rows
    // so that user's with no wheelchair accessible need will first have the row
    // that are wheelchair inaccessible reserved. Then reservation will consider
    // the rows that are wheelchair accessible.
    List<Integer> rowNums = new ArrayList<>();
    for (Integer element : noAccessRowNums) {
      rowNums.add(element);
    }
    for (Integer element : accessRowNums) {
      rowNums.add(element);
    }

    // Makes reservations as per user's need
    if (needAccess) {
      return doReserve(accessRowNums, numSeats, name);
    } else {
      return doReserve(rowNums, numSeats, name);
    }
  }

  /**
   * Checks each candidate rows from given List of Integers, which indicates the
   * row number. Make reservation when possible and return row number, otherwise
   * return 0 indicating no such row.
   *
   * @param rowNums  - candidate rows to check
   * @param numSeats - the number of seats to reserve as Integer
   * @param name     - the name being reserved for
   * @return the number of row being reserved as Integer, 0 when such row not
   * found
   */
  private Integer doReserve(
      List<Integer> rowNums, Integer numSeats, String name) {
    for (Integer i : rowNums) {
      i = i - 1;  // Offsets the row number difference from row index
      if (this.getTheater().getRows().get(i).calculateEmptySeats() < numSeats) {
        continue;
      } else {
        int startIndex = this.getTheater().getRows().get(i).getEmptySeatIndex();
        for (int j = startIndex; j < startIndex + numSeats; j++) {
          this.getTheater().getRows().get(i).get(j).setReservedFor(name);
        }
        return i + 1;
      }
    }
    return 0;
  }

  /**
   * Given a List of Integers to sort, returns a sorted List of Integers
   * according to element's difference from the center row's number.
   *
   * @param intList - the List of Integers to sort
   * @return a sorted List of Integers according to element's difference from
   * the center row's number
   */
  private List<Integer> bubbleSort(List<Integer> intList) {

    Integer[] intArray = new Integer[intList.size()];
    for (int i =0; i < intList.size(); i++) {
      intArray[i] = intList.get(i);
    }

    int baseInt = (this.theater.getRows().size() + 1) / 2;
    int size = intArray.length;
    for (int i = 0; i < size - 1; i++) {
      for (int j = 0; j < size - i - 1; j++) {
        // Sorts based on element's difference from the center row's number
        if (Math.abs(intArray[j] - baseInt)
            > Math.abs(intArray[j + 1] - baseInt)) {
          int temp = intArray[j];
          intArray[j] = intArray[j + 1];
          intArray[j + 1] = temp;
        }
      }
    }
    return Arrays.asList(intArray);
  }

  /**
   * Getter for the theater the reservation service built upon
   *
   * @return the theater the reservation service built upon
   */
  public Theater getTheater() {
    return this.theater;
  }

  /**
   * Return a formatted string that summarizes a reservations service with a
   * theater.
   *
   * @return a formatted string as specified by theater.
   */
  @Override
  public String toString() {
    return "ReservationsService{" +
        "theater=" + this.theater.getName() +
        '}';
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

    ReservationsService other = (ReservationsService) obj;
    return Objects.equals(this.theater, other.theater);
  }

  /**
   * Return the hash value of this object.
   *
   * @return the hash value of this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.theater);
  }
}
