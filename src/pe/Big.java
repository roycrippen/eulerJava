package pe;

import java.math.BigInteger;

class Big {

    // return N! as a BigInteger
    static BigInteger factorial(long n) {
        BigInteger fact = BigInteger.ONE;
        if (n > 1) {
            for (long i = 2; i <= n; i++) {
                fact = fact.multiply(BigInteger.valueOf(i));
            }
        }
        return fact;
    }
}



