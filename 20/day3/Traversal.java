import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Traversal {

    /**
     * Counts the number of times the user encounters a tree [#], 
     * with respect to the given slope 
     */
    public long treeEncounters (int right, int down, List<String> input) {
        int width = input.get(0).length();
        long numTrees = 0;
        int xCord = 0; // X coordinate

        for (int i = 0; i < input.size(); i += down) {
            String s = input.get(i);
            if(s.charAt(xCord) == '#')
                numTrees++;
            xCord += right;
            //Since the terrain repeats itself
            xCord = xCord % width; 
        }
        return numTrees;
    }

    public long puzzleB(List<String> input) {
        long count = 1;
        count *= treeEncounters(1, 1, input);
        count *= treeEncounters(3, 1, input);
        count *= treeEncounters(5, 1, input);
        count *= treeEncounters(7, 1, input);
        count *= treeEncounters(1, 2, input);
        return count;
    }

    public static void main(String[] args) throws IOException {
        Traversal tv = new Traversal();
        String filePath = "input.txt";
        List<String> input = Files.readAllLines(Path.of(filePath));

        // Part 1
        System.out.println("Part 1: Number of tree encounters " + tv.treeEncounters(3, 1, input));

        // Part 2
        System.out.println("Part 2: The product of the different slopes: " + tv.puzzleB(input));
    }
}

