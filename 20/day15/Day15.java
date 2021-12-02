import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day15 {

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Path.of("input.txt"));
    String[] nums = input.trim().split(",");

    // <Number, Index>
    Map<Integer, Integer> cache = new HashMap<>();

    // Change number to 2020 or 30000000
    int number = 30000000;
    int i = 0;
    int prev = -1;

    while (i < number) {
      if (i < nums.length - 1) {
        cache.put(Integer.parseInt(nums[i]), i);
      } else if (i == nums.length - 1) prev = Integer.parseInt(nums[i]);
      else {
        int tempPrev = cache.get(prev) == null ? 0 : (i - 1) - cache.get(prev);
        cache.put(prev, i - 1);
        prev = tempPrev;
      }
      i++;
    }
    System.out.println(prev);
  }
}
