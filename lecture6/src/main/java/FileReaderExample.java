import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reading files using Java8-style {@link Files files} and {@link Stream streams}.
 *
 * @see <a href="https://www.mkyong.com/java8/java-8-stream-read-a-file-line-by-line/">based upon Mkyong
 * -Example</a>
 */
public class FileReaderExample {

    /**
     * some arbitrary file to read
     */
    private final static String FILE_NAME = "/etc/passwd";

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReaderExample.class);

    /**
     * Java 7 style filereading using a {@link BufferedReader}
     *
     * @return content of the file
     */
    public static List<String> oldSchoolBufferedReader() {
        final List<String> list = new ArrayList<>();

        try (final BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line = br.readLine();
            while (line != null) {
                list.add(line);
                line = br.readLine();
            }

        } catch (IOException e) {
            LOGGER.error("error reading a file", e);
        }
        return list;
    }

    /**
     * Java 7 style filereading using a {@link Scanner}.
     * @return content of the file
     */
    public static List<String> oldSchoolScanner() {
        final List<String> list = new ArrayList<>();

        try (final Scanner scanner = new Scanner(new File(FILE_NAME))) {

            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }

        } catch (IOException e) {
            LOGGER.error("error reading a file", e);
        }
        return list;
    }
    /**
     * Java 8 style filereading using {@link Files#lines(Path)}.
     * @return content of the file
     */
    public static List<String> simpleStream() {

        //read file into stream, try-with-resources
        try (final Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {

            return stream.collect(Collectors.toList());

        } catch (IOException e) {
            LOGGER.error("error reading a file", e);
        }

        return new ArrayList<>();
    }
    /**
     * Java 8 style filereading using {@link Files#lines(Path)} and filtering and mapping the file
     * contents.
     * @return content of the file
     */
    public static List<String> readLine3AsUppercase() {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {

            //1. filter for root-user
            //2. convert all content to upper case
            //3. convert it into a List
            return stream
                    .filter(line -> !line.startsWith("root"))
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            LOGGER.error("error reading a file", e);
        }
        return new ArrayList<>();
    }
    /**
     * Java 8 style filereading using {@link Files#newBufferedReader(Path)}.
     * @return content of the file
     */
    public static List<String> bufferedReader() {
        List<String> list = new ArrayList<>();

        try (final BufferedReader br = Files.newBufferedReader(Paths.get(FILE_NAME))) {

            list = br.lines().collect(Collectors.toList());

        } catch (IOException e) {
            LOGGER.error("error reading a file", e);
        }
        return list;
    }
}
