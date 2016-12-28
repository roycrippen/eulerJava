package pe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

import static pe.Common.assertEq;
import static pe.Common.divisorSumList;
import static pe.Common.readTextFile;

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


        Function<Long, Long> solve;
        solve = n -> n + 1 - 1;

        assertEq(solve.apply(0L), 0, "p023 test");

        long res = solve.apply(0L);
        return assertEq(res, 0, "p023");
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


    final static ArrayList<String> solutions = new ArrayList<>(Arrays.asList(
            p021(), p022(), p023()));
}
