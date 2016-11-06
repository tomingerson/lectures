package de.fh_kiel.functionalprogramming;

/**
 * @author Created by tom on 14.10.2016.
 */
public class Isqrt {

    /**
     * Calculates the integer part of the square root of the given integer.
     * @param n number to calculate the integer part
     * @return integer part of the square root of the given integer
     */
    static int isqrt(int n) {
        if ( n < 0 )
            throw new IllegalArgumentException("n must be a natural number");
        else if (n == 0) {
            return 0;
        } else {
            final int x = isqrt(n - 1);
            if ((x+1) * (x+1) <= n) {
                return x + 1;
            } else {
                return x;
            }
        }
    }
}
