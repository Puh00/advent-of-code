import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class PasswordChecker {

    // Assuming that numbers are separated by the regex "-"
    public int[] getBounds(String num) {
        int[] bounds = Arrays.stream(num.split("-"))
                        .map(Integer::parseInt)
                        .mapToInt(x -> x).toArray();
        return bounds;
    }

    public char getLetter(String letter) {
        String lt = letter.chars()
                .filter(Character::isLetter)
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
        return lt.charAt(0);
    }

    /**
     * Part 1
     */
    public boolean withinBounds(String pw, char ch, int lo, int hi) {
        int count = 0;
        for (int i = 0; i < pw.length(); i++) {
            if (pw.charAt(i) == ch) 
                count++;
        }
        return count >= lo && count <= hi ? true : false;
    }

    /**
     * Part 2
     */
    public boolean definedAtPos(String pw, char ch, int x1, int x2) {
        int count = 0;
        if (pw.charAt(x1) == ch) 
            count++;
        if (pw.charAt(x2) == ch)
            count++;
        return count == 1; 
    }

    public static void main(String[] args) throws IOException {
         String filepath = "input.txt";
         //Store each line as a string in an array
         List<String> input = Files.readAllLines(Path.of(filepath));
         // Remove newlines [\n]
         input.removeAll(Collections.singleton(""));
         PasswordChecker pb = new PasswordChecker();
         
         int i = 0;
         int policyOne = 0;
         int policyTwo = 0;

         while(true) {
             try {
                 Scanner sn = new Scanner(input.get(i));
                 int[] bounds = pb.getBounds(sn.next());
                 char ch = pb.getLetter(sn.next());
                 String pw = sn.next();

                 // Part 1
                 if (pb.withinBounds(pw, ch, bounds[0], bounds[1]))
                     policyOne++;

                 //P Part 2
                 if (pb.definedAtPos(pw, ch, bounds[0]-1, bounds[1]-1))
                     policyTwo++;
                     
                 i++;
                 // Break out if index exceedes array size
                 if(i >= input.size())
                     break;

             } catch (Exception e) {
                 System.out.println("Parse error");
                 e.printStackTrace();
                 break;
             }
         }

         System.out.println("Number of valid passwords with regards to policy 1: " + policyOne);
         System.out.println("Number of valid passwords with regards to policy 2: " + policyTwo);
    }
}

