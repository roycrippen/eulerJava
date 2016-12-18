package pe;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static pe.Common.assertEq;
import static pe.Common.readTextFile;

class P011_020 {

    // problem 11 ----------------------------------------------7
    // Largest product in a grid
    private static String p011() {

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


    // problem 12 ----------------------------------------------7
    // Highly divisible triangular number
    private static String p012() {

        Function<Integer, Integer> factor_cnt;
        factor_cnt = n ->  {
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
        };

        Function<Integer, Integer> solve;
        solve = n -> {
            for (int i = 2; ; i += 4) {
                int temp = factor_cnt.apply(i + 1);
                if (temp * factor_cnt.apply(i / 2) > n)
                    return i * (i + 1) / 2;
                if (temp * factor_cnt.apply((i + 2) / 2) > n)
                    return (i + 1) * (i + 2) / 2;
            }
        };

        assertEq(solve.apply(6), 120, "p012 test");

        long res = solve.apply(500);
        return assertEq(res, 76576500, "p012");
    }



    // problem 13 ----------------------------------------------7
    // Large sum
    private static String p013() {

        List<String> xs = readTextFile("p013.txt");

        String bigSum = xs
                .stream()
                .map(BigInteger::new)
                .reduce(BigInteger.ZERO, BigInteger::add)
                .toString();

        long res = Long.parseLong(bigSum.substring(0, 10));
        return assertEq(res, 5537376230L, "p013");
    }


    // problem 0 ----------------------------------------------7
    //
    @SuppressWarnings("unused")
    private static String p01() {

        Function<Long, Long> solve;
        solve = n -> n;

        assertEq(solve.apply(0L), 0, "p01 test");

        long res = solve.apply(0L);
        return assertEq(res, 0, "p01");
    }


//    final static Stream<String> solutions = Stream.of(p013());
    final static Stream<String> solutions = Stream.of(
            p011(), p012(), p013());



}
