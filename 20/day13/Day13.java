import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day13 {

  public List<Integer> parseInput(String input) {
    String[] temp = input.split(",");
    List<Integer> numbersMason = new ArrayList<>();
    for (String s : temp) {
      if (!s.equals("x")) numbersMason.add(Integer.parseInt(s));
    }
    return numbersMason;
  }

  public int onTime(List<Integer> input, long time) {
    for (Integer i : input) {
      if (time % i == 0) return i;
    }
    return -1;
  }

  /**
   * Utilize the fact that all of the bus ids are primes, 
   * LCM(bus1, bus2) = bus1 * bus2, if bus1 and bus2 are primes
   *  - LCM gives us the the time where both buses are in "sync"
   *    - i.e LCM(bus3,bus7) = 21, both buses appear at 21
   */  
  public long solve(String[] dep) {
    long time = 0;
    long stepSize = Long.parseLong(dep[0]);

    for (int i = 1; i < dep.length; i++) {
      if (dep[i].equals("x")) continue;
      long bus = Long.parseLong(dep[i]);

      // Loop until we find a timeslot that's divisible by the bus
      // Increasing by the stepSize doesn't violate the invariant,
      // since stepSize is LCM(bus1, bus2, ...)
      while ((time + i) % bus != 0) {
        time += stepSize;
      }
      // LCM(bus1, bus2) = bus1 * bus2
      stepSize *= bus;
    }
    return time;
  }

  public static void main(String[] args) throws IOException {
    Day13 dy = new Day13();
    List<String> input =
        Files.readAllLines(Path.of("/Users/nam/dev/advent-of-code-2020/day13/input.txt"));

    // Part 1
    List<Integer> buses = dy.parseInput(input.get(1));
    long startTime = Long.parseLong(input.get(0));
    long time = startTime;
    int busId = -1;
    while (true) {
      int val = dy.onTime(buses, time);
      if (val != -1) {
        busId = val;
        break;
      }
      time++;
    }
    System.out.println("Part 1: " + (busId * (time - startTime)));

    // Part 2
    String[] departures = input.get(1).split(",");
    System.out.println("Part 2: " + dy.solve(departures));
  }
}
