import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day9 {

  /** Checks if any pair sums up to the given 'sum', O(n) */
  public boolean validPreamble(List<Long> input, long sum) {
    Set<Long> others = new HashSet<>(input);
    boolean isValid = false;

    for (int i = 0; i < input.size(); i++) {
      long x = input.get(i);
      others.add(x);
      long y = sum - x;
      isValid |= others.contains(y); // Boolean logicalOr
    }
    return isValid;
  }

  /**
   * Finds a contiguous set which sums up to the given 'invalidNumber', returns the sum of the
   * extremes
   */
  public long xmasEncryption(List<Long> input, long invalidNumber) {
    for (int i = 0; i < input.size(); i++) {
      // Balanced BST for quick lookups on min and max
      SortedSet<Long> temp = new TreeSet<>();
      long sum = 0;
      int y = i;

      while (sum < invalidNumber && y < input.size()) {
        long cur = input.get(y++);
        if (cur != invalidNumber) {
          temp.add(cur);
          sum += cur;
        }
      }
      if (sum == invalidNumber) return temp.first() + temp.last();
    }
    return -1;
  }

  public static void main(String[] args) throws IOException {
    Day9 dy = new Day9();
    // Parsing
    List<Long> input =
        Files.readAllLines(Path.of("input.txt")).stream()
            .map(x -> Long.parseLong(x))
            .collect(Collectors.toList());

    // Part 1
    long invalidNumber = -1;
    int preambleSize = 25;
    // Iterate through the list and check if valid preamble
    for (int i = preambleSize; i < input.size(); i++) {
      List<Long> subArray =
          IntStream.range(i - preambleSize, i)
              .mapToObj(y -> input.get(y))
              .collect(Collectors.toList());
      if (!dy.validPreamble(subArray, input.get(i))) invalidNumber = input.get(i);
    }
    System.out.println("Part 1, invalid number: " + invalidNumber);

    // Part 2
    long sumMinMax = dy.xmasEncryption(input, invalidNumber);
    System.out.println("Part 2, sum of min and max of the contiguous set: " + sumMinMax);
  }
}
