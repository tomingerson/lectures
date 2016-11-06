import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Examples of filter, map and fold (reduce) on streams.
 *
 * @author Created by tom on 30.10.2016.
 */
public class StreamFilterMapReduce {
    private static final List<String> words = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamFilterMapReduce.class);

    static {
        for (int i = 0; i < 20000; i++) {
            words.add(RandomStringUtils.randomAscii(6));
        }
    }

    /**
     * basic example of a stream iterating over the stream elements
     */
    public static void sequentialStream() {
        words.stream().forEach(s -> {
            if (s.length() > 0) {
                LOGGER.info(s);
            }
        });
    }

    /**
     * apply a filter on all elements of the stream and then iterate over all the remaining elements
     */
    public static void sequentialFiltered() {
        words.stream()
                .filter(s -> !s.isEmpty())
                .forEach(LOGGER::info);
    }

    /**
     * There is no method reference for "String is not empty" in the class String.
     */
    public static void sequentialFilteredOops() {
        words.stream()
                .filter(String::isEmpty)
                .forEach(LOGGER::info);
    }

    /**
     * Iterate over the words and replace the elements with a different string.
     * Uses a classic for-loop.
     */
    public static void filterMapLoop() {
        for (final String s : words) {
            if (s != null && s.length() > 0) {
                final String t = s.replaceAll("x", "z");
                LOGGER.info(t);
            }
        }
    }

    /**
     * Iterate over the words and replace the elements with a different string.
     * Uses a stream and the {@link java.util.stream.Stream#map(Function) map} operation.
     *
     * @see Objects#nonNull(Object)
     */
    public static void filterMap() {
        words.stream()
                .filter(Objects::nonNull)
                .filter(s -> s.length() > 0)
                .map(s -> s.replaceAll("x", "z"))
                .forEach(LOGGER::info);
    }

    /**
     * Iterate over the words and calculate the length of all the words.
     * Uses a loop.
     */
    public static void loopFilterMapReduce() {
        int sum = 0;
        for (final String s : words) {
            if (s.length() > 0) {
                sum = sum + s.length();
            }
        }
    }

    /**
     * Iterate over the words and calculate the length of all the words.
     * Uses {@link java.util.stream.Stream#filter(Predicate) filter},
     * {@link java.util.stream.Stream#map(Function) map} and
     * {@link java.util.stream.Stream#reduce(Object, BinaryOperator) reduce} on the stream.
     * It also uses {@link java.util.stream.Stream#peek(Consumer) peek} to take a look at each element
     * without changing the stream (side effect).
     */
    public static void streamFilterMapReduce() {
        words.stream()
                .filter(s -> s.length() > 0)
                .peek(LOGGER::info)
                .map(String::length)
                .peek(i -> LOGGER.info(String.valueOf(i)))
                //.reduce(0, (s, t) -> s + t);
                .reduce(0, Integer::sum);
    }

    /** same as above without the peek to compare performance */
    public static void streamFilterMapReducePerformancetest() {
        words.stream()
                .filter(s -> s.length() > 0)
                .map(String::length)
                //.reduce(0, (s, t) -> s + t);
                .reduce(0, Integer::sum);
    }
    /**
     * Almost the same as {@link StreamFilterMapReduce#streamFilterMapReduce()} but uses a
     * parallelStream.
     */
    public static void pstreamFilterMapReduce() {
        words.parallelStream()
                .filter(s -> s.length() > 0)
                .map(String::length)
                //.reduce(0, (s, t) -> s + t);
                .reduce(0, Integer::sum);
    }

    /**
     * Use a parallel stream, filter out null-values and empty string, map to the length of each
     * element, order all elements by size, sum up all the elements.
     * The use of {@link Stream#sorted()} breaks the parallelism of the operation, since all threads
     * have to synchronize in order to collect all elements and sort them.
     */
    public static void pstreamFilterMapReduceNullsafe() {
        words.parallelStream()
                .filter(Objects::nonNull)
                .filter(s -> s.length() > 0)
                .map(String::length)
                .sorted()
                .reduce(0, Integer::sum);
    }


    public static void main(String... args) {
        LOGGER.info("words have been initialised");

        final Instant startL = Instant.now();
        loopFilterMapReduce();
        final Instant endL = Instant.now();
        LOGGER.info("   loop " + Duration.between(startL, endL));

        final Instant startS = Instant.now();
        streamFilterMapReducePerformancetest();
        final Instant endS = Instant.now();
        LOGGER.info(" stream " + Duration.between(startS, endS));

        final Instant startP = Instant.now();
        pstreamFilterMapReduce();
        final Instant endP = Instant.now();
        LOGGER.info("pstream " + Duration.between(startP, endP));
    }
}