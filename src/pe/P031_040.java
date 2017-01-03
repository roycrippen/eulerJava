package pe;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static pe.Common.isPandigital;
import static pe.FunctionalInterfaceExtensions.IntIntFunction;
import static pe.Primes.getPrimes;
import static pe.Primes.isPrime;
import static pe.Utils.assertEq;

class P031_040 {

    // problem 31 ----------------------------------------------
    // Coin sums
    private static class p031 implements Supplier<String> {

        @Override
        public String get() {

            int[] ws = new int[201];
            ws[0] = 1;
            int[] ps = {1, 2, 5, 10, 20, 50, 100, 200};
            for (int v: ps) {
                for (int j = v; j < 201; j++) {
                    ws[j] += ws[j - v];
                }
            }

            long res = ws[200];
            return assertEq(res, 73682, "p031");
        }
    }


    // problem 32 ----------------------------------------------
    // Pandigital products
    private static class p032 implements Supplier<String> {

        @Override
        public String get() {
            HashSet<Integer> m = new HashSet<>();

            // 1-digit * 4-digits in map to avoid duplicates
            for (int i = 2; i < 10; i++) {
                int k = 10000/i;
                for (int j = 1234; j < k; j++) {
                    String str =  "" + i + j + i * j;
                    if (str.length() != 9) {
                        break;
                    }
                    if (str.indexOf('0') != -1) {
                        continue;
                    }
                    if (isPandigital(str, 1, 9)) {
                        m.add(i * j);
                    }
                }
            }

            // 2-digits * 3-digits in map to avoid duplicates
            for (int i = 11; i < 100; i++) {
                int k = 10000/i;
                for (int j = 100; j < k; j++) {
                    String str =  "" + i + j + i * j;
                    if (str.length() != 9) {
                        break;
                    }
                    if (str.indexOf('0') != -1) {
                        continue;
                    }
                    if (isPandigital(str, 1, 9)) {
                        m.add(i * j);
                    }
                }
            }

            long res = m.stream().reduce(0, (acc, x) -> acc + x);
            return assertEq(res, 45228, "p032");
        }
    }


    // problem 33 ----------------------------------------------
    // Digit cancelling fractions
    private static class p033 implements Supplier<String> {

        @Override
        public String get() {
            final double EPSILON = 0.0000001;

            IntIntFunction<Boolean> isDigitCancelingFraction;
            isDigitCancelingFraction = (n, d) -> {
                double[] ns = String.valueOf(n).chars().mapToDouble(x -> x - 48.0).toArray();
                double[] ds = String.valueOf(d).chars().mapToDouble(x -> x - 48.0).toArray();

                if (ns[0] == ds[1]) {
                    return Math.abs(ns[1] / ds[0] - (double) n / (double) d) < EPSILON;
                } else if (ns[1] == ds[0]) {
                    return Math.abs(ns[0] / ds[1] - (double) n / (double) d) < EPSILON;
                }
                return false;
            };


            int prodNum = 1;
            int prodDen = 1;
            for (int i = 10; i < 100 ; i++) {
                for (int j = i + 1; j < 100; j++) {
                    if (isDigitCancelingFraction.apply(i, j)) {
                        prodNum *= i;
                        prodDen *= j;
                    }
                }
            }

            long res = prodDen / Common.gcd(prodNum, prodDen);
            return assertEq(res, 100, "p033");
        }
    }

    // problem 34 ----------------------------------------------
    // Digit factorials
    private static class p034 implements Supplier<String> {
        final int[] fact = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};

        final IntPredicate isDigitFact = n -> {
            int val = 0;
            int t = n;
            while (t != 0) {
                val += fact[t % 10];
                t /= 10;
            }
            return val == n;
        };

        @Override
        public String get() {

        // max value 5*9! = 1814400 < 10^7
        int sum = IntStream
                .rangeClosed(10, 181440)
                .filter(isDigitFact)
                .sum();

        return assertEq(sum, 40730, "p034");
        }
    }


    // problem 35 ----------------------------------------------
    // Circular primes
    private static class p035 implements Supplier<String> {

        final IntUnaryOperator rotate = n -> {
            String s = String.valueOf(n);
            return Integer.valueOf(s.substring(1) + s.charAt(0));
        };

        final IntPredicate containEvens =
                n -> String
                        .valueOf(n)
                        .chars()
                        .anyMatch(c -> c == '0' || c == '2' || c == '4' || c == '6' || c == '8');

        final BiFunction<Integer, boolean[], Boolean> isCircularPrime = (n, primes) -> {
            if (primes[n] && !containEvens.test(n)) {
                int next = rotate.applyAsInt(n);
                while (next != n) {
                    if (!primes[next]) {
                        return false;
                    }
                    next = rotate.applyAsInt(next);
                }
                return true;
            }
            return false;
        };

        final IntUnaryOperator solve = n -> {
            boolean[] primes = getPrimes(n);
            int sum = 1;
            for (int i = 3; i <= n; i += 2) {
                if (isCircularPrime.apply(i, primes)) {
                    sum++;
                }
            }
            return sum;
        };

        @Override
        public String get() {
            assertEq(solve.applyAsInt(100), 13, "p035 test");

            long res = solve.applyAsInt(1_000_000);
            return assertEq(res, 55, "p035");
        }
    }

    // problem 36 ----------------------------------------------
    // Double-base palindromes
    private static class p036 implements Supplier<String> {

        @Override
        public String get() {
            long res = LongStream
                    .range(1, 1_000_000)
                    .filter(Common::isPalindromeBinary)
                    .filter(Common::isPalindrome)
                    .sum();

            return assertEq(res, 872187, "p036");
        }
    }

    // problem 37 ----------------------------------------------
    // Truncatable primes
    // from 'manishi' on https://projecteuler.net/
    private static class p037 implements Supplier<String> {

        final IntPredicate isLeftTruncatable = n -> {
            int mod = 10;
            while (mod < n) {
                if (!isPrime(n % mod)) {
                    return false;
                }
                mod *= 10;
            }
            return true;
        };

        @Override
        public String get() {
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(2);
            queue.add(3);
            queue.add(5);
            queue.add(7);

            List<Integer> results = new ArrayList<>();
            int[] primes = {1, 3, 7, 9};
            while (!queue.isEmpty()) {
                int current = queue.remove();
                for (int i = 0; i < 4; i++) {
                    int candidate = current * 10 + primes[i];
                    if (!isPrime(candidate)) {
                        continue;
                    }
                    queue.add(candidate);
                    if(isLeftTruncatable.test(candidate)) {
                        results.add(candidate);
                    }
                }
             }

            long res = results.stream().reduce(0, (acc, x) -> acc + x);
            return assertEq(res, 748317, "p037");
        }
    } // 23+37+53+73+313+317+373+797+3137+3797+739397=748317


    // problem 38 ----------------------------------------------
    //
    private static class p038 implements Supplier<String> {

        @Override
        public String get() {

            long res = 0;
            return assertEq(res, 0, "p038");
        }
    }


    // problem 39 ----------------------------------------------
    //
    private static class p039 implements Supplier<String> {

        @Override
        public String get() {

            long res = 0;
            return assertEq(res, 0, "p039");
        }
    }


    // problem 40 ----------------------------------------------
    //
    private static class p040 implements Supplier<String> {

        @Override
        public String get() {

            long res = 0;
            return assertEq(res, 0, "p040");
        }
    }


    // hashmap of problems solves in this class
    static TreeMap<Integer, Supplier<String>> loadSolutionMap() {
        TreeMap<Integer, Supplier<String>> m = new TreeMap<>();
        m.put(31, new p031());
        m.put(32, new p032());
        m.put(33, new p033());
        m.put(34, new p034());
        m.put(35, new p035());
        m.put(36, new p036());
        m.put(37, new p037());
        m.put(38, new p038());
        m.put(39, new p039());
        m.put(40, new p040());
        return m;
    }
}
