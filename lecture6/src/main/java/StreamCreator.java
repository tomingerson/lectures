import java.security.SecureRandom;
import java.util.SplittableRandom;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Created by tom on 01.11.2016.
 */
public class StreamCreator {

    /**
     * Generate a stream using the {@link Stream#generate(Supplier)} method.
     * As {@link Supplier} we use the pseudorandom generator {@link Math#random()}.
     */
    public static Stream<Double> streamOfRandomNumbers() {
        Stream<Double> rand = Stream.generate(Math::random);
        return rand;
    }

    /**
     * Generate a stream using the {@link Stream#generate(Supplier)} method.
     * As {@link Supplier} we use the cryptographically strong generator
     * {@link SecureRandom#nextDouble()}.
     */
    public static Stream<Double> streamOfSecureRandomNumbers() {
        Stream<Double> sRand = Stream.generate(new SecureRandom(new byte[]{4, 2})::nextDouble);
        return sRand;
    }

    /**
     * Insecure random numbers for use in isolated parallel computations
     *
     * @see SplittableRandom
     */
    public static DoubleStream streamOfPerformantRandomNumbers() {
        DoubleStream dRand = new SplittableRandom().doubles();
        return dRand;
    }

    /** Generate an {@link java.util.stream.IntStream} whith a range of years. Note, that the second
     * argument is exclusive, i.e. it is not included in the stream!
     * @return a stream of years beginning with 2006 and ending with 2025
     */
    public static IntStream streamOfYears() {
        IntStream years = IntStream.range(2006, 2026);
        return years;
    }
}
