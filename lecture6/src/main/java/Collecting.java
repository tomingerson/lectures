import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Collecting elements in a stream using {@link Collectors}.
 *
 * @author Created by tom on 02.11.2016.
 */
public class Collecting {

    private static final Logger LOGGER = LoggerFactory.getLogger(Collecting.class);

    /**
     * Basic collectors to create a list, a set and an array.
     */
    public static void collectBasic() {
        final List<String> list = Stream.of("one", "two", "three").collect(Collectors.toList());
        final Set<String> set = Stream.of("one", "two", "three").collect(Collectors.toSet());
        final Object[] objects = Stream.of("one", "two", "three").toArray();
        final String[] stringsMethodReference = Stream.of("one", "two", "three").toArray(String[]::new);
        final String[] stringLambda = Stream.of("one", "two", "three").toArray(size -> new String[size]);
    }

    /**
     * String collector {@link Collectors#joining joining}.
     */
    public static void collectStrings() {
        final String joined = Stream.of("one", "two", "three").collect(Collectors.joining());
        LOGGER.info(joined);
        final String delimited = Stream.of("one", "two", "three").collect(Collectors.joining(", "));
        LOGGER.info(delimited);
        final String toStringStyle = Stream.of("one", "two", "three").collect(Collectors.joining(", ",
                "[", "]"));
        LOGGER.info(toStringStyle);
    }

    /**
     * Collect to a specified set.
     */
    public static void collectToSet() {
        final Set<String> stringTreeSet = Stream.of("one", "two", "three").collect(Collectors
                .toCollection(TreeSet::new));
        final Set<String> stringTreeSetCustomOrder = Stream.of("one", "two", "three").collect(Collectors
                .toCollection(() -> new TreeSet<>(String::compareToIgnoreCase)));
    }

    /**
     * Collect to Maps.
     */
    public static void collectToMap() {
        // setup some data
        final Address address = new Address("Baker Street 221b");
        final Person sherlock = new Person(1L, "Sherlock", "Holmes", address, LocalDate.of(1854, 1, 1));
        final Person watson = new Person(2L, "John", "Watson", address, LocalDate.of(1860, 1, 1));

        final Collection<Person> detectives = new ArrayList<>();
        detectives.add(sherlock);
        detectives.add(watson);

        // stream and collect to map
        final Map<Long, String> idToName = detectives.parallelStream()
                .collect(Collectors.toMap(Person::getPersonId, Person::getLastname));

        final Map<Long, Person> idToPerson = detectives.parallelStream()
                .collect(Collectors.toMap(Person::getPersonId, Function.identity()));

        final Map<Address, List<Person>> addrToPerson = detectives.parallelStream()
                .collect(Collectors.toMap(Person::getAddress, person -> {
                    List<Person> tmp = new ArrayList<>();
                    tmp.add(person);
                    return tmp;
                }, (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                }));

        final Map<Address, List<Person>> addrToPersonTree = detectives.parallelStream()
                .collect(Collectors.toMap(Person::getAddress, person -> {
                    List<Person> tmp = new ArrayList<>();
                    tmp.add(person);
                    return tmp;
                }, (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                }, () -> new TreeMap<>((addr1, addr2) -> addr1.getStreet()
                        .compareToIgnoreCase(addr2.getStreet()))));

        final Map<Address, List<Person>> groupedAddrToPerson = detectives.parallelStream()
                .collect(Collectors.groupingBy(Person::getAddress));

        final Map<Boolean, List<Person>> baker = detectives.stream()
                .collect(Collectors
                        .partitioningBy(person -> person.getAddress().getStreet().startsWith("Baker"))
                );

        final Map<Address, Optional<Person>> oldestByAddress = detectives.stream()
                .collect(Collectors.groupingBy(Person::getAddress,
                        Collectors.maxBy((p1, p2) -> p1.getAge() - p2.getAge())
                ));

        final Map<Address, Set<String>> addrToNames = detectives.stream()
                .collect(Collectors.groupingBy(Person::getAddress,
                        Collectors.mapping(Person::getFirstname, Collectors.toSet())
                ));
    }

    /**
     * Collect using your own implementation of a collector. Don't implement the interface
     * {@link java.util.stream.Collector}, but use the factory {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}.
     */
    public static void collectYourOwn() {
        final List<Object> collected = Stream.of("one", "two", "three").collect(ArrayList::new,
                ArrayList::add, ArrayList::addAll);
    }

    /**
     * Statistical data: averagingInt.
     * averagingInt needs a {@link java.util.function.ToIntFunction}, which means we need a stream of
     * Objects, and not int. Therefore we box the ints.
     */
    public static void statistics() {
        final Double average = IntStream.range(0, 10)
                .boxed()
                .collect(Collectors.averagingInt(i -> i.intValue()));
    }

    /**
     * see the difference in performance using joining vs reduce
     */
    public static void performanceJoin(Stream<String> source) {
        source.parallel().collect(Collectors.joining(", "));
    }

    /**
     * see the difference in performance using joining vs reduce
     */
    public static void performanceReduce(Stream<String> source) {
        source.reduce("", (s1, s2) -> s1 + ", " + s2);
    }


    public static void main(String... args) {

        collectBasic();
        collectStrings();
        collectToMap();


        final Stream<String> stream1 = Stream.generate(() -> RandomStringUtils.random(6)).limit(20000);
        final Stream<String> stream2 = Stream.generate(() -> RandomStringUtils.random(6)).limit(20000);

        final Instant startJ = Instant.now();
        performanceJoin(stream1);
        final Instant endJ = Instant.now();

        final Instant startL = Instant.now();
        performanceReduce(stream2);
        final Instant endL = Instant.now();

        LOGGER.info("Join: " + Duration.between(startJ, endJ) + ", reduce: " + Duration.between
                (startL, endL));
    }

    /**
     * quick implementation of a Person as a data transfer object (DTO).
     */
    private static final class Person {
        private final Long personId;
        private final String firstname;
        private final String lastname;
        private final Address address;
        private final LocalDate dob;

        public Person(Long personId, String firstname, String lastname, Address address, LocalDate dob) {
            this.personId = personId;
            this.firstname = firstname;
            this.lastname = lastname;
            this.address = address;
            this.dob = dob;
        }

        public Long getPersonId() {
            return personId;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public Address getAddress() {
            return address;
        }

        public int getAge() {
            return Period.between(dob, LocalDate.now()).getYears();
        }
    }

    /**
     * very short implementation of an address. Not useful outside of the scope of these examples.
     */
    private static final class Address {
        private final String street;

        public Address(String street) {
            this.street = street;
        }

        public String getStreet() {
            return street;
        }
    }
}
