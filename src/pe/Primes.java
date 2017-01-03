package pe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.sqrt;

class Primes {

    // return true if n is probably prime
    // convenience wrapper
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static boolean isPrime(long n) {
        return BigInteger.valueOf(n).isProbablePrime(10);
    }

    // returns a list of the prime factors of n
    static List<Integer> primeFactors(long n) {
        List<Integer> xs = new ArrayList<>();

        while (n % 2 == 0) {
            xs.add(2);
            n /= 2;
        }

        int factor = 3;
        while (sqrt(n) > 1) {
            while (n % factor == 0) {
                xs.add(factor);
                n = n / factor;
            }
            factor += 2;
        }
        return xs;
    }


    // fill array of length n with true for prime indexes and false for non-prime
    static boolean[] getPrimes(int n) {
        boolean[] primes = new boolean[n];
        Arrays.fill(primes, true);
        primes[0] = primes[1] = false;
        for (int i = 2; i < primes.length; i++) {
            if (primes[i]) {
                for (int j = 2; i * j < primes.length; j++) {
                    primes[i * j] = false;
                }
            }
        }
        return primes;
    }


    // nth prime
    // nthPrime_10 from https://bitbucket.org/dafis/javaprimes/src
     static int nthPrime(int n) {
        if (n  < 2) return 2;
        if (n == 2) return 3;
        if (n == 3) return 5;
        int limit, root, count = 2;
        limit = (int)(n*(Math.log(n) + Math.log(Math.log(n)))) + 3;
        root = (int)Math.sqrt(limit);
        switch(limit%6) {
            case 0:
                limit = 2*(limit/6) - 1;
                break;
            case 5:
                limit = 2*(limit/6) + 1;
                break;
            default:
                limit = 2*(limit/6);
        }
        switch(root%6) {
            case 0:
                root = 2*(root/6) - 1;
                break;
            case 5:
                root = 2*(root/6) + 1;
                break;
            default:
                root = 2*(root/6);
        }
        int dim = (limit+31) >> 5;
        int[] sieve = new int[dim];
        for(int i = 0; i < root; ++i) {
            if ((sieve[i >> 5] & (1 << (i&31))) == 0) {
                ++count;
                int start, s1, s2;
                if ((i & 1) == 1) {
                    start = i*(3*i+8)+4;
                    s1 = 4*i+5;
                    s2 = 2*i+3;
                } else {
                    start = i*(3*i+10)+7;
                    s1 = 2*i+3;
                    s2 = 4*i+7;
                }
                for(int j = start; j < limit; j += s2) {
                    sieve[j >> 5] |= 1 << (j&31);
                    j += s1;
                    if (j >= limit) break;
                    sieve[j >> 5] |= 1 << (j&31);
                }
            }
        }
        int p;
        for(p = root; count < n; ++p) {
            if ((sieve[p >> 5] & (1 << (p&31))) == 0) {
                ++count;
            }
        }
        return 3*p+1+(p&1);
    }
}
