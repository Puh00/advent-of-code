import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day11 {

  // All 8 directions
  // {Left Up, Left, Down Left, Up, Down, Right, Down Right}
  private static final int[] xDir = {-1, -1, -1, 0, 0, 1, 1, 1};
  private static final int[] yDir = {-1, 0, 1, -1, 1, -1, 0, 1};

  /** Traverse adjacent nodes to find number of occupied spots */
  public int adjacentNeighbours(List<String> input, int x, int y) {
    int rows = input.size();
    int columns = input.get(0).length();
    int count = 0;
    for (int r = -1; r < 2; r++) {
      for (int c = -1; c < 2; c++) {
        if (r == 0 && c == 0) continue;
        int yCord = r + x;
        int xCord = c + y;
        if (yCord >= 0 && yCord < rows && xCord >= 0 && xCord < columns)
          if (input.get(yCord).charAt(xCord) == '#') count++;
      }
    }
    return count;
  }

  /** Searches for the first seat in each direction and counts the occurrences of occupied seats */
  public int distantNeighbours(List<String> input, int row, int col) {
    int count = 0;
    // Iterate through each of the 8 directions
    for (int dir = 0; dir < 8; dir++) {
      // Starting point for the current direction
      int rd = row + yDir[dir];
      int cd = col + xDir[dir];

      // Loop while within bounds
      while (rd < input.size() && rd >= 0 && cd < input.get(0).length() && cd >= 0) {
        char seat = input.get(rd).charAt(cd);
        if (seat == '#') {
          count++;
          break;
        } else if (seat == 'L') break;

        // Keep on moving in the current direction
        rd += yDir[dir];
        cd += xDir[dir];
      }
    }
    return count;
  }

  /** Prints the seats in a readable way */
  public void printSeats(List<String> input) {
    for (String s : input) System.out.println(s);
    System.out.println();
  }

  /** Returns number of occupied seats */
  public int occupiedSeats(List<String> input) {
    int count = 0;
    for (int row = 0; row < input.size(); row++) {
      for (int col = 0; col < input.get(row).length(); col++) {
        if (input.get(row).charAt(col) == '#') count++;
      }
    }
    return count;
  }

  public static void main(String[] args) throws IOException {
    Day11 dy = new Day11();
    List<String> input = Files.readAllLines(Path.of("input.txt"));
    boolean runPartB = true;

    boolean hasChanged = false;
    while (true) {
      hasChanged = false;
      List<String> round = new ArrayList<>();

      for (int row = 0; row < input.size(); row++) {
        StringBuilder sb = new StringBuilder();
        for (int col = 0; col < input.get(row).length(); col++) {
          char seat = input.get(row).charAt(col);
          if (!runPartB) {
            // Part 1
            int occupiedSeats = dy.adjacentNeighbours(input, row, col);
            if (seat == 'L' && occupiedSeats <= 0) {
              sb.append('#');
              hasChanged = true;
            } else if (seat == '#' && occupiedSeats >= 4) {
              sb.append('L');
              hasChanged = true;
            } else sb.append(input.get(row).charAt(col));
          } else {
            // Part 2
            int occupiedSeats = dy.distantNeighbours(input, row, col);
            if (seat == 'L' && occupiedSeats <= 0) {
              sb.append('#');
              hasChanged = true;
            } else if (seat == '#' && occupiedSeats >= 5) {
              sb.append('L');
              hasChanged = true;
            } else sb.append(input.get(row).charAt(col));
          }
        }
        round.add(sb.toString());
      }
      // Seat movement has stagnated
      if (!hasChanged) break;
      input = round;
    }

    dy.printSeats(input);
    System.out.println("Occupied seats: " + dy.occupiedSeats(input));
  }
}
