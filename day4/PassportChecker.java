import java.io.FileNotFoundException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class PassportChecker {

    public List<String> parsePassports(String filepath) throws FileNotFoundException {
        List<String> input = new ArrayList<>();
        Scanner sn = new Scanner(new File(filepath));

        // Each passport is their own paragraph, separated by \n
        sn.useDelimiter("(?m:^$)");
        while (sn.hasNext()) {
            input.add(sn.next());
        }
        return input;
    }

    // Checks if the entry has the required fields
    public boolean validateFields(List<String> entry) {
        List<String> rules = Arrays.asList("hcl", "iyr", "eyr", "ecl", "pid", "byr", "hgt");
        return entry.containsAll(rules);
    }

    // Checks if a passport entry is valid
    public boolean validatePassport(String pp) {
        Scanner sn = new Scanner(pp);
        List<String> fields = new ArrayList<>();
        while (sn.hasNext()) {
            // Get the key
            String[] temp = sn.next().split(":");
            fields.add(temp[0]);
        }
        return validateFields(fields);
    }

    // Counts how many passports are valid
    public int validateBatch(List<String> batch) {
        int count = 0;
        for (String s : batch) {
            if (validatePassport(s))
                count++;
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        PassportChecker pc = new PassportChecker();
        String filepath = "input.txt";

        List<String> input = pc.parsePassports(filepath);
        int nValidPass = pc.validateBatch(input);
        System.out.println("Number of valid passports: " + nValidPass);
        
    }
}
