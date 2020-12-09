import java.io.FileNotFoundException;
import java.io.File;
import java.util.*; 
import java.util.stream.*; 

public class PasswordChecker {

    public static void main(String[] args){
        ArrayList<String> correctPw = new ArrayList<>();
         
        String filepath = "input.txt";
        try {
            File obj = new File(filepath);
            Scanner sn = new Scanner(obj);
            while (sn.hasNextLine()) {
                //---------------Read input-----------------------
                // Retrieves the bounds, [0] => lo, [1] => hi
                String line = sn.next();
                List<Integer> bounds = Arrays.stream(line.split("-"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                
                // The char to be checked
                String letter = sn.next().chars()
                        .filter(Character::isLetter)
                        .mapToObj(Character::toString)
                        .collect(Collectors.joining());

                // The password
                String pw = sn.next();

                //---------------Logic----------------------------

                // Count how many times the char appears in the pw
                int count = 0;
                for (int i = 0; i < pw.length(); i++) {
                    if (pw.charAt(i) == letter.charAt(0)) 
                        count++;
                }

                // Check if the pw satisfies the predicate (bounds)
                if (count >= bounds.get(0) && count <= bounds.get(1)){
                    correctPw.add(pw);                   
                }
            }
        } catch (Exception e) {
            System.out.println("Parse error");
            e.printStackTrace();
        }

        System.out.println("Valid passwords: ");
        System.out.println(correctPw);
        System.out.println("Number of valid passwords: " + correctPw.size());
    }
}
