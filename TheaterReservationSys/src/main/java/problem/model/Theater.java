package problem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * A class that represents a theater made up of rows of seats
 */
public class Theater implements ITheater {

  private static final String EMPTY_STRING = "";
  private static final String NEW_LINE_CHAR = "\n";
  private String name;
  private List<Row> rows;
  private List<Integer> accessibleRows;

  /**
   * Constructor that creates a new theater object with specified name as String
   * and collection of rows of seats as List of Rows.
   *
   * @param name - the name of the theater
   * @param rows - the collection of rows of seats in the theater
   */
  public Theater(String name, List<Row> rows) {
    this.name = name;
    this.rows = rows;
    this.accessibleRows = new ArrayList<>();
    // Populates the wheelchair accessible rows
    for (Row element : this.rows) {
      if (element.isAccessible()) {
        accessibleRows.add(element.getRowNumber());
      }
    }
  }

  /**
   * Returns the row numbers that are wheelchair inaccessible as List of
   * Integers
   *
   * @return the row numbers, as List of Integers, that are wheelchair
   * inaccessible
   */
  @Override
  public List<Integer> getNoAccessRows() {
    List<Integer> noAccessRows = new ArrayList<>();
    IntStream.rangeClosed(1, this.rows.size()).forEach(noAccessRows::add);
    for (Integer element : this.accessibleRows) {
      if (noAccessRows.contains(element)) {
        noAccessRows.remove(element);
      }
    }
    return noAccessRows;
  }

  /**
   * Getter for the name of the theater as String
   *
   * @return the name of the theater as String
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter for the collection of rows of seats in the theater as List of Rows
   *
   * @return the collection of rows of seats in the theater as List of Rows
   */
  public List<Row> getRows() {
    return this.rows;
  }

  /**
   * Getter for the collection of wheelchair accessible rows of the theater as
   * List of Integers
   *
   * @return the collection of wheelchair accessible rows of the theater as List
   * of Integers
   */
  public List<Integer> getAccessibleRows() {
    return this.accessibleRows;
  }

  /**
   * Return a formatted string that represents rows of seats of a theater.
   *
   * @return a formatted string that represents rows of seats of a theater.
   */
  @Override
  public String toString() {
    String str = EMPTY_STRING;
    for (int i = 0; i < this.getRows().size(); i++) {
      if (this.getRows().get(i).isAccessible()) {
        this.getRows().get(i).setAccessible();
      }
      str += (this.getRows().get(i).toString() + NEW_LINE_CHAR);
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

    Theater other = (Theater) obj;
    return Objects.equals(this.name, other.name)
        && Objects.equals(this.rows, other.rows);
  }

  /**
   * Return the hash value of this object.
   *
   * @return the hash value of this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.rows);
  }
}
