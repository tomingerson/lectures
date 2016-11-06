import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Examples using {@link Stream#map(Function)} and {@link Stream#flatMap(Function)}.<br>
 * based upon
 * <a href="http://www.angelikalanger.com/Articles/EffectiveJava/75.Java8.Fundamental-Stream-Operations/75.Java8.Fundamental-Stream-Operations.html">Article by Angelika Langer</a>
 *
 * @author Created by tom on 01.11.2016.
 */
public class MapFlatmap {
    //private static final String FILENAME = "/etc/passwd";
    private static final String FILENAME = "C:/setup.log";
    private static final Logger LOGGER = LoggerFactory.getLogger(MapFlatmap.class);

    /**
     * maps the file lines to a different type
     *
     * @throws IOException in case the file cannot be read
     */
    public static int exampleMapToInt() throws IOException {
        final Stream<String> lines = Files.lines(Paths.get(FILENAME));
        // you can map to a Stream with a different type
        //final Stream<Integer> integerStream = lines.map(String::length);
        // or to a different type at all
        final IntStream lineLengths = lines.mapToInt(String::length);
        final int sum = lineLengths.sum();

        LOGGER.info("calculated sum: " + sum);

        return sum;
    }

    /**
     * Example what does not work as expected
     *
     * @return count of lines in the file
     * @throws IOException in case the file cannot be read
     */
    public static long exampleFlatMapToInt1() throws IOException {

        final Stream<String> lines = Files.lines(Paths.get(FILENAME));
        // matches any character beginning with a unicode letter -> ignores punctuation marks
        final Stream<Stream<String>> words = lines.map(Pattern.compile("[^\\p{L}]")::splitAsStream);

        // this returns only the count of the "outer" stream, not what we want
        long sum = words.count();

        LOGGER.info("calculated sum: " + sum);

        return sum;
    }

    /**
     * Example using flatmap<br>also take a look at the lab-unittests for an example on converting a
     * {@code Stream<Set<String>>} so a {@code Stream<String>}.
     *
     * @return count of words in the file
     * @throws IOException in case the file cannot be read
     */
    public static long exampleFlatMapToInt2() throws IOException {

        final Stream<String> lines = Files.lines(Paths.get(FILENAME));
        // flatMap flattens the Stream of Stream into a single Stream
        final Stream<String> words = lines.flatMap(Pattern.compile("[^\\p{L}]")::splitAsStream);

        long sum = words.count();

        LOGGER.info("calculated sum: " + sum);

        return sum;
    }

    /**
     * Interfering with an underlying list while streaming just leads to an
     * {@link java.util.ConcurrentModificationException}
     */
    public static void interfering() {
        List<Integer> underlyingList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        underlyingList.stream()
                .map(i -> 2 * i)
                .forEach(underlyingList::add);
    }


    public static void main(String... args) {
        try {
            exampleMapToInt();
            exampleFlatMapToInt1();
            exampleFlatMapToInt2();
            interfering();
        } catch (IOException e) {
            LOGGER.error("error reading a file", e);
        }
    }

}
