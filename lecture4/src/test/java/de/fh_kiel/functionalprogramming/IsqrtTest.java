package de.fh_kiel.functionalprogramming;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Created by tom on 15.10.2016.
 */
public class IsqrtTest {

    @Test
    public void basicTest() {
        assertThat("base is wrong", Isqrt.isqrt(0), is(0));
        assertThat("1 is wrong", Isqrt.isqrt(1), is(1));
        assertThat("2 is wrong", Isqrt.isqrt(2), is(1));

        assertThat("20 is wrong", Isqrt.isqrt(20), is(4));
        assertThat("25 is wrong", Isqrt.isqrt(25), is(5));
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void exceptionTest() {
        assertThat("exception should be thrown", Isqrt.isqrt(-1), is(0));
    }

}