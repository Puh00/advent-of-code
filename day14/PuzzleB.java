import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class PuzzleB {

  /** Fills the missing leading zeroes */
  public String binaryFormat(String number, int bits) {
    int n = bits - number.length();
    String leadingZeroes = IntStream.range(0, n).mapToObj(i -> "0").collect(Collectors.joining(""));
    StringBuilder sb = new StringBuilder();
    sb.append(leadingZeroes).append(number);
    return sb.toString();
  }

  public static void main(String[] args) throws IOException {
    PuzzleB dy = new PuzzleB();
    List<String> input = Files.readAllLines(Path.of("input.txt"));

    Map<Long, Long> memory = new HashMap<>();
    String mask = "";
    for (String s : input) {
      String[] op = s.replaceAll("\\s", "").split("=");
      String instruction = op[0];

      // Save the bitmask for the pending allocations
      if (instruction.equals("mask")) {
        mask = op[1];
        continue;
      }

      // Address to be manipulated
      int address = Integer.parseInt(instruction.replaceAll("[^-0-9]+", ""));
      // Value to be stored
      long value = Long.parseLong(op[1]);

      // Ensures the 36-bit format for the address
      char[] binaryAddress = dy.binaryFormat(Long.toBinaryString(address), 36).toCharArray();

      // Mask the bits according to the rules and save the indices of the xs
      List<Integer> xs = new ArrayList<>();
      for (int i = 0; i < 36; i++) {
        if (mask.charAt(i) == '1') binaryAddress[i] = '1';
        else if (mask.charAt(i) == 'X') {
          binaryAddress[i] = 'X';
          xs.add(i);
        }
      }

      // Utilize the fact that n digits of the binary number is 2^n in decimal, i.e '10' -> 1*2^1
      // Write the value to each permutation of the binary number
      double k = Math.pow(2, xs.size());
      for (int i = 0; i < k; i++) {
        // Convert i to binary form
        String bin = dy.binaryFormat(Long.toBinaryString(i), xs.size());

        // Change the xs to the current binary number
        for (int y = 0; y < bin.length(); y++) {
          binaryAddress[xs.get(y)] = bin.charAt(y);
        }
        Long adr = Long.parseLong(String.valueOf(binaryAddress), 2);
        memory.put(adr, value);
      }
    }

    // Sum it up
    long sum = 0;
    for (Long i : memory.keySet()) sum += memory.get(i);
    System.out.println("Sum: " + sum);
  }
}
