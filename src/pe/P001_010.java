package pe;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.IntToLongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static pe.Utils.assertEq;
import static pe.Utils.readTextFile;
import static pe.Common.isPalindrome;
import static pe.Primes.nthPrime;

class P001_010 {

    // problem 1 ----------------------------------------------
    // Multiples of 3 and 5
    private static class p001 implements Supplier<String> {

        @Override
        public String get() {

            int res = IntStream
                    .range(3, 1000)
                    .filter(i -> i % 3 == 0 || i % 5 == 0)
                    .sum();

            return assertEq(res, 233168, "p001");
        }
    }


    // problem 2 ----------------------------------------------
    // Even Fibonacci numbers
    private static class p002 implements Supplier<String> {

        @Override
        public String get() {

            IntToLongFunction solve;
            solve = n -> {
                // Stream code for fibonacci found at
                // http://stackoverflow.com/questions/26288818/infinite-fibonacci-sequence-with-memoized-in-java-8
                LongStream fibStream = Stream.iterate(
                        new long[]{1, 1},
                        f -> new long[]{f[1], f[0] + f[1]}
                ).mapToLong(f -> f[0]);

                Iterator fibIterator = fibStream.filter(fib -> fib % 2 == 0).iterator();

                long tot = 0;
                while (fibIterator.hasNext()) {
                    long fib = ((long) fibIterator.next());

                    if (fib >= n)
                        break;
                    tot += fib;
                }
                return tot;
            };

            assertEq(solve.applyAsLong(50), 44, "p002 test");

            long res = solve.applyAsLong(4_000_000);
            return assertEq(res, 4613732, "p002");
        }
    }


    // problem 3 ----------------------------------------------
    // Largest prime factor
    private static class p003 implements Supplier<String> {

        @Override
        public String get() {

            LongUnaryOperator solve;
            solve = n -> Primes.primeFactors(n).stream().max(Integer::compare).orElse(0);

            assertEq(solve.applyAsLong(13195), 29, "p003 test");

            long res = solve.applyAsLong(600851475143L);
            return assertEq(res, 6857, "p003");
        }
    }


    // problem 4 ----------------------------------------------
    // Largest palindrome product
    private static class p004 implements Supplier<String> {

        @Override
        public String get() {

            IntToLongFunction solve;
            solve = n -> {
                long lower;
                long upper;

                switch (n) {
                    case 2:
                        lower = 10;
                        upper = 100;
                        break;
                    case 3:
                        lower = 100;
                        upper = 1000;
                        break;
                    case 4:
                        lower = 1000;
                        upper = 10000;
                        break;
                    default:
                        return 0L;
                }

                LongStream candidates = LongStream
                        .range(lower, upper)
                        .flatMap(i -> LongStream
                                .range(i, upper)
                                .map(j -> i * j));

                return candidates.filter(x -> x % 11 == 0 && isPalindrome(x)).max().orElse(0L);
            };


            assertEq(solve.applyAsLong(2), 9009, "p004 test");

            long res = solve.applyAsLong(3);
            return assertEq(res, 906609, "p004");
        }
    }


    // problem 5 ----------------------------------------------
    // Smallest multiple
    private static class p005 implements Supplier<String> {

        @Override
        public String get() {

            LongUnaryOperator solve;
            solve = n -> LongStream.rangeClosed(1, n).reduce(1, Common::lcm);

            assertEq(solve.applyAsLong(10), 2520, "p005 test");

            long res = solve.applyAsLong(20);
            return assertEq(res, 232792560, "p005");
        }
    }


    // problem 6 ----------------------------------------------
    // Sum square difference
    private static class p006 implements Supplier<String> {

        @Override
        public String get() {

            LongUnaryOperator solve;
            solve = n -> {
                long sum = LongStream.rangeClosed(1, n).sum();
                long sumSquared = LongStream.rangeClosed(1, n).reduce(0, (acc, x) -> acc + x * x);
                return sum * sum - sumSquared;
            };

            assertEq(solve.applyAsLong(10), 2640, "p006 test");

            long res = solve.applyAsLong(100);
            return assertEq(res, 25164150, "p006");
        }
    }


    // problem 7 ----------------------------------------------
    // 10001st prime
    private static class p007 implements Supplier<String> {

        @Override
        public String get() {

            assertEq(nthPrime(6), 13, "p007 test");

            long res = nthPrime(10_001);
            return assertEq(res, 104743, "p007");
        }
    }


    // problem 8 ----------------------------------------------
    // Largest product in a series
    private static class p008 implements Supplier<String> {

        @Override
        public String get() {

            List<Long> data = readTextFile("p008.txt")
                    .stream()
                    .reduce("", (acc, x) -> acc + x)
                    .chars()
                    .mapToObj(i -> i - 48L)
                    .collect(Collectors.toList());

            long res = LongStream
                    .range(0, data.size() - 12)
                    .map(i -> data
                            .stream()
                            .limit(i + 13L)
                            .skip(i)
                            .reduce(1L, (acc, x) -> acc * x))
                    .max().orElse(0);

            return assertEq(res, 23514624000L, "p008");
        }
    }


    // problem 9 ----------------------------------------------
    // Special Pythagorean triplet
    private static class p009 implements Supplier<String> {

        @Override
        public String get() {

            int res = IntStream
                    .rangeClosed(2, 500)
                    .flatMap(a ->
                            IntStream.rangeClosed(a, 500)
                                    .filter(b -> a + b < 1000 && a * a + b * b == (1000 - a - b) * (1000 - a - b))
                                    .map(b -> a * b * (1000 - a - b)))
                    .limit(1)
                    .max()
                    .orElse(0);

            return assertEq(res, 31875000, "p009");
        }
    }


    // problem 10 ----------------------------------------------
    // Summation of primes
    private static class p010 implements Supplier<String> {

        @Override
        public String get() {

            IntToLongFunction solve;
            solve = n -> {
                switch (n) {
                    case 0:
                    case 1:
                        return 0L;
                    case 2:
                        return 2L;
                    case 3:
                    case 4:
                        return 5L;
                    case 5:
                    case 6:
                    case 7:
                        return 10L;
                    default:
                        boolean[] primes = Primes.getPrimes(n);
                        long sum = 10;
                        for (int i = 7; i <= n; i += 2) {
                            if (primes[i]) {
                                sum += i;
                            }
                        }
                        return sum;
                }
            };

            assertEq(solve.applyAsLong(10), 17, "p010 test");

            long res = solve.applyAsLong(2_000_000);
            return assertEq(res, 142913828922L, "p010");
        }
    }


    // hashmap of problems solves in this class
    static TreeMap<Integer, Supplier<String>> loadSolutionMap() {
        TreeMap<Integer, Supplier<String>> m = new TreeMap<>();
        m.put(1, new p001());
        m.put(2, new p002());
        m.put(3, new p003());
        m.put(4, new p004());
        m.put(5, new p005());
        m.put(6, new p006());
        m.put(7, new p007());
        m.put(8, new p008());
        m.put(9, new p009());
        m.put(10, new p010());
        return m;
    }
}