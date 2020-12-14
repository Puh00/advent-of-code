import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day7Part1 {

  /**
   * Parses the bags into a map in the following structure: Map<Parent Bag, LinkedList<Child bags>>
   */
  public Map<String, LinkedList<String>> parseBags(String filepath) throws IOException {

    List<String> input = Files.readAllLines(Path.of(filepath));
    Map<String, LinkedList<String>> bags = new HashMap<>();

    for (String s : input) {
      // [Bag, bags inside [0]]
      String[] temp = s.split("contain");
      String key = formatString(temp[0]);

      LinkedList<String> cont = new LinkedList<>();
      String[] tempy = temp[1].split(",");
      for (int i = 0; i < tempy.length; i++) {
        String sack = formatString(tempy[i]);
        if (sack.equals("no other")) cont = null;
        else cont.add(sack);
      }
      bags.put(key, cont);
    }
    return bags;
  }

  /** Recursively search all bags */
  public boolean containsShinyBag(Map<String, LinkedList<String>> bags, String key) {
    // The bags that the key contain
    LinkedList<String> cont = bags.get(key);
    // Base case
    if (cont == null) return false;
    for (String s : cont) {
      if (s.equals("shiny gold")) return true;
      if (containsShinyBag(bags, s)) return true;
    }
    return false;
  }

  // Trims, removes numbers, dots and the word "bag(s)"
  public String formatString(String bag) {
    char[] temp = bag.toCharArray();
    for (int i = 0; i < bag.length(); i++) {
      if (Character.isDigit(bag.charAt(i))) temp[i] = ' ';
      else if (bag.charAt(i) == '.') temp[i] = ' ';
    }
    String ret = String.valueOf(temp).trim();
    // Since both bag and bags occurr...
    if (ret.contains("bag")) {
      StringBuilder sb = new StringBuilder();
      sb.append(ret.substring(0, ret.indexOf("bag")));
      ret = sb.toString().trim();
    }
    return ret;
  }

  // Gets the amount of shiny gold bags
  public int nShinyBags(Map<String, LinkedList<String>> bags) {
    int count = 0;
    for (String key : bags.keySet()) {
      if (containsShinyBag(bags, key)) count++;
    }
    return count;
  }

  public static void main(String[] args) throws IOException {
    Day7Part1 bg = new Day7Part1();
    Map<String, LinkedList<String>> bags =
        bg.parseBags("/Users/nam/dev/advent-of-code-2020/day7/input.txt");
    System.out.println(
        "Number of bags that contains at least a shiny gold bag: " + bg.nShinyBags(bags));
  }
}
