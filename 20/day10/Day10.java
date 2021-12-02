import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day10 {

  static Map<Integer, Long> visitedIndices;

  /** Part 1, number of 1-jolt diff multiplied by the number of 3-jolt diff */
  public Integer prodOfDiff(List<Integer> input) {
    // Store number of occurences of the differences 1, 2, and 3
    Map<Integer, Integer> mp = new HashMap<>();
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
    return mp.get(1) * mp.get(3);
  }

  /** Part 2, Recursively iterate until the end */
  public long arrangements(List<Integer> input, int index) {
    // Base case, reached the end of the input
    if (index == input.size() - 1) return 1;

    long total = 0;
    int nextIndex = index + 1;
    // Current value of the index
    int curVal = input.get(index);

    // Recursively find number of arrangements on the next index, given that it satisfies the
    // adapter's joltage range
    while (nextIndex < input.size() && input.get(nextIndex) - curVal <= 3) {
      // If the current index has already been visited before, return the number of arrangements
      if (visitedIndices.containsKey(curVal)) return visitedIndices.get(curVal);
      total += arrangements(input, nextIndex);
      nextIndex++;
    }
    // Store number of arrangements for the current index for the pending iterations
    visitedIndices.put(curVal, total);
    return total;
  }

  public static void main(String[] args) throws IOException {
    Day10 dy = new Day10();
    List<Integer> input =
        Files.readAllLines(Path.of("input.txt")).stream()
            .map(x -> Integer.parseInt(x))
            .collect(Collectors.toList());

    // Part 1
    System.out.println("Part 1: " + dy.prodOfDiff(input));

    // Part 2
    visitedIndices = new HashMap<>();
    System.out.println("Part 2: " + dy.arrangements(input, 0));
  }
}
