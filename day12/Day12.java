import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day12 {

  enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
  }

  // Rotate by intervals of 90 degrees clockwise
  public Direction rotateClockWise(Direction ord, int n) {
    return Direction.values()[Math.floorMod((ord.ordinal() + n), 4)];
  }

  public int manhattanDistance(Map<String, Integer> mp) {
    int xCord = Math.abs(mp.getOrDefault("N", 0) - mp.getOrDefault("S", 0));
    int yCord = Math.abs(mp.getOrDefault("E", 0) - mp.getOrDefault("W", 0));
    return xCord + yCord;
  }

  public static void main(String[] args) throws IOException {
    Day12 dy = new Day12();
    List<String> input = Files.readAllLines(Path.of("input.txt"));

    Direction dir = Direction.EAST;
    Map<String, Integer> pos = new HashMap<>();
    for (String s : input) {
      char op = s.charAt(0);
      int val = Integer.parseInt(s.substring(1, s.length()));
      switch (op) {
        case 'N':
          pos.put("N", pos.getOrDefault("N", 0) + val);
          break;
        case 'S':
          pos.put("S", pos.getOrDefault("S", 0) + val);
          break;
        case 'E':
          pos.put("E", pos.getOrDefault("E", 0) + val);
          break;
        case 'W':
          pos.put("W", pos.getOrDefault("W", 0) + val);
          break;
        case 'L':
          dir = dy.rotateClockWise(dir, -(val / 90));
          System.out.println("changed dir: " + dir);
          break;
        case 'R':
          dir = dy.rotateClockWise(dir, (val / 90));
          System.out.println("changed dir: " + dir);
          break;
        case 'F':
          String d = dir.toString().substring(0, 1);
          pos.put(d, pos.getOrDefault(d, 0) + val);
          break;
      }
    }
    System.out.println(pos);
    System.out.println(dy.manhattanDistance(pos));
  }
}
