import java.util.*;

// TODO
// # Since there are no circular directions,
//   solve it with recursion!

public class Day7 {

  public boolean containsShiny(Map<String, LinkedList<String>> map, String key) {
    LinkedList<String> temp = map.get(key);
    if (temp == null) return false;
    for (String s : temp) {
      if (s.equals("gold")) return true;
      containsShiny(map, s);
    }
    return false;
  }

  public static void main(String[] args) {
    LinkedList<String> ld = new LinkedList<>();
    ld.add("white");
    ld.add("yellow");
    Map<String, LinkedList<String>> test = new HashMap<>();

    test.put("red", ld);

    LinkedList<String> ld1 = new LinkedList<>();
    ld.add("gold");
    test.put("white", ld1);

    LinkedList<String> ld2 = new LinkedList<>();
    ld2.add("gold");
    ld2.add("blue");
    test.put("yellow", ld2);

    Day7 d = new Day7();
    System.out.println(d.containsShiny(test, "red"));

    // It does indeed return a null
    System.out.println(test.get("no"));
  }
}
