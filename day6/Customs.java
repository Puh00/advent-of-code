import java.io.*;
import java.util.*;

public class Customs {

  // Each block is parsed as a string
  public List<String> parseGroups(String filepath) throws FileNotFoundException {
    List<String> input = new ArrayList<>();
    Scanner sn = new Scanner(new File(filepath));

    // Each group is their own 'block'
    sn.useDelimiter("(?m:^$)");
    while (sn.hasNext()) {
      input.add(sn.next());
    }
    return input;
  }

  // #####################################
  // # Part 1                            #
  // #####################################

  // Retrieves the amount of unique answers in a group
  public Set<String> groupUniqueAns(String input) {
    Set<String> ans = new HashSet<>();
    Scanner sn = new Scanner(input.strip());
    while (sn.hasNext()) {
      String cur = sn.next();
      for (int i = 0; i < cur.length(); i++) {
        ans.add(Character.toString(cur.charAt(i)));
      }
    }
    return ans;
  }

  public int puzzleA(List<String> input) {
    int count = 0;
    for (String s : input) {
      count += groupUniqueAns(s).size();
    }
    return count;
  }

  // #####################################
  // # Part 2                            #
  // #####################################

  // Retrieves the amount of answers that everyone has answered
  public int groupCommonAns(String input) {
    String temp = input.strip();
    Map<String, Integer> mp = new HashMap<>();
    Scanner sn = new Scanner(temp);
    try {
      while (sn.hasNext()) {
        String cur = sn.next();
        for (int i = 0; i < cur.length(); i++) {
          String ch = Character.toString(cur.charAt(i));
          Integer val = mp.get(ch);
          mp.put(ch, val == null ? 1 : val + 1);
        }
      }
    } finally {
      sn.close();
    }

    // Amount of people in the group
    int groupSize = temp.split("\n").length;

    // Calculate common answers
    int count = 0;
    for (String key : mp.keySet()) {
      if (mp.get(key) == groupSize) count++;
    }
    return count;
  }

  public int puzzleB(List<String> input) {
    int count = 0;
    for (String s : input) {
      count += groupCommonAns(s);
    }
    return count;
  }

  public static void main(String[] args) throws FileNotFoundException {
    Customs cs = new Customs();
    String filepath = "input.txt";

    List<String> input = cs.parseGroups(filepath);
    System.out.println("Part 1: " + cs.puzzleA(input));

    System.out.println("Part 2: " + cs.puzzleB(input));
  }
}
