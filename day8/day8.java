import java.io.*;
import java.nio.file.*;
import java.util.*;

public class day8 {

  public int getVal(String s) {
    String inp = s.trim();
    char op = inp.charAt(0);
    int val = Integer.parseInt(inp.substring(1, inp.length()));
    return op == '+' ? val : -val;
  }

  public static void main(String[] args) throws IOException {
    String filepath = "/Users/nam/dev/advent-of-code-2020/day8/input.txt";
    day8 dy = new day8();
    List<String> input = Files.readAllLines(Path.of(filepath));
    Set<Integer> index = new HashSet<>();

    int acc = 0;
    int i = 0;
    while (!index.contains(i)) {
      index.add(i);
      String[] temp = input.get(i).split(" ");
      String inst = temp[0];
      int val = dy.getVal(temp[1]);
      if (inst.equals("jmp")) {
        i += val;
        continue;
      } else if (inst.equals("acc")) acc += val;
      i++;
    }
    System.out.println("Part 1: " + acc);
  }
}
