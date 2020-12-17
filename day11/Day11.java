import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * # (L) - Empty seat and no occupied seats adjacent to it -> occupied # (#) - Occupied and 4 or
 * more seats adjacent are occupied -> empty - | Iterate until no changes are made
 */
public class Day11 {

  // For searching in all 8 directions,
  // {Left Up, Left, Down Left, Up, Down, Right, Down Right}
  private static final int[] xDir = {-1, -1, -1, 0, 0, 1, 1, 1};
  private static final int[] yDir = {-1, 0, 1, -1, 1, -1, 0, 1};

  /** Traverse adjacent nodes to find number of occupied spots */
  public int neighbours(List<String> input, int x, int y) {
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

  public int neighbours2(List<String> input, int row, int col) {
    int count = 0;
    for (int dir = 0; dir < 8; dir++) {
      // Starting point for the current direction
      int rd = row + yDir[dir];
      int cd = col + xDir[dir];

      while (rd < input.size() && rd >= 0 && cd < input.get(0).length() && cd >= 0) {

        char c = input.get(rd).charAt(cd);
        if (c == '#') count++;

        rd += yDir[dir];
        cd += xDir[dir];
      }
    }
    return count;
  }

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
    List<String> input =
        Files.readAllLines(Path.of("/Users/nam/dev/advent-of-code-2020/day11/input.txt"));
    boolean runPartB = true;

    /*int t = dy.neighbours2(input, 4, 4);
    System.out.println(t);*/
    // Part 1

    while (true) {
      List<String> round = new ArrayList<>();

      for (int row = 0; row < input.size(); row++) {
        StringBuilder sb = new StringBuilder();
        for (int col = 0; col < input.get(row).length(); col++) {
          char seat = input.get(row).charAt(col);
          if (!runPartB) {
            int adj = dy.neighbours(input, row, col);
            if (seat == 'L' && adj < 1) sb.append('#');
            else if (seat == '#' && adj > 3) sb.append('L');
            else sb.append(input.get(row).charAt(col));
          } else {
            int adj = dy.neighbours2(input, row, col);
            if (seat == 'L' && adj <= 0) sb.append('#');
            else if (seat == '#' && adj >= 5) sb.append('L');
            else sb.append(input.get(row).charAt(col));
          }
        }
        round.add(sb.toString());
      }
      // Stabilized
      if (input.equals(round)) break;
      input = round;
    }

    dy.printSeats(input);
    System.out.println("Occupied seats: " + dy.occupiedSeats(input));
  }
}
