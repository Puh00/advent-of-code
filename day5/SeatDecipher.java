import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SeatDecipher {

    public int calculateRow(String input) {
        double lo = 0;
        double hi = 127;
        int i = 0;
        while (lo < hi) {
            if (input.charAt(i) == 'F') {
                hi = Math.floor((hi - lo) / 2);
                hi += lo;
            } else if (input.charAt(i) == 'B') {
                double temp = lo;
                lo = Math.ceil((hi - lo) / 2);
                lo += temp;
            }
            i++;
        }
        return (int) lo;
    }

    public int calculateColumn(String input) {
        double lo = 0;
        double hi = 7;
        int i = 7;
        while (lo < hi) {
            if (input.charAt(i) == 'L') {
                hi = Math.floor((hi - lo) / 2);
                hi += lo;
            } else if (input.charAt(i) == 'R') {
                double temp = lo;
                lo = Math.ceil((hi - lo) / 2);
                lo += temp;
            }
            i++;
        }
        return (int) lo;
    }

    public int calculateSID(int row, int col) {
        return row * 8 + col;
    }

    /**
     * Part 1
     */
    public int calcMaxSID(List<String> input) {

        // Max priority queue
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        for (String s : input) {
            pq.add(calculateSID(calculateRow(s), calculateColumn(s)));
        }

        return pq.poll();
    }

    /**
     * Part 2
     */
    public void calculateSeat(List<String> input) {
        List<Integer> ids = new ArrayList<>();

        for (String s : input) {
            ids.add(calculateSID(calculateRow(s), calculateColumn(s)));
        }

        // Since the seat id +1/-1 from our seat is in the list
        Collections.sort(ids);
        for (int i = 1; i < ids.size(); i++) {
            if (ids.get(i) - ids.get(i - 1) == 2)
                System.out.println("Part 2: " + (ids.get(i) - 1));
        }
    }

    public static void main(String[] args) throws IOException {
        SeatDecipher sd = new SeatDecipher();
        String filepath = "input.txt";
        List<String> input = Files.readAllLines(Path.of(filepath));

        // Part 1
        int maxSID = sd.calcMaxSID(input);
        System.out.println("Maximum seat ID: " + maxSID);

        // Part 2
        sd.calculateSeat(input);
    }
}

