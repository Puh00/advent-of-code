import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day12 {

  /** To simplify the calculations of the coordinates */
  enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0);

    int xDir;
    int yDir;

    Direction(int xDir, int yDir) {
      this.xDir = xDir;
      this.yDir = yDir;
    }
  }

  /** Rotate by intervals of 90 degrees, clockwise */
  public Direction rotateClockWise(Direction ord, int n) {
    return Direction.values()[Math.floorMod((ord.ordinal() + n), 4)];
  }

  public static void main(String[] args) throws IOException {
    Day12 dy = new Day12();
    List<String> input = Files.readAllLines(Path.of("input.txt"));
    boolean runPartB = true;

    Direction dir = Direction.EAST;
    // Ship coordinates
    int sx = 0;
    int sy = 0;
    // Waypoint coordinates
    int wx = 10;
    int wy = 1;

    for (String s : input) {
      char op = s.charAt(0);
      int val = Integer.parseInt(s.substring(1, s.length()));
      if (!runPartB) {
        // Part A
        switch (op) {
          case 'N': sy += val; break;
          case 'S': sy -= val; break;
          case 'E': sx += val; break;
          case 'W': sx -= val; break;
          case 'L':
            dir = dy.rotateClockWise(dir, -(val / 90));
            break;
          case 'R':
            dir = dy.rotateClockWise(dir, (val / 90));
            break;
          case 'F':
            sx += dir.xDir * val;
            sy += dir.yDir * val;
            break;
          default:
            throw new IllegalArgumentException("Invalid op code");
        }
      } else {
        // Part B
        switch (op) {
          case 'N': wy += val; break;
          case 'S': wy -= val; break;
          case 'E': wx += val; break;
          case 'W': wx -= val; break;
          case 'L':
            for (int i = 0; i < val / 90; i++) {
              // Old x-axis becomes the new y-axis,
              // old y-axis becomes the new x-axis but the inverse
              int temp = wy;
              wy = wx;
              wx = temp * -1;
            }
            break;
          case 'R':
            for (int i = 0; i < val / 90; i++) {
              // Old x-axis becomes the new y-axis but the inverse
              // old y-axis becomes the new x-axis
              int temp = wy;
              wy = wx * -1;
              wx = temp;
            }
            break;
          case 'F':
            sx += wx * val;
            sy += wy * val;
            break;
          default:
            throw new IllegalArgumentException("Invalid op code");
        }
      }
    }
    System.out.println("Manhattan distance: " + (Math.abs(sx) + Math.abs(sy)));
  }
}
