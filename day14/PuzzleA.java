import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PuzzleA {

  public long bitmaskOr(String mask) {
    char[] arr = mask.toCharArray();
    for (int i = 0; i < arr.length; i++) if (arr[i] == 'X') arr[i] = '0';
    return Long.parseLong(String.valueOf(arr), 2);
  }

  public long bitmaskAnd(String mask) {
    char[] arr = mask.toCharArray();
    for (int i = 0; i < arr.length; i++) if (arr[i] == 'X') arr[i] = '1';
    return Long.parseLong(String.valueOf(arr), 2);
  }

  public static void main(String[] args) throws IOException {
    PuzzleA dy = new PuzzleA();
    List<String> input = Files.readAllLines(Path.of("input.txt"));

    Map<Integer, Long> memory = new HashMap<>();
    long bitmaskAnd = -1;
    long bitmaskOr = -1;
    for (String s : input) {
      String[] op = s.replaceAll("\\s", "").split("=");
      String instruction = op[0];
      String value = op[1];

      // Save the mask for the pending allocations
      if (instruction.equals("mask")) {
        bitmaskAnd = dy.bitmaskAnd(value);
        bitmaskOr = dy.bitmaskOr(value);
        continue;
      }

      int address = Integer.parseInt(instruction.replaceAll("[^-0-9]+", ""));
      long val = Long.parseLong(value);
      // Mask the bits accordingly
      memory.put(address, (val & bitmaskAnd) | bitmaskOr);
    }

    // Sum
    long sum = 0;
    for (Integer i : memory.keySet()) {
      sum += memory.get(i);
    }
    System.out.println("Sum: " + sum);
  }
}
