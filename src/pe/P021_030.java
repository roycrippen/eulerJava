package pe;

import java.util.ArrayList;
import java.util.Arrays;

import static pe.Common.assertEq;
import static pe.Common.divisorSumList;

class P021_030 {

    // problem 21 ----------------------------------------------
    // Amicable numbers
    private static class P021 extends Euler {

        public String run() {
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
    }


    // problem 2 ----------------------------------------------
    //
    @SuppressWarnings("unused")
    private static class P02 extends Euler {

        static Long solve(Long n) {
            return n;
        }

        public String run() {
            assertEq(solve(0L), 0, "p02 test");

            long res = solve(0L);
            return assertEq(res, 0, "p02");
        }
    }



    final static ArrayList<Euler> solutions = new ArrayList<>(Arrays.asList(
            new P021()));
}
