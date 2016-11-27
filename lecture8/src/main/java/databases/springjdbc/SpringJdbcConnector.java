package databases.springjdbc;

import databases.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Spring-JDBC example using spring databases.springjdbc
 *
 * @author Created by tom on 22.11.2016.
 */
@Component
@Order(2)
public class SpringJdbcConnector implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SpringJdbcConnector.class);

    // major magic: h2 driver is found in classpath, loaded and connected to
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void run(String... args) {
        logger.info("Running SpringJdbcConnector");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        final List<Object[]> names = Stream.of("Arun Gupta", "Lukas Eder", "Josh Juneau", "Josh" +
                " Long", "Vlad Mihalcea")
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", names);

        logger.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
                new Object[]{"Josh"},
                (rs, rowNum) -> new Person(rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"))
        ).forEach(customer -> logger.info("read value: " + customer.toString()));
    }
}

