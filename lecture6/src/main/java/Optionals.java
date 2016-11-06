import java.util.Optional;

/**
 * Examples of how to create and use {@link java.util.Optional}.
 *
 * @author Created by tom on 01.11.2016.
 */
public class Optionals {

    /**
     * examples using the factory methods of Optional
     */
    public static void createOptionals() {
        String nullString = null;
        String emptyString = "";

        Optional.ofNullable(nullString);
        Optional.of(emptyString);
        try {
            Optional.of(nullString);
        } catch (NullPointerException e) {
        }
        Optional.empty();
    }

    /**
     * operate on a string-argument that might be null
     */
    public static void filterMap(final String testString) {

        // filter
        if (testString != null && testString.contains("Hello")) {
            System.out.println(testString);
        }
        Optional.ofNullable(testString).filter(s -> s.contains("Hello")).ifPresent(System.out::println);


        // map
        if (testString != null) {
            String trimmed = testString.trim();
            if (trimmed.length() > 0)
                System.out.println(trimmed);
        }
        Optional.ofNullable(testString).map(String::trim).filter(s -> s
                .length() > 0).ifPresent(System.out::println);
    }


    /**
     * example of flatMap
     */
    public static void flatMap(final String testString) {
        final Optional<Optional<String>> optionalOptional = Optional.ofNullable(testString).map
                (Optionals::helperMethod);
        final Optional<String> stringOptional = Optional.ofNullable(testString).flatMap
                (Optionals::helperMethod);
    }

    /**
     * just an example method returning an Optional of String
     * we trim the input
     *
     * @param input some input, might be {@code null}
     * @return trimmed input as optional
     */
    public static Optional<String> helperMethod(final String input) {
        return Optional.ofNullable(input).map(String::trim);
    }

    /**
     * get the String out of {@code Optional<String>}.
     */
    public static void conversion() {
        final String trimmed1 = helperMethod(null).orElse("");
        final String trimmed2 = helperMethod(null).orElseGet(() -> new String("Hello World".codePoints
                ().toArray(), 6, 5));
        final String trimmed3 = helperMethod(null).orElseThrow(() -> new IllegalStateException("some " +
                "error message"));

        String trimmed4 = null;
        final Optional<String> opt = helperMethod(null);
        if ( opt.isPresent()) {
            trimmed4 = opt.get();
        }
        System.out.println(trimmed4);

        helperMethod(null).ifPresent(System.out::println);
    }

    public static void main(String... args) {
        filterMap(null);
        filterMap(" Hello World ");

        flatMap(null);
        flatMap(" Hello World ");
    }

}
