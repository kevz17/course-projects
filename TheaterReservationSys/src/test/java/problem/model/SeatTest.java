package problem.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SeatTest {

  private Seat testSeat1;
  private Seat testSeat2;
  private Seat testSeat3;
  private Seat testSeat4;
  private Seat testSeat5;
  private Seat testSeat6;
  private Seat testSeat7;

  @Before
  public void setUp() throws Exception {
    testSeat1 = new Seat("A");
    testSeat2 = new Seat("B");
    testSeat3 = new Seat("A");
    testSeat3.setReservedFor("Tony");
    testSeat4 = new Seat("A");
    testSeat4.setAccessible();
    testSeat4.setReservedFor("Tony");
    testSeat5 = new Seat("A");
    testSeat6 = new Seat("A");
    testSeat6.setReservedFor("Jo");
    testSeat7 = new Seat("B");
    testSeat7.setAccessible();
    testSeat7.setReservedFor("Tony");
  }

  @Test
  public void setAccessible() {
    assertFalse(testSeat1.getAccessible());
    testSeat1.setAccessible();
    assertTrue(testSeat1.getAccessible());
  }

  @Test
  public void getSeatName() {
    String expectedSeatName = "A";

    assertEquals(expectedSeatName, testSeat1.getSeatName());
  }

  @Test
  public void getReservedFor() {
    assertEquals(null, testSeat1.getReservedFor());
  }

  @Test
  public void setReservedFor() {
    String expectedReservedFor = "Tony";

    testSeat1.setReservedFor("Tony");
    assertEquals(expectedReservedFor, testSeat1.getReservedFor());
  }

  @Test
  public void testToString() {
    String expectedEmpty = "_";
    String expectedOccupied = "X";
    String expectedEmptyWheelchair = "=";

    assertEquals(expectedEmpty, testSeat1.toString());

    testSeat1.setAccessible();
    assertEquals(expectedEmptyWheelchair, testSeat1.toString());

    testSeat1.setReservedFor("Tony");
    assertEquals(expectedOccupied, testSeat1.toString());
  }

  @Test
  public void testEquals() {
    Integer illegal = 9;

    assertTrue(testSeat1.equals(testSeat1));
    assertFalse(testSeat1.equals(null));
    assertFalse(testSeat1.equals(illegal));
    assertFalse(testSeat1.equals(testSeat2));
    assertFalse(testSeat1.equals(testSeat3));
    assertFalse(testSeat1.equals(testSeat4));
    assertFalse(testSeat3.equals(testSeat6));
    assertFalse(testSeat4.equals(testSeat7));
    assertTrue(testSeat1.equals(testSeat5));
  }

  @Test
  public void testHashCode() {
    Integer illegal = 0;

    assertEquals(testSeat1.hashCode(), testSeat1.hashCode());
    assertNotEquals(testSeat1.hashCode(), illegal.hashCode());
    assertNotEquals(testSeat1.hashCode(), testSeat2.hashCode());
    assertNotEquals(testSeat1.hashCode(), testSeat3.hashCode());
    assertNotEquals(testSeat1.hashCode(), testSeat4.hashCode());
    assertEquals(testSeat1.hashCode(), testSeat5.hashCode());
  }
}