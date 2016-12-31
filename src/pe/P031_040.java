package pe;

import java.util.*;
import java.util.function.*;

import static pe.Common.isPandigital;
import static pe.Utils.assertEq;

class P031_040 {

    // problem 31 ----------------------------------------------
    //
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
    //
    private static class p032 implements Supplier<String> {

        @Override
        public String get() {

            HashSet<Integer> m = new HashSet<>();
            // 1-digit * 4-digits in map to avoid duplicate
            for (int i = 2; i < 10; i++) {
                for (int j = 1000; j < 10_000; j++) {
                    String str =  String.format("%s%s%s", i, j, i * j);
                    if (str.length() != 9) {
                        break; // don't continue to iterate if result is more than 9 digits
                    }
                    if (isPandigital(str, 1, 9)) {
                        m.add(i * j);
                    }
                }
            }

            // 2-digits * 3-digits in map to avoid dups
            for (int i = 11; i < 100; i++) {
                for (int j = 100; j < 1_000; j++) {
                    String str =  String.format("%s%s%s", i, j, i * j);
                    if (str.length() != 9) {
                        break; // don't continue to iterate if result is more than 9 digits
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
    //
    private static class p033 implements Supplier<String> {

        @Override
        public String get() {

            long res = 0;
            return assertEq(res, 0, "p033");
        }
    }


    // problem 34 ----------------------------------------------
    //
    private static class p034 implements Supplier<String> {

        @Override
        public String get() {

            long res = 0;
            return assertEq(res, 0, "p034");
        }
    }


    // problem 35 ----------------------------------------------
    //
    private static class p035 implements Supplier<String> {

        @Override
        public String get() {

            long res = 0;
            return assertEq(res, 0, "p035");
        }
    }

    // problem 36 ----------------------------------------------
    //
    private static class p036 implements Supplier<String> {

        @Override
        public String get() {

            long res = 0;
            return assertEq(res, 0, "p036");
        }
    }

    // problem 37 ----------------------------------------------
    //
    private static class p037 implements Supplier<String> {

        @Override
        public String get() {

            long res = 0;
            return assertEq(res, 0, "p037");
        }
    }


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
