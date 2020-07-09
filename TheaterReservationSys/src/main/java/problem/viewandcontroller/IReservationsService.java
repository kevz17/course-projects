package problem.viewandcontroller;

/**
 * An interface represents public behaviors of a reservations service of a
 * theater consisting of rows of seats.
 */
public interface IReservationsService {

  /**
   * Displays all the seats of a theater along with the number and wheelchair
   * accessibility of each row as well as the availability of each seat.
   */
  void displaySeats();

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
  Integer reserve(Integer numSeats, String name, Boolean needAccess);
}
