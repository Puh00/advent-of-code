import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day8 {

  /** Retrieves the number from the instruction */
  private int getVal(String s) {
    String inp = s.trim();
    char op = inp.charAt(0);
    int val = Integer.parseInt(inp.substring(1, inp.length()));
    return op == '+' ? val : -val;
  }

  /**
   * Returns if the run was successful, the final value of the accumulator, and a set of the visited
   * instruction's addresses
   */
  private Truple<Boolean, Integer, Set<Integer>> runInstructions(List<String> input) {
    Set<Integer> visitedInstructions = new HashSet<>();
    Integer acc = 0;
    int i = 0;
    while (!visitedInstructions.contains(i) && i < input.size()) {
      visitedInstructions.add(i);
      String[] instruction = input.get(i).split(" ");
      String opCode = instruction[0];
      int val = getVal(instruction[1]);
      if (opCode.equals("jmp")) {
        i += val;
        continue;
      } else if (opCode.equals("acc")) acc += val;
      i++;
    }
    Boolean isSuccessfulRun = i >= input.size();
    return new Truple<Boolean, Integer, Set<Integer>>(isSuccessfulRun, acc, visitedInstructions);
  }

  /** Change the visited instructions (jmp/nop) and see if it resolves the infinite loop */
  public Integer restoreInstructions(List<String> input) {
    Set<Integer> visitedIns = runInstructions(input).z;
    for (Integer k : visitedIns) {
      String[] op = input.get(k).split(" ");
      if (op[0] != "acc") {
        String newOp = "nop " + op[1];
        List<String> temp = new ArrayList<>(input);
        temp.set(k, newOp);
        Truple<Boolean, Integer, Set<Integer>> trup = runInstructions(temp);
        // Successfully went through the last instruction
        if (trup.x) return trup.y;
      }
    }
    return -1;
  }

  private class Truple<X, Y, Z> {
    public final X x;
    public final Y y;
    public final Z z;

    public Truple(X x, Y y, Z z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }
  }

  public static void main(String[] args) throws IOException {
    String filepath = "input.txt";
    Day8 dy = new Day8();
    List<String> input = Files.readAllLines(Path.of(filepath));
    Integer acc = dy.restoreInstructions(input);
    System.out.println("The final value of the accumulator: " + acc);
  }
}
