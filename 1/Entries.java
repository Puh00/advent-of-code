import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Entries {

    /**
     * Part 1
     */
    Set<Integer> findTwoEntries (ArrayList<Integer> nums) {
        Set<Integer> matching = new HashSet<>();
        int n = nums.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Integer num1 = nums.get(i);
                Integer num2 = nums.get(j);                
                if (num1 + num2 == 2020)
                    matching.add(num1 * num2);
            }
        }
        return matching;
    }
    /**
     * Part 2
     */
    Set<Integer> findThreeEntries (ArrayList<Integer> nums) {
        Set<Integer> matching = new HashSet<>();
        int n = nums.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    Integer num1 = nums.get(i);
                    Integer num2 = nums.get(j);
                    Integer num3 = nums.get(k);

                    if (num1 + num2 + num3 == 2020)
                        matching.add(num1 * num2 * num3);
                }
            }
        }
        return matching;
    }

    public static void main(String[] args) {
        Entries et = new Entries();
        ArrayList<Integer> nums = et.readFile("input.txt");

        // Part 1
        Set<Integer> matchingTwoEntries = et.findTwoEntries(nums);
        System.out.println("The multiplication of the two entries that sum to 2020: ");
        System.out.println(matchingTwoEntries);

        // Part 2
        Set<Integer> matchingThreeEntries = et.findThreeEntries(nums);
        System.out.println("The multiplication of the three entries that sum to 2020: ");
        System.out.println(matchingThreeEntries);
    }

    /**
     * Util for storing the file's content into an array
     */
    ArrayList<Integer> readFile(String filepath) {
        ArrayList<Integer> temp = new ArrayList<>();
        try {
            File obj = new File(filepath);
            Scanner sn = new Scanner(obj);
            while (sn.hasNextLine()) {
                Integer n = Integer.parseInt(sn.nextLine());
                temp.add(n);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
