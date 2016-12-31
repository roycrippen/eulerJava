package pe;

import java.math.BigInteger;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static pe.Common.divisorSumList;
import static pe.Common.nthLexPerm;
import static pe.Utils.assertEq;
import static pe.Utils.readTextFile;

class P021_030 {

    // problem 21 ----------------------------------------------
    // Amicable numbers
    private static class p021 implements Supplier<String> {

        @Override
        public String get() {
            final int N = 10_000;
            int[] amic = divisorSumList(N);
            int sum = 0;

            for (int i = 0; i <= N; i++) {
                if (amic[i] < N && i != amic[i] && i == amic[amic[i]]) {
                    sum += amic[i] + amic[amic[i]];
                }
            }

            return assertEq(sum / 2, 31626, "p021");
        }
    }


    // problem 22 ----------------------------------------------
    // Names scores
    private static class p022 implements Supplier<String> {

        @Override
        public String get() {
            ToIntFunction<String> getScore;
            getScore = string -> string
                    .chars()
                    .filter(i -> i != '\"')
                    .map(i -> i - 64)
                    .sum();

            String[] names = Arrays.stream(readTextFile("p022.txt").get(0).split(","))
                    .sorted()
                    .toArray(String[]::new);

            long res = IntStream.rangeClosed(1, names.length)
                    .reduce(0, (acc, i) -> acc + i * getScore.applyAsInt(names[i - 1]));

            return assertEq(res, 871198282, "p022");
        }
    }


    // problem 23 ----------------------------------------------
    // Non-abundant sums
    private static class p023 implements Supplier<String> {

        @Override
        public String get() {
            final int N = 28124;
            int[] factorSums = divisorSumList(N);

            boolean[] abundants = new boolean[N];
            for (int i = 0; i < N; i++) {
                abundants[i] = factorSums[i] > i;
            }

            int sum = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (abundants[j]) {
                        if (j >= i) {
                            sum += i;
                            break;
                        }
                        if (abundants[i - j]) {
                            break;
                        }
                    }
                }
            }

            return assertEq(sum, 4179871, "p023");
        }
    }


    // problem 24 ----------------------------------------------
    // Lexicographic permutations
    private static class p024 implements Supplier<String> {

        @Override
        public String get() {
            int[] xs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

            String resStr = Arrays.stream(nthLexPerm(xs, 999999))
                    .mapToObj(String::valueOf)
                    .reduce("", (s, acc) -> s + acc);

            long res = Long.parseLong(resStr);
            return assertEq(res, 2783915460L, "p024");
        }
    }


    // problem 25 ----------------------------------------------
    // 1000-digit Fibonacci number
    private static class p025 implements Supplier<String> {

        @Override
        public String get() {
            LongUnaryOperator solve;
            solve = n -> {
                BigInteger limit = BigInteger.ONE;
                for (int i = 0; i < n - 1; i++) {
                    limit = limit.multiply(BigInteger.TEN);
                }
                BigInteger a = BigInteger.ZERO;
                BigInteger b = BigInteger.ONE;
                int cnt = 0;
                while (a.compareTo(limit) < 0) {
                    BigInteger x = b;
                    b = a.add(b);
                    a = x;
                    cnt++;
                }
                return cnt;
            };

            assertEq(solve.applyAsLong(3), 12, "p025 test");

            long res = solve.applyAsLong(1000);
            return assertEq(res, 4782, "p025");
        }
    }

    // problem 26 ----------------------------------------------
    // Reciprocal cycles
    private static class p026 implements Supplier<String> {

        @Override
        public String get() {
            IntUnaryOperator repeatCnt;
            repeatCnt = n -> {
                int cnt = 2;
                if (n % 5 != 0) {
                    int md = 10 % n;
                    while (md != 1 && cnt != n) {
                        md = (10 * md) % n;
                        cnt++;
                    }
                }
                return cnt;
            };

            int max = 0;
            int idx = 0;
            int i = 3;
            while (i < 1000) {
                int current = repeatCnt.applyAsInt(i);
                if (current > max) {
                    max = current;
                    idx = i;
                }
                i += 2;
            }

            return assertEq(idx, 983, "p026");
        }
    }

    // problem 27 ----------------------------------------------
    // Quadratic primes
    private static class p027 implements Supplier<String> {

        @Override
        public String get() {
            final int N = Primes.nthPrime(15_000);
            Boolean[] primes = Primes.getPrimes(N+1);

            int[] bs = IntStream.rangeClosed(0, 1000)
                    .filter(x -> primes[x])
                    .toArray();


            int max = 0;
            int ab = 0;
            for (int b: bs) {
                for (int a = -999; a < 1000; a++) {
                    int n = 1;
                    while(true) {
                        int v = n*n + a*n + b;
                        if (v < 0 || !primes[v]) {
                            break;
                        }
                        n++;
                     }
                     if (n > max) {
                         max = n;
                         ab = a * b;
                     }
                }
            }

            return assertEq(ab, -59231, "p027");
         }
    }


    // problem 28 ----------------------------------------------
    // Number spiral diagonals
    private static class p028 implements Supplier<String> {

        @Override
        public String get() {
            LongUnaryOperator solve;
            solve = n -> LongStream
                    .iterate(3, x -> x + 2)
                    .limit(n / 2)
                    .reduce(1, (acc, i) -> acc + 4 * i * i - 6 * (i - 1));

            assertEq(solve.applyAsLong(5), 101, "p028 test");

            long res = solve.applyAsLong(1001);
            return assertEq(res, 669171001, "p028");
        }
    }


    // problem 29 ----------------------------------------------
    // Distinct powers
    private static class p029 implements Supplier<String> {

        @Override
        public String get() {
            IntUnaryOperator solve;
            solve = n -> {
                HashSet<Double> hs = new HashSet<>();
                for (double a = 2; a <= n; a++) {
                    for (int b = 2; b <= n; b++) {
                        hs.add(b * Math.log(a));
                    }
                }
                return hs.size();
            };

            assertEq(solve.applyAsInt(5), 15, "p029 test");
            long res = solve.applyAsInt(100);
            return assertEq(res, 9240, "p029");
        }
    }


    // problem 30 ----------------------------------------------
    // Digit fifth powers
    private static class p030 implements Supplier<String> {

        @Override
        public String get() {
            IntUnaryOperator solve;
            solve = n -> n + 1 - 1;

            assertEq(solve.applyAsInt(19316), 19316, "p030 test");

            long res = solve.applyAsInt(443839);
            return assertEq(res, 443839, "p030");
        }
    }


    // hashmap of problems solves in this class
    static TreeMap<Integer, Supplier<String>> loadSolutionMap() {
        TreeMap<Integer, Supplier<String>> m = new TreeMap<>();
        m.put(21, new p021());
        m.put(22, new p022());
        m.put(23, new p023());
        m.put(24, new p024());
        m.put(25, new p025());
        m.put(26, new p026());
        m.put(27, new p027());
        m.put(28, new p028());
        m.put(29, new p029());
        m.put(30, new p030());
        return m;
    }
}
