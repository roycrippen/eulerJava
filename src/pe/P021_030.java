package pe;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.IntStream;

import static pe.Common.divisorSumList;
import static pe.Common.nthLexPerm;
import static pe.Utils.assertEq;
import static pe.Utils.readTextFile;

class P021_030 {

    // problem 21 ----------------------------------------------
    // Amicable numbers
    private static String p021() {

            final int N = 10_000;
            int[] amic = divisorSumList(N);
            int sum = 0;

            for (int i = 0; i <= N; i++) {
                if (amic[i] < N && i != amic[i] && i == amic[amic[i]]) {
                    sum += amic[i] + amic[amic[i]];
                }
            }

            return assertEq(sum/2, 31626, "p021");
    }


    // problem 22 ----------------------------------------------
    // Names scores
     private static String p022() {

         Function<String, Integer> getScore;
         getScore = string -> string
                     .chars()
                     .filter(i -> i != '\"')
                     .map(i -> i - 64)
                     .sum();

         String[] names = Arrays.stream(readTextFile("p022.txt").get(0).split(","))
                 .sorted()
                 .toArray(String[]::new);

         long res = IntStream.rangeClosed(1, names.length)
                 .reduce(0, (acc, i) -> acc + i * getScore.apply(names[i - 1]));

         return assertEq(res, 871198282, "p022");
    }


    // problem 23 ----------------------------------------------
    // Non-abundant sums
    private static String p023() {

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


    // problem 24 ----------------------------------------------
    // Lexicographic permutations
    private static String p024() {

       int[] xs = {0,1,2,3,4,5,6,7,8,9};

       String resStr = Arrays.stream(nthLexPerm(xs, 999999))
               .mapToObj(String::valueOf)
               .reduce("", (s,acc) -> s + acc);

        long res = Long.parseLong(resStr);
        return assertEq(res, 2783915460L, "p024");
    }


    // problem 25 ----------------------------------------------
    // 1000-digit Fibonacci number
    private static String p025() {

        Function<Integer, Integer> solve;
        solve = n -> {
            BigInteger limit = BigInteger.ONE;
            for (int i = 0; i < n-1; i++) {
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

        assertEq(solve.apply(3), 12, "p025 test");

        long res = solve.apply(1000);
        return assertEq(res, 4782, "p025");
    }


    // problem 26 ----------------------------------------------
    // Reciprocal cycles
    @SuppressWarnings("unused")
    private static String p026() {

        Function<Integer, Integer> repeatCnt;
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
            int current = repeatCnt.apply(i);
            if (current > max) {
                max = current;
                idx = i;
            }
            i += 2;
        }

        return assertEq(idx, 983, "p026");
    }



    // problem 2 ----------------------------------------------
    //
    @SuppressWarnings("unused")
    private static String p02() {


        Function<Long, Long> solve;
        solve = n -> n + 1 - 1;

        assertEq(solve.apply(0L), 0, "p02 test");

        long res = solve.apply(0L);
        return assertEq(res, 0, "p02");
    }


    // hashmap of problems solves in this class
    static HashMap<Integer, String> loadSolutionMap() {
        HashMap<Integer, String> m = new HashMap<>();
        m.put(21, p021());
        m.put(22, p022());
        m.put(23, p023());
        m.put(24, p024());
        m.put(25, p025());
        m.put(26, p026());
//        m.put(27, p027());
//        m.put(28, p028());
//        m.put(29, p029());
//        m.put(30, p030());
        return m;
    }
}
