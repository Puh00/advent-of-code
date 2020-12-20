import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day16 {

  public List<String> parseTicket(String filePath) throws FileNotFoundException {
    List<String> input = new ArrayList<>();
    Scanner sn = new Scanner(new File(filePath));

    // Each passport is their own paragraph, separated by \n
    sn.useDelimiter("(?m:^$)");
    while (sn.hasNext()) {
      input.add(sn.next());
    }
    return input;
  }

  /**
   * has some bugs...
   */
  public List<Pair<Integer, Integer>> parseBounds(String rules) {
    String[] r = rules.split("\n");
    //System.out.println(Arrays.toString(r));
    List<Pair<Integer, Integer>> bounds = new ArrayList<>();
    for (String s : r) {
      bounds.addAll(parseBound(s.split(" ")));
    }
    return bounds;
  }

  private List<Pair<Integer, Integer>> parseBound(String[] b) {
    List<Pair<Integer, Integer>> temp = new ArrayList<>();
    int i = 1;
    while (i < 4) {
      String[] curr = b[i].trim().split("-");
      int lowerBound = Integer.parseInt(curr[0]);
      int upperBound = Integer.parseInt(curr[1]);
      temp.add(new Pair<Integer,Integer>(lowerBound,upperBound));
      i = i + 2;
    }
    return temp;
  }

  private static class Pair<X, Y> {
    public final X x;
    public final Y y;

    public Pair(X x, Y y) {
      this.x = x;
      this.y = y;
    }
  }

  public static void main(String[] args) throws IOException {
    Day16 dy = new Day16();
    List<String> input = dy.parseTicket("input.txt");
    List<Pair<Integer, Integer>> bounds = dy.parseBounds(input.get(0));
    //System.out.println(input);
    //for (Pair<Integer, Integer> p : bounds) System.out.println("L: " + p.x + ", U:" + p.y);

    String[] tickets = input.get(2).trim().split("\n");
    int i = 1;
    List<Integer> invalids = new ArrayList<>();

    while (i < tickets.length) {
      String[] nums = tickets[i].split(",");
      for (String s : nums) {
        int val = Integer.parseInt(s);
        boolean isValid = false;
        for (Pair<Integer,Integer> p : bounds) {
          if (val >= p.x && val <= p.y)
            isValid = true;
        }
        if (!isValid)
          invalids.add(val);
      }
      i++;
    }
    System.out.println(invalids);
    System.out.println("Sum: " + invalids.stream().reduce(0, Integer::sum));
  }
}
