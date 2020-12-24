import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day17 {

  public Set<Coord> getActiveNodes(List<String> lines) {
    Set<Coord> coords = new HashSet<>();
    for (int row = 0; row < lines.size(); row++) {
      for (int col = 0; col < lines.get(0).length(); col++) {
        final char ch = lines.get(row).charAt(col);
        if (ch == '#') coords.add(new Coord(row, col, 0, 0));
      }
    }
    return coords;
  }

  private Set<Coord> getNeighbours(Coord c) {
    Set<Coord> neighbours = new HashSet<>();
    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        for (int dz = -1; dz <= 1; dz++) {
          for (int dw = -1; dw <= 1; dw++) {
            Coord neighbour = new Coord(c.x + dx, c.y + dy, c.z + dz, c.w + dw);
            neighbours.add(neighbour);
          }
        }
      }
    }
    neighbours.remove(c);
    return neighbours;
  }

  public Set<Coord> stepSimulation(Set<Coord> activeNodes) {
    Set<Coord> newNodes = new HashSet<>();
    Map<Coord,Integer> inactives = new HashMap<>();

    for (Coord c : activeNodes) {
      Set<Coord> neighbours = getNeighbours(c);
      int nActiveNodes = 0;

      // Iterate over the neighbours, check if they are active from previours round
      for (Coord n : neighbours) {
        if (activeNodes.contains(n)) nActiveNodes++;
        else {
          // To check inactives
          int count = inactives.getOrDefault(n, 0);
          inactives.put(n, count + 1);
        }
      }

      // Active remains active if 2 or 3 of its neighbours are active
      if (nActiveNodes == 2 || nActiveNodes == 3)
        newNodes.add(c);
    }

    // Inactive becomes active if 3 of its neighbours are active
    for (Coord c : inactives.keySet()) {
      if (inactives.get(c) == 3)
        newNodes.add(c);
    }

    return newNodes;
  }

  public static void main(String[] args) throws IOException {
    List<String> input = Files.readAllLines(Path.of("input.txt"));
    Day17 dy = new Day17();

    Set<Coord> state = dy.getActiveNodes(input);
    for (int i = 0; i < 6; i++) {
      state = dy.stepSimulation(state);
    }

    System.out.println("Active nodes: " + state.size());
  }

  // ##############################
  // Util                         #
  // ##############################

  private static class Coord {
    public final int x;
    public final int y;
    public final int z;
    public final int w;

    public Coord(int x, int y, int z, int w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
    }

    public String toString() {
      return String.format("[%d, %d, %d, %d]", x, y, z, w);
    }

    public boolean equals(Object other) {
      return this.toString().equals(other.toString());
    }

    public int hashCode() {
      return this.toString().hashCode();
    }
  }
}
