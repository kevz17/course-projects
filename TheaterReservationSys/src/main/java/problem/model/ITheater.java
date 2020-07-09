package problem.model;

import java.util.List;

/**
 * An interface that represents public behaviors of a theater.
 */
public interface ITheater {

  /**
   * Returns the row numbers that are wheelchair inaccessible as List of
   * Integers
   *
   * @return the row numbers, as List of Integers, that are wheelchair
   * inaccessible
   */
  List<Integer> getNoAccessRows();
}
