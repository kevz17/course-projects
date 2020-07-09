package problem.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RowTest {

  private Row testRow1;
  private Row testRow2;
  private Row testRow3;
  private Row testRow4;
  private Row testRow5;
  private Row testRow6;
  private Seat testSeat1;
  private Seat testSeat2;
  private Seat testSeat3;
  private Seat testSeat4;
  private Seat testSeat5;
  private Seat testSeat6;

  @Before
  public void setUp() throws Exception {
    testRow1 = new Row(1, false);
    testRow2 = new Row(2, false);
    testRow3 = new Row(1, true);
    testRow4 = new Row(1, false);
    testRow5 = new Row(1, false);
    testRow6 = new Row(1, false);

    testSeat1 = new Seat("A");
    testSeat1.setReservedFor("Tony");
    testSeat2 = new Seat("B");
    testSeat3 = new Seat("C");
    testSeat4 = new Seat("A");
    testSeat4.setReservedFor("Tony");
    testSeat5 = new Seat("B");
    testSeat5.setReservedFor("Jo");
    testSeat6 = new Seat("C");
    testSeat6.setReservedFor("Sara");

    testRow5.add(testSeat1);
    testRow5.add(testSeat2);
    testRow5.add(testSeat3);
    testRow6.add(testSeat4);
    testRow6.add(testSeat5);
    testRow6.add(testSeat6);
  }

  @Test
  public void calculateEmptySeats() {
    Integer expectedEmptySeats = 2;

    assertEquals(expectedEmptySeats, testRow5.calculateEmptySeats());
  }

  @Test
  public void getEmptySeatIndex() {
    Integer expectedIndex1 = 1;
    Integer expectedIndex2 = -1;

    assertEquals(expectedIndex1, testRow5.getEmptySeatIndex());
    assertEquals(expectedIndex2, testRow6.getEmptySeatIndex());
  }

  @Test
  public void setAccessible() {
    testRow5.setAccessible();

    assertTrue(testRow5.get(0).getAccessible());
    assertTrue(testRow5.get(1).getAccessible());
    assertTrue(testRow5.get(2).getAccessible());
  }

  @Test
  public void getRowNumber() {
    Integer expectedRowNumber = 1;

    assertEquals(expectedRowNumber, testRow1.getRowNumber());
  }

  @Test
  public void isAccessible() {
    assertTrue(testRow3.isAccessible());
  }

  @Test
  public void testToString() {
    String expected = "1 X _ _";

    assertEquals(expected, testRow5.toString());
  }

  @Test
  public void testEquals() {
    Integer illegal = 9;

    assertTrue(testRow1.equals(testRow1));
    assertFalse(testRow1.equals(null));
    assertFalse(testRow1.equals(illegal));
    assertFalse(testRow1.equals(testRow2));
    assertFalse(testRow1.equals(testRow3));
    assertTrue(testRow1.equals(testRow4));
  }

  @Test
  public void testHashCode() {
    Integer illegal = 0;

    assertEquals(testRow1.hashCode(), testRow1.hashCode());
    assertNotEquals(testRow1.hashCode(), illegal.hashCode());
    assertNotEquals(testRow1.hashCode(), testRow2.hashCode());
    assertNotEquals(testRow1.hashCode(), testRow3.hashCode());
    assertEquals(testRow1.hashCode(), testRow4.hashCode());
  }
}