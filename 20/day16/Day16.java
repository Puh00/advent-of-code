import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day16 {

  public List<String> parseTicket(String filePath) throws FileNotFoundException {
    List<String> input = new ArrayList<>();
    Scanner sn = new Scanner(new File(filePath));

    sn.useDelimiter("(?m:^$)");
    while (sn.hasNext()) {
      input.add(sn.next());
    }
    return input;
  }

  public List<Field> parseBounds(String rules) {
    String[] r = rules.split("\n");
    List<Field> bounds = new ArrayList<>();
    for (String s : r) {
      bounds.add(parseBound(s.split(":")));
    }
    return bounds;
  }

  private Field parseBound(String[] b) {
    String[] hoho = b[1].trim().split("or");

    String[] lo = hoho[0].trim().split("-");
    String[] hi = hoho[1].trim().split("-");
    Pair<Integer, Integer> low = new Pair<>(Integer.parseInt(lo[0]), Integer.parseInt(lo[1]));
    Pair<Integer, Integer> hig = new Pair<>(Integer.parseInt(hi[0]), Integer.parseInt(hi[1]));
    return new Field(b[0], low, hig);
  }

  private static class Field {
    public final String field;
    public final Pair<Integer, Integer> lowerBound;
    public final Pair<Integer, Integer> upperBound;

    public Field(
        String field, Pair<Integer, Integer> lowerBound, Pair<Integer, Integer> upperBound) {
      this.field = field;
      this.lowerBound = lowerBound;
      this.upperBound = upperBound;
    }
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
    List<Field> bounds = dy.parseBounds(input.get(0));

    // _______________________PART 1
    String[] tickets = input.get(2).trim().split("\n");
    int i = 1;
    List<Integer> invalids = new ArrayList<>();
    List<String> valids = new ArrayList<>();

    while (i < tickets.length) {
      String[] nums = tickets[i].split(",");
      boolean validTicket = true;
      for (String s : nums) {
        int val = Integer.parseInt(s);
        boolean isValid = false;
        for (Field f : bounds) {
          if ((val >= f.lowerBound.x && val <= f.lowerBound.y)
              || (val >= f.upperBound.x && val <= f.upperBound.y)) isValid = true;
        }
        if (!isValid) {
          invalids.add(val);
          validTicket = false;
        }
      }
      if (validTicket) valids.add(tickets[i]);
      i++;
    }
    System.out.println("Part 1: " + invalids.stream().reduce(0, Integer::sum));

    // _______________________PART 2

    // Get all possibilities
    Map<Integer, List<String>> possibilities = new HashMap<>();
    for (Field f : bounds) {
      String fieldName = f.field.trim();

      for (int x = 0; x < valids.get(0).split(",").length; x++) {
        boolean validIndex = true;
        for (int y = 0; y < valids.size(); y++) {
          // Current value
          int val = Integer.parseInt(valids.get(y).split(",")[x]);
          // Check if all rows satisfies the criteria
          if (!((val >= f.lowerBound.x && val <= f.lowerBound.y)
              || (val >= f.upperBound.x && val <= f.upperBound.y))) {
            validIndex = false;
            break;
          }
        }
        if (validIndex) {
          List<String> temp;
          if (possibilities.get(x) == null) {
            temp = new ArrayList<>();
          } else {
            temp = possibilities.get(x);
          }
          temp.add(fieldName);
          possibilities.put(x, temp);
        }
      }
    }

    // Fix the possibilities
    Map<String, Integer> solution = new HashMap<>();
    // Iterate until all keys have found their field
    while (solution.keySet().size() < bounds.size()) {
      for (Integer k : possibilities.keySet()) {
        if (possibilities.get(k).size() == 1) {
          String label = possibilities.get(k).get(0);
          solution.put(label, k);

          // Remove from all the other lists
          for (Integer z : possibilities.keySet()) {
            possibilities.get(z).remove(label);
          }
        }
      }
    }

    // Multiply value together from "your ticket"
    String[] yourTicket = input.get(1).trim().split("\n")[1].split(",");
    long count = 1;
    for (String s : solution.keySet()) {
      String field = s.trim().split(" ")[0];
      if (field.equals("departure")) {
        int index = solution.get(s);
        count *= Integer.parseInt(yourTicket[index]);
      }
    }
    System.out.println("Part 2 : " + count);
  }
}
