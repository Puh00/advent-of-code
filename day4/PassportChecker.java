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

    //########################################
    // Part 1
    //########################################

    // Checks if the entry has the required fields
    public boolean validateFields(List<String> entry) {
        List<String> rules = Arrays.asList("hcl", "iyr", "eyr", "ecl", "pid", "byr", "hgt");
        return entry.containsAll(rules);
    }

    // Checks if a passport entry is valid
    public boolean validatePassportA(String pp) {
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
    public int validateBatchA(List<String> batch) {
        int count = 0;
        for (String s : batch) {
            if (validatePassportA(s))
                count++;
        }
        return count;
    }


    //########################################
    // Part 2
    //########################################
    

    public boolean checkBirthYear(int by) {
        return by >= 1920 && by <= 2002;
    }

    public boolean checkIssueYear(int iy) {
        return iy >= 2010 && iy <= 2020;
    }

    public boolean checkExpYear(int ey) {
        return ey >= 2020 && ey <= 2030;
    }

    public boolean checkHeight(String height) {
        // Splits digits and letters
        String[] hData = height.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        if (hData.length > 1) {
            String scale = hData[1];
            int h = Integer.parseInt(hData[0]);

            switch (scale) {
                case "cm":
                    return h >= 150 && h <= 193;
                case "in":
                    return h >= 59 && h <= 76;
                default:
                    return false;
            }
        } else return false;
    }

    public boolean checkHairColour(String hcl) {
        if (hcl.charAt(0) != '#')
            return false;

        int count = 0;
        for(int i = 1; i < hcl.length(); i++) {
            if (!isDigitOrLetter(hcl.charAt(i)))
                return false;
            count++;
        }
        return (hcl.length() - 1) == 6;
    }

    private boolean isDigitOrLetter(char c) {
        return Character.isDigit(c) || Character.isLetter(c);
    }

    public boolean checkEyeColour(String ecl) {
        List<String> validColours = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        return validColours.contains(ecl);
    }

    public boolean checkPID(String pid) {
        for (int i = 0; i < pid.length(); i++) {
            if(!Character.isDigit(pid.charAt(i)))
                return false;
        }
        return pid.length() == 9;
    }

    // Iterates through each "paragraph", -> passport
    public int validateBatchB(List<String> batch) {
        int count = 0;
        for (String s : batch) {
            if (validatePassportB(s))
                count++;
        }
        return count;
    }

    // Stores each key:value pair 
    public boolean validatePassportB(String pp) {
        Scanner sn = new Scanner(pp);
        Map<String,String> fields = new HashMap<>();
        while (sn.hasNext()) {
            String[] keyVal = sn.next().split(":");
            fields.put(keyVal[0], keyVal[1]);
        }
        return validateFieldsB(fields);
    }

    /**
     * This is disgusting
     */
    public boolean validateFieldsB(Map<String,String> fields) {
        List<String> rules = Arrays.asList("hcl", "iyr", "eyr", "ecl", "pid", "byr", "hgt");
        // Check if all keys are present
        for (String s : rules) {
            if (!fields.containsKey(s))
                return false;
        }

        for (String key : fields.keySet()) {
            switch (key) {
                case "byr":
                    if (checkBirthYear(Integer.parseInt(fields.get(key))))
                        continue;
                    else 
                        return false;
                case "iyr":
                    if (checkIssueYear(Integer.parseInt(fields.get(key))))
                        continue;
                    else 
                        return false;
                case "eyr":
                    if (checkExpYear(Integer.parseInt(fields.get(key))))
                        continue;
                    else 
                        return false;
                case "hgt":
                    if (checkHeight(fields.get(key)))
                        continue;
                    else 
                        return false;
                case "hcl":
                    if (checkHairColour(fields.get(key)))
                        continue;
                    else 
                        return false;
                case "ecl":
                    if (checkEyeColour(fields.get(key)))
                        continue;
                    else 
                        return false;
                case "pid":
                    if (checkPID(fields.get(key)))
                        continue;
                    else 
                        return false;
                default:
                    break;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        PassportChecker pc = new PassportChecker();
        String filepath = "input.txt";

        // Part 1
        List<String> input = pc.parsePassports(filepath);
        int nValidPassA = pc.validateBatchA(input);
        System.out.println("Part 1: " + nValidPassA + " valid passports");
        

        // Part 2
        int nValidPassB = pc.validateBatchB(input);
        System.out.println("Part 2: " + nValidPassB + " valid passports");
    }
}

