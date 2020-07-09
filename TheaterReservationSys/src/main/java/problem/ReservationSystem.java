package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import problem.model.Row;
import problem.model.Seat;
import problem.model.Theater;
import problem.viewandcontroller.ReservationsService;

/**
 * This class represents the entry point of a theater's reservation system. It
 * defines theater's specifications through constant variables, and creates a
 * new instance of Theater, then calls the service implemented in
 * ReservationService with theater object as its argument. The system(main
 * method) will perform corresponding task as per user's input commands,
 * illustrated in USAGE_HELP constant defined below.
 */
public class ReservationSystem {

  private static final String INPUT_PATTERN = "(reserve \\d+)|show|done";
  private static final String SPLIT_PATTERN = "\\s";
  private static final String YES_NO_PATTERN = "yes|no";
  private static final String NEW_LINE = "\n";
  private static final String ASK = "What would you like to do?";
  private static final String ASK_AGAIN = "Invalid command, please re-enter:";
  private static final String RESERVE_COMMAND = "reserve";
  private static final String SHOW_COMMAND = "show";
  private static final String SHUT_DOWN_COMMAND = "done";
  private static final String ASK_NAME = "What's your name?";
  private static final String ASK_WHEELCHAIR
      = "Do you need wheelchair accessible seats?";
  private static final String ASK_WHEELCHAIR_AGAIN = "Please enter yes or no";
  private static final String YES = "yes";
  private static final String TRUE = "true";
  private static final String FALSE = "false";
  private static final String EXIT_MESSAGE = "Have a nice day!";
  private static final String RESERVATION_INFO_1 = "I've reserved ";
  private static final String RESERVATION_INFO_2 = " seats for you at the ";
  private static final String RESERVATION_INFO_3 = " in row ";
  private static final String RESERVATION_INFO_4 = ", ";
  private static final String RESERVATION_INFO_5 = ".";
  private static final Character START_NAME = 'A';
  private static final Character END_NAME = 'Z';
  private static final Integer NO_ROW_AVAILABLE = 0;
  private static final Integer MAX_NUM_SEATS_IN_A_ROW = 26;
  private static final String NO_ROW_MESSAGE =
      "Sorry, we don't have that many seats together for you.";
  private static final String USAGE_HELP =
      "reserve <number>    --reserves that number of seats\n"
          + "show                --to display the current available seats\n"
          + "done                --to shut down the system";

  // Note: the 4 variables defined below should be modified as per the needs of
  // the owner of this reservation system. The number of seats in a row must
  // be no greater than 26(the number of Alphabets). The wheelchair accessible
  // rows' number(ACCESSIBLE_ROWS_NUMS) can be modified by putting Integers(as
  // row's number) within the parenthesis of asList(), separated by comma ",".
  private static final String THEATER_NAME = "Roxy";
  private static final Integer NUM_ROWS_IN_THEATER = 15;
  private static Integer NUM_SEATS_IN_A_ROW = 10;
  // Wheelchair accessible rows are pre-defined here, this case assumes the last
  // row and the first row are wheelchair accessible. Note that if no row is
  // wheelchair accessible, the system will automatically set the last row as
  // wheelchair accessible.
  public static final List<Integer> ACCESSIBLE_ROWS_NUMS
      = new ArrayList<>(Arrays.asList(1, 15));

  /**
   * Creates a theater object and calls corresponding methods according to
   * user's answers to prompting questions in several while loops. Note that
   * some variables(illustrated above) should be defined by the owner of the
   * theater(not the user or the customer of theater who makes reservations)
   * before running the program.
   *
   * @param args - the command line arguments
   */
  public static void main(String[] args) {

    List<Row> rows = new ArrayList<>();
    // Populates a series of rows with row consisting of wheelchair accessible
    // row and the ones not accessible
    for (int i = 1; i <= NUM_ROWS_IN_THEATER; i++) {
      Row row;
      if (ACCESSIBLE_ROWS_NUMS.contains(Integer.valueOf(i))) {
        row = new Row(i, true);
      } else {
        row = new Row(i, false);
      }

      // Automatically assign the last row as wheelchair accessible, if there is
      // no row with wheelchair accessible defined
      if (ACCESSIBLE_ROWS_NUMS.size() == 0 && i == NUM_ROWS_IN_THEATER) {
        row = new Row(i, true);
      }

      // Populates a row with defined number of seats, using functional
      // programming
      Row finalRow = row;
      // Sets the number of seats in a row to be 26, if it is greater than 26
      if (NUM_SEATS_IN_A_ROW > MAX_NUM_SEATS_IN_A_ROW) {
        NUM_SEATS_IN_A_ROW = MAX_NUM_SEATS_IN_A_ROW;
      }
      IntStream.rangeClosed(START_NAME, END_NAME)
          .filter(c -> c < START_NAME + NUM_SEATS_IN_A_ROW)
          .forEach(c -> finalRow.add(new Seat(Character.toString((char) c))));

      rows.add(row);
    }

    // Creates a theater with defined specifications declared private static
    // final data listed above, theater's specs are able to modify through
    // changing these values of data
    Theater theater = new Theater(THEATER_NAME, rows);
    ReservationsService service = new ReservationsService(theater);

    // Starts the interactions with user
    System.out.println(ASK);
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();

    Matcher matcher = Pattern.compile(INPUT_PATTERN).matcher(input);

    // User proceeds to further service
    while (!input.equals(SHUT_DOWN_COMMAND)) {
      // Validates user's input
      while (!matcher.matches()) {
        System.out.println(ASK_AGAIN);
        System.out.println(USAGE_HELP);
        input = scanner.nextLine();
        matcher = Pattern.compile(INPUT_PATTERN).matcher(input);
      }

      String[] command = input.split(SPLIT_PATTERN);

      // Displays available seats
      if (command[0].equals(SHOW_COMMAND)) {
        service.displaySeats();
      }

      // Makes reservation
      else if (command[0].equals(RESERVE_COMMAND)) {
        System.out.println(ASK_NAME);
        String name = scanner.nextLine();

        // Asks wheelchair accessible
        System.out.println(ASK_WHEELCHAIR);
        String needWheelchair = scanner.nextLine();

        Matcher matcher2 = Pattern.compile(YES_NO_PATTERN)
            .matcher(needWheelchair);
        while (!matcher.matches()) {
          System.out.println(ASK_WHEELCHAIR_AGAIN);
          needWheelchair = scanner.nextLine();
          matcher2 = Pattern.compile(YES_NO_PATTERN).matcher(needWheelchair);
        }

        Boolean needAccess = Boolean.parseBoolean(FALSE);
        if (needWheelchair.equals(YES)) {
          needAccess = Boolean.parseBoolean(TRUE);
        }

        Integer rowNum = service.reserve(Integer.parseInt(
            command[1]), name, needAccess);

        // Prints different messages based on reservation result
        if (rowNum.equals(NO_ROW_AVAILABLE)) {
          System.out.println(NO_ROW_MESSAGE + NEW_LINE);
        } else {
          System.out.println(RESERVATION_INFO_1 + command[1]
              + RESERVATION_INFO_2 + theater.getName()
              + RESERVATION_INFO_3 + rowNum
              + RESERVATION_INFO_4 + name + RESERVATION_INFO_5 + NEW_LINE);
        }
      }

      if (Pattern.compile(SHUT_DOWN_COMMAND).matcher(input).matches()) {
        break;
      }

      // Proceeds to next request from user
      System.out.println(ASK);
      input = scanner.nextLine();
      matcher = Pattern.compile(INPUT_PATTERN).matcher(input);
    }

    // Prints the ending message when done
    System.out.println(EXIT_MESSAGE);
  }
}
