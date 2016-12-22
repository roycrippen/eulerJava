package pe;

import java.util.ArrayList;
import java.util.Arrays;

import static pe.Common.assertEq;

class P021_030 {

    // problem 21 ----------------------------------------------
    // Amicable numbers
    private static class P021 extends Euler {

        public String run() {

             return assertEq(0, 0, "p021");
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
