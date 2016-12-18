package pe;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Common {

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


    // returns true if value n is a palindrome (reads the same backward or forward)
    public static boolean isPalindrome(long n) {
        String s = String.valueOf(n);

        while (true) {
            int len = s.length();
            switch (len) {
                case 1:
                    return true;
                case 2:
                    return s.charAt(0) == s.charAt(1);
                default:
                    if (s.charAt(0) != s.charAt(len - 1))
                        return false;
                    else
                        s = s.substring(1, len - 1);
            }
        }
    }


    // recursive version
    // returns true if value n is a palindrome (reads the same backward or forward)
    public static boolean isPalindrome2(long n) {
        return isPalindromeHelper(String.valueOf(n));
    }

    private static boolean isPalindromeHelper(String s) {
        int len = s.length();
        switch (len) {
            case 1:
                return true;
            case 2:
                return s.charAt(0) == s.charAt(1);
            default:
                return s.charAt(0) == s.charAt(len - 1) && isPalindromeHelper(s.substring(1, len - 1));
        }
    }


    // returns greatest common divisor of m and n
    public static long gcd(long m, long n) {
        while(m != 0) {
            long m_old = m;
            m = n % m;
            n = m_old;
        }
        return abs(n);
    }


    // recursive version
    // returns greatest common divisor of m ans n
    public static long gcd2(long m, long n) {
        return (m == 0) ? abs(n): gcd2(n % m, m);
    }


    // return least common multiple of m and n
    public static long lcm(long m, long n) {
        return abs(m * n) / gcd(m, n);
    }


    // factors of a number
    public static int[] factors(int n) {
        return IntStream
                .rangeClosed(1, n).filter(x -> n % x == 0).toArray();
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

}



