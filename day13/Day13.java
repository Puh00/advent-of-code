import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day13 {

  public List<Integer> parseInput(String input) {
    String[] temp = input.split(",");
    List<Integer> numbersMason = new ArrayList<>();
    for (String s : temp) {
      if (!s.equals("x")) numbersMason.add(Integer.parseInt(s));
    }
    return numbersMason;
  }

  public int onTime(List<Integer> input, long time) {
    for (Integer i : input) {
      if (time % i == 0)
        return i;
    }
    return -1;
  }

  public static void main(String[] args) throws IOException {
    Day13 dy = new Day13();
    List<String> input = Files.readAllLines(Path.of("input.txt"));
    List<Integer> buses = dy.parseInput(input.get(1));

    long startTime = Long.parseLong(input.get(0));
    long time = startTime;
    int busId = -1;
    while (true) {
      int val = dy.onTime(buses, time);
      if(val != -1) {
        busId = val;
        break;
      }
      time++;
    }
    System.out.println(busId * (time - startTime));
  }
}
