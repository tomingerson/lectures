import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Introductory examples. Start with iterating over a list, end up using a stream.
 *
 * @author Created by tom on 25.10.2016.
 */
public class IteratorToStream {

    /**
     * a bsic list of words
     */
    private static final List<String> words = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(IteratorToStream.class);

    static {
        // initialise the list with some random strings
        for (int i = 0; i < 10; i++) {
            words.add(RandomStringUtils.randomAscii(6));
        }
    }

    /**
     * classic for-loop
     */
    public static void loop() {
        for (final String s : words) {
            if (s.length() > 0) {
                LOGGER.info(s);
            }
        }
    }

    /**
     * Iteration using {@link Iterable#forEach}.
     */
    public static void forEach() {
        words.forEach(s -> {
            if (s.length() > 0) {
                LOGGER.info(s);
            }
        });
    }

    /**
     * Iteration using {@link java.util.stream.Stream#forEach(java.util.function.Consumer)} with a
     * sequential stream
     */
    public static void sequentialStream() {
        words.stream().forEach(s -> {
            if (s.length() > 0) {
                LOGGER.info(s);
            }
        });
    }

    /**
     * Iteration using {@link java.util.stream.Stream#forEach(java.util.function.Consumer)} with a
     * parallel stream
     */
    public static void parallelStream() {
        words.parallelStream().forEach(s -> {
            if (s.length() > 0) {
                LOGGER.info(s);
            }
        });
    }

    /**
     * Iteration using {@link java.util.stream.Stream#forEachOrdered(java.util.function.Consumer)} with a
     * parallel stream
     */
    public static void parallelStreamOrdered() {
        words.parallelStream().forEachOrdered(s -> {
            if (s.length() > 0) {
                LOGGER.info(s);
            }
        });
    }

    /**
     * create an endless stream using
     * {@link java.util.stream.Stream#generate(java.util.function.Supplier)}
     */
    public static void forever() {
        Stream.generate(() -> RandomStringUtils.randomAscii(6))
                .distinct()
                .limit(1000) // comment this line out and you have an endless stream
                .forEach(LOGGER::info);
    }

    /**
     * demonstrating the principle: each stream element gets visited only once and then it is gone: a
     * terminal operation closes the stream
     */
    public static void doubleStream() {
        final Stream<String> stream = words.stream();
        stream.forEach(LOGGER::info);
        stream.forEach(LOGGER::info);
    }

    public static void main(String... args) {
        LOGGER.info("sequential:");
        sequentialStream();
        LOGGER.info("parallel:");
        parallelStream();
        LOGGER.info("parallel in order:");
        parallelStreamOrdered();
        LOGGER.info("forever:");
        forever();
        LOGGER.info("doubleStream:");
        doubleStream();
    }
}
