import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * # Sort, choose and check difference # Store in a hasmap where keys are 1, 2, 3 # Values are the
 * number of occurences
 */
public class Day10 {

  public static void main(String[] args) throws IOException {
    String filePath = "/Users/nam/dev/advent-of-code-2020/day10/input.txt";
    List<Integer> input =
        Files.readAllLines(Path.of(filePath)).stream()
            .map(x -> Integer.parseInt(x))
            .collect(Collectors.toList());
    Map<Integer, Integer> mp = new HashMap<>();

    // Part 1
    Collections.sort(input);
    // Outlet, starts from 0
    input.add(0, 0);
    for (int i = 1; i < input.size(); i++) {
      Integer diff = input.get(i) - input.get(i - 1);
      if (mp.get(diff) == null) mp.put(diff, 1);
      else mp.put(diff, mp.get(diff) + 1);
    }
    // Due to the device's inbuilt joltage adapter
    mp.put(3, mp.get(3) + 1);

    // info
    System.out.println(mp);
    Integer diff = mp.get(1) * mp.get(3);
    System.out.println("Part 1: " + diff);
  }
}
