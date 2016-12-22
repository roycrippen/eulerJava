package pe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pe.Common.assertEq;
import static pe.Common.factorial;
import static pe.Common.numberToWords;
import static pe.Common.readTextFile;

class P011_020 {

    // problem 11 ----------------------------------------------
    // Largest product in a grid
    private static class P011 extends Euler {

        public String run() {
            int[][] data = readTextFile("p011.txt")
                    .stream()
                    .map(s -> Stream
                            .of(s.split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .toArray(int[][]::new);

            int max = 0;
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 17; j++) {
                    int t;
                    // right
                    t = data[i][j] * data[i][j + 1] * data[i][j + 2] * data[i][j + 3];
                    if (t > max)
                        max = t;

                    // down
                    t = data[i][j] * data[i + 1][j] * data[i + 2][j] * data[i + 3][j];
                    if (t > max)
                        max = t;

                    // diagonal right
                    t = data[i][j] * data[i + 1][j + 1] * data[i + 2][j + 2] * data[i + 3][j + 3];
                    if (t > max)
                        max = t;

                    // diagonal left
                    t = data[i][j + 3] * data[i + 1][j + 2] * data[i + 2][j + 1] * data[i + 3][j];
                    if (t > max)
                        max = t;
                }
            }

            return assertEq(max, 70600674, "p011");
        }
    }

    // problem 12 ----------------------------------------------
    // Highly divisible triangular number
    private static class P012 extends Euler {

        static Integer solve(Integer n) {
            for (int i = 2; ; i += 4) {
                int temp = factor_cnt(i + 1);
                if (temp * factor_cnt(i / 2) > n)
                    return i * (i + 1) / 2;
                if (temp * factor_cnt((i + 2) / 2) > n)
                    return (i + 1) * (i + 2) / 2;
            }
        }

        static Integer factor_cnt(Integer n) {
            if (n < 2)
                return 1;
            int factors = 1;
            int max = (int) Math.sqrt((double) n);
            for (int i = 2; ; i++) {
                if (i > max)
                    break;
                int p = 0;
                while (n % i == 0) {
                    p += 1;
                    n /= i;
                }
                factors *= p + 1;
            }
            return factors;
        }

        public String run() {
            assertEq(solve(6), 120, "p012 test");

            long res = solve(500);
            return assertEq(res, 76576500, "p012");
        }
    }


    // problem 13 ----------------------------------------------
    // Large sum
    private static class P013 extends Euler {

        public String run() {
            List<String> xs = readTextFile("p013.txt");
            String bigSum = xs
                    .stream()
                    .map(BigInteger::new)
                    .reduce(BigInteger.ZERO, BigInteger::add)
                    .toString();

            long res = Long.parseLong(bigSum.substring(0, 10));
            return assertEq(res, 5537376230L, "p013");
        }
    }

        // problem 14 ----------------------------------------------
        // Longest Collatz sequence
        private static class P014 extends Euler {

            public String run() {
                final int LIMIT = 1_000_000;
                int[] cache = new int[LIMIT];

                int max = 0;
                int answer = 1;
                for (int i = 1; i < LIMIT; i++) {
                    long n = i;
                    int cnt = 0;
                    while (n != 1) {
                        cnt++;
                        if (n % 2 == 0) n >>= 1;
                        else n = (3 * n) + 1;
                        if (n < i) {
                            cnt += cache[(int) n];
                            break;
                        }
                    }
                    cache[i] = cnt;
                    if (cnt > max) {
                        max = cnt;
                        answer = i;
                    }
                }

                return assertEq(answer, 837799, "p014");
        }
    }

    // problem 15 ----------------------------------------------
    // Lattice paths
    private static class P015 extends Euler {

        public String run() {
            // C(n,r) = n! / ( r! (n - r)! )
            // 40! / (20! (40 - 20)!) = 40!/(40!*20!)
            BigInteger factN = factorial(40);
            BigInteger factR = factorial(20);
            Long res = factN.divide(factR.multiply(factR)).longValue();

            return assertEq(res, 137846528820L,"p015");
        }
    }


    // problem 16 ----------------------------------------------
    // Power digit sum
    private static class P016 extends Euler {

        static int solve(int n) {
            BigInteger two = new BigInteger("2");
            return two
                    .pow(n)
                    .toString()
                    .chars()
                    .reduce(0, (acc, c) -> acc + (c - 48));
       }

        public String run() {
            assertEq(solve(15), 26, "p016 test");

            long res = solve(1000);
            return assertEq(res, 1366, "p016");
        }
    } //


    // problem 17 ----------------------------------------------
    // Number letter counts
    private static class P017 extends Euler {

        static int letterCnt (String s) {
            return s.chars().reduce(0, (acc, c) -> {
                if (c != ' ' && c != '-')                    return acc +1;
                else
                    return acc;
            });
        }

        static int solve(int n) {
            return IntStream.rangeClosed(1, n).reduce(0, (acc, x) -> acc + letterCnt(numberToWords(x)));
        }

        public String run() {
            assertEq(solve(5), 19, "p017 test");

            long res = solve(1000);
            return assertEq(res, 21124, "p017");
        }
    }


    // problem 18 ----------------------------------------------
    // Maximum path sum I
    private static class P018 extends Euler {
        public String run() {
            int[][] data = readTextFile("p018.txt")
                    .stream()
                    .map(s -> Stream
                            .of(s.split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .toArray(int[][]::new);

            for (int i = data.length - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    data[i - 1][j] += Math.max(data[i][j], data[i][j + 1]);
                }
            }

            long res = (long) data[0][0];
            return assertEq(res, 1074, "p018");
        }
    }



    // problem 19 ----------------------------------------------
    // Counting Sundays
    private static class P019 extends Euler {

        public String run() {
            int[] daysMonth = {0,31,28,31,30,31,30,31,31,30,31,30,31};
            int sundays = 0;
            int day = 1;

            for (int year = 1900; year < 2001; year++) {
                if (year % 4 == 0 && year % 100 != 0)
                    daysMonth[2] = 29;
                else
                    daysMonth[2] = 28;
                for (int i = 1; i < 13; i++) {
                    for (int j = 1; j <= daysMonth[i]; j++) {
                        if (day == 7 && j == 1 && year != 1900)
                                sundays++;
                        if (day == 7)
                            day = 1;
                        else
                            day++;
                    }
                }
            }

            return assertEq(sundays, 171, "p019");
        }
    }


    // problem 20 ----------------------------------------------
    // Factorial digit sum
    private static class P020 extends Euler {

        static Long solve(Long n) {
            int sum = factorial(n)
                    .toString()
                    .chars()
                    .reduce(0, (acc, x) -> acc + x - 48);
            return (long) sum;
        }

        public String run() {
            assertEq(solve(10L), 27, "p020 test");

            long res = solve(100L);
            return assertEq(res, 648, "p020");
        }
    }

//    // problem 20 ----------------------------------------------
//    //
//    @SuppressWarnings("unused")
//    private static class P020 extends Euler {
//
//        static Long solve(Long n) {
//            return n;
//        }
//
//        public String run() {
//            assertEq(solve(0L), 0, "p020 test");
//
//            long res = solve(0L);
//            return assertEq(res, 0, "p020");
//        }
//    }



    final static ArrayList<Euler> solutions = new ArrayList<>(Arrays.asList(
            new P011(), new P012(), new P013(), new P014(), new P015(),
            new P016(), new P017(), new P018(), new P019(), new P020()));
}
