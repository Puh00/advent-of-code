import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day14 {

  public long bitmaskOr(String mask) {
    char[] arr = mask.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == 'X') arr[i] = '0';
    }
    return Long.parseLong(String.valueOf(arr), 2);
  }

  public long bitmaskAnd(String mask) {
    char[] arr = mask.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == 'X') arr[i] = '1';
    }
    return Long.parseLong(String.valueOf(arr), 2);
  }

  public static void main(String[] args) throws IOException {
    Day14 dy = new Day14();
    List<String> input =
        Files.readAllLines(Path.of("/Users/nam/dev/advent-of-code-2020/day14/input.txt"));

    // Part A
    Map<Integer, Long> memory = new HashMap<>();
    long bitmaskAnd = -1;
    long bitmaskOr = -1;
    for (String s : input) {
      String[] op = s.replaceAll("\\s", "").split("=");
      if (op[0].equals("mask")) {
        bitmaskAnd = dy.bitmaskAnd(op[1]);
        bitmaskOr = dy.bitmaskOr(op[1]);
        continue;
      }

      int address = Integer.parseInt(op[0].replaceAll("[^-0-9]+", ""));
      long value = Long.parseLong(op[1]);
      memory.put(address, (value & bitmaskAnd) | bitmaskOr);
    }
    long sum = 0;
    for (Integer i : memory.keySet()) {
      sum += memory.get(i);
    }
    System.out.println("Sum: " + sum);
  }
}
