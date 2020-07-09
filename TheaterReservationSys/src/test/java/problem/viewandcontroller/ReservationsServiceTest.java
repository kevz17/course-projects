package problem.viewandcontroller;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import problem.model.Row;
import problem.model.Seat;
import problem.model.Theater;

public class ReservationsServiceTest {

  private static final String THEATER_NAME = "Roxy";
  private static final Character START_NAME = 'A';
  private static final Character END_NAME = 'Z';
  private static final Integer NUM_ROWS_IN_THEATER = Integer.valueOf(15);
  private static final Integer NUM_SEATS_IN_A_ROW = Integer.valueOf(10);
  public static final List<Integer> ACCESSIBLE_NUM
      = new ArrayList<>(Arrays.asList(NUM_ROWS_IN_THEATER));

  private ReservationsService testService1;
  private ReservationsService testService2;
  private ReservationsService testService3;
  private ReservationsService testService4;
  private Theater testTheater1;
  private Theater testTheater2;
  private List<Row> testRows;
  private Row testRow;
  private Seat testSeat;
  private Theater expectedTheater;
  private List<Row> expectedRows;
  private Row expectedRow;
  private Seat expectedSeat;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUp() throws Exception {
    testSeat = new Seat("A");
    testRow = new Row(1, true);
    testRow.add(testSeat);
    testRows = new ArrayList<>(Arrays.asList(testRow));

    testTheater1 = new Theater("Roxy", testRows);
    testTheater2 = new Theater("Regency", testRows);

    testService1 = new ReservationsService(testTheater1);
    testService2 = new ReservationsService(testTheater2);
    testService3 = new ReservationsService(testTheater1);

    expectedSeat = new Seat("A");
    expectedRow = new Row(1, true);
    expectedRow.add(expectedSeat);
    expectedRows = new ArrayList<>(Arrays.asList(expectedRow));
    expectedTheater = new Theater("Roxy", expectedRows);

    // Creates a theater that has 15 rows of seats of which the last row is
    // wheelchair accessible. Each row consists of 10 seats.
    List<Row> rows = new ArrayList<>();
    for (int i = 1; i <= NUM_ROWS_IN_THEATER; i++) {
      Row row;
      if (ACCESSIBLE_NUM.contains(Integer.valueOf(i))) {
        row = new Row(i, true);
      } else {
        row = new Row(i, false);
      }
      IntStream.rangeClosed(START_NAME, END_NAME)
          .filter(c -> c <= START_NAME + NUM_SEATS_IN_A_ROW)
          .forEach(c -> row.add(new Seat(Character.toString((char) c))));
      rows.add(row);
    }
    Theater theater = new Theater(THEATER_NAME, rows);
    testService4 = new ReservationsService(theater);

    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void displaySeats() {
    String expectedDisplay = "1 =\n\n";
    testService1.displaySeats();

    assertEquals(expectedDisplay, outContent.toString());
  }

  @Test
  public void reserve() {
    Integer expectedRowNumber1 = 1;
    Integer expectedRowNumber2 = 8;

    assertEquals(expectedRowNumber1, testService1.reserve(1, "Tony", true));
    assertEquals(expectedRowNumber2, testService4.reserve(3, "Jean Claude", false));
  }

  @Test
  public void getTheater() {
    assertEquals(expectedTheater, testService1.getTheater());
  }

  @Test
  public void testToString() {
    String expected = "ReservationsService{theater=Roxy}";

    assertEquals(expected, testService1.toString());
  }

  @Test
  public void testEquals() {
    Integer illegal = 9;

    assertTrue(testService1.equals(testService1));
    assertFalse(testService1.equals(null));
    assertFalse(testService1.equals(illegal));
    assertFalse(testService1.equals(testService2));
    assertTrue(testService1.equals(testService3));
  }

  @Test
  public void testHashCode() {
    Integer illegal = 0;

    assertEquals(testService1.hashCode(), testService1.hashCode());
    assertNotEquals(testService1.hashCode(), illegal.hashCode());
    assertNotEquals(testService1.hashCode(), testService2.hashCode());
    assertEquals(testService1.hashCode(), testService3.hashCode());
  }
}