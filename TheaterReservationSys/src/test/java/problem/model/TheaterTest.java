package problem.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TheaterTest {

  private Theater testTheater1;
  private Theater testTheater2;
  private Theater testTheater3;
  private Theater testTheater4;
  private List<Row> testRows1;
  private List<Row> testRows2;
  private List<Row> testRows3;
  private Row testRow1;
  private Row testRow2;
  private Seat testSeat1;
  private Seat testSeat2;
  private Seat testSeat3;
  private Seat testSeat4;
  private Seat testSeat5;
  private Seat testSeat6;
  private Row expectedRow1;
  private Row expectedRow2;
  private Seat expectedSeat1;
  private Seat expectedSeat2;
  private Seat expectedSeat3;
  private Seat expectedSeat4;
  private Seat expectedSeat5;
  private Seat expectedSeat6;
  private List<Row> expectedRows;

  @Before
  public void setUp() throws Exception {
    testRow1 = new Row(1, false);
    testRow2 = new Row(2, true);

    testSeat1 = new Seat("A");
    testSeat1.setReservedFor("Tony");
    testSeat2 = new Seat("B");
    testSeat3 = new Seat("C");

    testSeat4 = new Seat("A");
    testSeat5 = new Seat("B");
    testSeat6 = new Seat("C");

    testRow1.add(testSeat1);
    testRow1.add(testSeat2);
    testRow1.add(testSeat3);
    testRow2.add(testSeat4);
    testRow2.add(testSeat5);
    testRow2.add(testSeat6);

    testRows1 = new ArrayList<>(Arrays.asList(testRow1, testRow2));
    testRows2 = new ArrayList<>(Arrays.asList(testRow1));
    testRows3 = new ArrayList<>(Arrays.asList(testRow1, testRow2));
    testTheater1 = new Theater("Roxy", testRows1);
    testTheater2 = new Theater("Regency", testRows1);
    testTheater3 = new Theater("Roxy", testRows2);
    testTheater4 = new Theater("Roxy", testRows3);

    expectedRow1 = new Row(1, false);
    expectedRow2 = new Row(2, true);
    expectedSeat1 = new Seat("A");
    expectedSeat1.setReservedFor("Tony");
    expectedSeat2 = new Seat("B");
    expectedSeat3 = new Seat("C");
    expectedSeat4 = new Seat("A");
    expectedSeat5 = new Seat("B");
    expectedSeat6 = new Seat("C");
    expectedRow1.add(expectedSeat1);
    expectedRow1.add(expectedSeat2);
    expectedRow1.add(expectedSeat3);
    expectedRow2.add(expectedSeat4);
    expectedRow2.add(expectedSeat5);
    expectedRow2.add(expectedSeat6);
    expectedRows = new ArrayList<>(Arrays.asList(expectedRow1, expectedRow2));
  }

  @Test
  public void getNoAccessRows() {
    List<Integer> expectedNoAccessRows = new ArrayList<>(Arrays.asList(1));

    assertEquals(expectedNoAccessRows, testTheater1.getNoAccessRows());
  }

  @Test
  public void getName() {
    String expectedName = "Roxy";

    assertEquals(expectedName, testTheater1.getName());
  }

  @Test
  public void getRows() {
    assertEquals(expectedRows, testTheater1.getRows());
  }

  @Test
  public void getAccessibleRows() {
    List<Integer> expectedAccessibleRows = new ArrayList<>(Arrays.asList(2));

    assertEquals(expectedAccessibleRows, testTheater1.getAccessibleRows());
  }

  @Test
  public void testToString() {
    String expected = "1 X _ _\n2 = = =\n";

    assertEquals(expected, testTheater1.toString());
  }

  @Test
  public void testEquals() {
    Integer illegal = 9;

    assertTrue(testTheater1.equals(testTheater1));
    assertFalse(testTheater1.equals(null));
    assertFalse(testTheater1.equals(illegal));
    assertFalse(testTheater1.equals(testTheater2));
    assertFalse(testTheater1.equals(testTheater3));
    assertTrue(testTheater1.equals(testTheater4));
  }

  @Test
  public void testHashCode() {
    Integer illegal = 0;

    assertEquals(testTheater1.hashCode(), testTheater1.hashCode());
    assertNotEquals(testTheater1.hashCode(), illegal.hashCode());
    assertNotEquals(testTheater1.hashCode(), testTheater2.hashCode());
    assertNotEquals(testTheater1.hashCode(), testTheater3.hashCode());
    assertEquals(testTheater1.hashCode(), testTheater4.hashCode());
  }
}