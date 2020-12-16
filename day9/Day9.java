import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.*;

public class Day9 {

  /**
   * # 2 of the 5 numbers prior to the current one should sum up to the current one # find the 1st
   * value that do not have this property - But preamble 25-numbers
   */
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

  public static void main(String[] args) throws IOException {
    Day9 dy = new Day9();
    List<Long> input =
        Files.readAllLines(Path.of("input.txt")).stream()
            .map(x -> Long.parseLong(x))
            .collect(Collectors.toList());

    int preambleSize = 25;
    for (int i = preambleSize; i < input.size(); i++) {
      List<Long> subArray =
          IntStream.range(i - preambleSize, i)
              .mapToObj(y -> input.get(y))
              .collect(Collectors.toList());

      if (!dy.validPreamble(subArray, input.get(i)))
        System.out.println("Not valid: " + input.get(i));
    }
  }
}
