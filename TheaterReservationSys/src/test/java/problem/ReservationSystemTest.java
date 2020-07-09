package problem;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReservationSystemTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private InputStream sysInBackup;
  private ByteArrayInputStream in;

  @Before
  public void setUp() throws Exception {
    System.setOut(new PrintStream(outContent));
    sysInBackup = System.in; // backup System.in to restore it later
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setIn(sysInBackup);
  }

  @Test
  public void main1() {
    String expected1 = "What would you like to do?\n";
    String expected2 = "Have a nice day!\n";
    String testString = "done";
    String[] args = {""};

    in = new ByteArrayInputStream(testString.getBytes());
    System.setIn(in);

    ReservationSystem.main(args);
    assertEquals(expected1 + expected2, outContent.toString());
  }

  @Test
  public void main2() {
    String expected1 = "What would you like to do?\n";
    String expected2 = "1 = = = = = = = = = =\n"
        + "2 _ _ _ _ _ _ _ _ _ _\n"
        + "3 _ _ _ _ _ _ _ _ _ _\n"
        + "4 _ _ _ _ _ _ _ _ _ _\n"
        + "5 _ _ _ _ _ _ _ _ _ _\n"
        + "6 _ _ _ _ _ _ _ _ _ _\n"
        + "7 _ _ _ _ _ _ _ _ _ _\n"
        + "8 _ _ _ _ _ _ _ _ _ _\n"
        + "9 _ _ _ _ _ _ _ _ _ _\n"
        + "10 _ _ _ _ _ _ _ _ _ _\n"
        + "11 _ _ _ _ _ _ _ _ _ _\n"
        + "12 _ _ _ _ _ _ _ _ _ _\n"
        + "13 _ _ _ _ _ _ _ _ _ _\n"
        + "14 _ _ _ _ _ _ _ _ _ _\n"
        + "15 = = = = = = = = = =\n\n";
    String expected3 = "What would you like to do?\n";
    String expected4 = "Have a nice day!\n";

    String testString = "show" + System.lineSeparator()
        + "done" + System.lineSeparator();
    String[] args = {""};

    in = new ByteArrayInputStream(testString.getBytes());
    System.setIn(in);

    ReservationSystem.main(args);
    assertEquals(expected1 + expected2 + expected3 + expected4,
        outContent.toString());
  }

  @Test
  public void main3() {
    String expected1 = "What would you like to do?\n";
    String expected2 = "Invalid command, please re-enter:\n";
    String expected3 = "reserve <number>    --reserves that number of seats\n"
        + "show                --to display the current available seats\n"
        + "done                --to shut down the system\n";
    String expected4 = "Have a nice day!\n";

    String testString = "abc" + System.lineSeparator()
        + "done" + System.lineSeparator();

    String[] args = {""};

    in = new ByteArrayInputStream(testString.getBytes());
    System.setIn(in);

    ReservationSystem.main(args);
    assertEquals(expected1 + expected2 + expected3 + expected4,
        outContent.toString());
  }

  @Test
  public void main4() {
    String expected1 = "What would you like to do?\n";
    String expected2 = "What's your name?\n";
    String expected3 = "Do you need wheelchair accessible seats?\n";
    String expected4 =
        "I've reserved 7 seats for you at the Roxy in row 8, Tony.\n\n";

    String expected5 = "What would you like to do?\n";
    String expected6 = "What's your name?\n";
    String expected7 = "Do you need wheelchair accessible seats?\n";
    String expected8 =
        "I've reserved 10 seats for you at the Roxy in row 1, Sara.\n\n";

    String expected9 = "What would you like to do?\n";
    String expected10 = "What's your name?\n";
    String expected11 = "Do you need wheelchair accessible seats?\n";
    String expected12 =
        "Sorry, we don't have that many seats together for you.\n\n";

    String expected13 = "What would you like to do?\n";
    String expected14 = "Have a nice day!\n";

    String testString = "reserve 7" + System.lineSeparator()
        + "Tony" + System.lineSeparator()
        + "no" + System.lineSeparator()
        + "reserve 10" + System.lineSeparator()
        + "Sara" + System.lineSeparator()
        + "yes" + System.lineSeparator()
        + "reserve 11" + System.lineSeparator()
        + "Jo" + System.lineSeparator()
        + "no" + System.lineSeparator()
        + "done" + System.lineSeparator();
    String[] args = {""};

    in = new ByteArrayInputStream(testString.getBytes());
    System.setIn(in);

    ReservationSystem.main(args);
    assertEquals(expected1 + expected2 + expected3 + expected4
        + expected5 + expected6 + expected7 + expected8 + expected9 + expected10
        + expected11 + expected12 + expected13 + expected14,
        outContent.toString());
  }
}