package pe;

import java.io.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Utils {

    // common assertion check for Project Euler problems
    // returns success string or exits application of result and answer are different
    public static String assertEq(long result, long answer, String problem) {
        if (answer != result) {
            System.out.println("ERROR: solution for " + problem + " is wrong.");
            System.out.println("calculated result = " + result);
            System.out.println("    actual answer = " + answer);
            System.out.println("\naborting Euler problems");
            System.exit(1);
        }
        return problem + " = " + result;
    }


    // return text file from resource folder
    public static List<String> readTextFile(String fileName) {

        //Get file from resources folder
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream(fileName);

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            return buffer.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


    // return single solution matching key s
    public static Supplier<String> getOneSolution(String s, TreeMap<Integer, Supplier<String>> m) {
        Scanner sc = new Scanner(s);
        if (!sc.hasNextInt()) {
            System.out.println("'" + s + "' is not a valid positive integer.\nAborting.");
            System.exit(1);
            return m.get(0);
        } else {
            int i = sc.nextInt();
            if (m.containsKey(i)) {
                return m.get(i);
            } else {
                System.out.println("Problem number '" + i + "' is not in the solution set.\nAborting.");
                System.exit(1);
                return m.get(0);
            }
        }
    }
}



