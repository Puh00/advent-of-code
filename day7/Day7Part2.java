import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day7Part2 {

  // Stores the number too, as opposed to part 1's implementation
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

  // Trims, dots and the word "bag(s)"
  public String formatString(String bag) {
    char[] temp = bag.toCharArray();
    for (int i = 0; i < bag.length(); i++) {
      if (bag.charAt(i) == '.') temp[i] = ' ';
    }
    String ret = String.valueOf(temp).trim();
    if (ret.contains("bag")) {
      StringBuilder sb = new StringBuilder();
      sb.append(ret.substring(0, ret.indexOf("bag")));
      ret = sb.toString().trim();
    }
    return ret;
  }

  // Assuming that the numbers won't exceed 1 digit
  public String removeNumbers(String s) {
    char[] charArr = s.toCharArray();
    for (int i = 0; i < charArr.length; i++) {
      if (Character.isDigit(charArr[i])) charArr[i] = ' ';
    }
    return String.valueOf(charArr).trim();
  }

  // Number of bags inside the shiny golden bag
  public int nBagsInShinyBag(Map<String, LinkedList<String>> bags) {
    return recursiveBags(bags, "shiny gold") - 1;
  }

  // Recursively calculate the number of bags
  public int recursiveBags(Map<String, LinkedList<String>> bags, String key) {
    LinkedList<String> curr = bags.get(key);
    // Base case
    if (curr == null) return 1;
    int count = 1;
    for (String s : curr) {
      int n = Integer.parseInt(String.valueOf(s.charAt(0)));
      count += n * recursiveBags(bags, removeNumbers(s));
    }
    return count;
  }

  public static void main(String[] args) throws IOException {
    Day7Part2 bg = new Day7Part2();
    Map<String, LinkedList<String>> bags = bg.parseBags("input.txt");
    System.out.println("Part 2: " + bg.nBagsInShinyBag(bags));
  }
}
