package pe;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Common {

    // return true if value n is a palindrome (reads the same backward or forward)
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
    // return true if value n is a palindrome (reads the same backward or forward)
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


    // return greatest common divisor of m and n
    public static long gcd(long m, long n) {
        while(m != 0) {
            long m_old = m;
            m = n % m;
            n = m_old;
        }
        return abs(n);
    }


    // recursive version
    // return greatest common divisor of m ans n
    public static long gcd2(long m, long n) {
        return (m == 0) ? abs(n): gcd2(n % m, m);
    }


    // return least common multiple of m and n
    public static long lcm(long m, long n) {
        return abs(m * n) / gcd(m, n);
    }


    // return array of factors of n
    public static int[] factors(int n) {
        return IntStream
                .rangeClosed(1, n).filter(x -> n % x == 0).toArray();
    }


    // return N! as a Long
    public static Long factorial(long n) {
        if (n < 0 || n > 20) {
            return 1L;
        } else {
            return LongStream
                    .rangeClosed(1, n)
                    .reduce(1, (acc, i) -> acc * i);
        }
    }


    // return word representation of number n
    // from http://stackoverflow.com/questions/2729752/converting-numbers-in-to-words-c-sharp
    public static String numberToWords(int number) {
        if (number == 0)
            return "zero";

        if (number < 0)
            return "minus " + numberToWords(Math.abs(number));

        String words = "";

        if ((number / 1000000) > 0) {
            words += numberToWords(number / 1000000) + " million ";
            number %= 1000000;
        }

        if ((number / 1000) > 0) {
            words += numberToWords(number / 1000) + " thousand ";
            number %= 1000;
        }

        if ((number / 100) > 0) {
            words += numberToWords(number / 100) + " hundred ";
            number %= 100;
        }

        if (number > 0) {
            if (!Objects.equals(words, ""))
                words += "and ";

            String[] units =
                    { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven",
                            "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
            String[] tens  =
                    { "zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };

            if (number < 20)
                words += units[number];
            else {
                words += tens[number / 10];
                if ((number % 10) > 0)
                    words += "-" + units[number % 10];
            }
        }
        return words;
    }


    // return array containing `divisor_sum`(i) for i from 0 to n.
    public static int[] divisorSumList(int limit) {
        int[] xs = new int[limit + 1];
        for (int i = 1; i <= limit / 2; i++) {
            int j = 2 * i;
            while (j <= limit) {
                xs[j] += i;
                j += i;
            }
        }
        return xs;
    }


    // return nth lexicographic permutation, recursive
    public static int[] nthLexPerm(int[] lmr, int n) {
        if (lmr.length == 0) {
            return new int[0];
        } else {
            double fact = factorial(lmr.length - 1).doubleValue();
            int i = (int) floor((double) n / fact);
            int[] l = Arrays.copyOfRange(lmr, 0, i);
            int[] r = Arrays.copyOfRange(lmr, i+1, lmr.length);
            int[] lr = IntStream.concat(Arrays.stream(l), Arrays.stream(r)).toArray();
            int[] m = {lmr[i]};
            return IntStream.concat(Arrays.stream(m), Arrays.stream(nthLexPerm(lr, n % (int) fact))).toArray();
        }
    }

    // return if pandigital over 'len' ints from 'start'
    // isPandigital("456123", 1, 6) == true, isPandigital("4560123", 0, 7) == true
    public static boolean isPandigital(String string, int start, int len) {
        char[] all = {'0','1','2','3','4','5','6','7','8','9'};
        if (start + len > string.length() + 1) {
            return false;
        }
        for (int i = start; i < start + len; i++) {
            if (string.indexOf(all[i]) < 0) {
                return false;
            }
        }
        return true;
    }
}



