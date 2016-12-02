package databases.plain;

import databases.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

/**
 * @author Created by tom on 22.11.2016.
 */
@Component
@Order(1)
public class DatabaseConnector implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);

    @Override
    public void run(String... args) throws ClassNotFoundException {
        logger.info("Running plain DatabaseConnector");

        Class.forName("org.h2.Driver");
        try (
                final Connection con = DriverManager.getConnection("jdbc:h2:~/plain",
                        "sa", "");
                final Statement stm = con.createStatement()
        ) {


            stm.execute("DROP TABLE customers IF EXISTS");
            stm.execute("CREATE TABLE customers(id SERIAL, " +
                    "first_name VARCHAR(255), " +
                    "last_name VARCHAR(255))");


            try (final PreparedStatement pstm = con.prepareStatement("INSERT INTO " +
                    "customers(first_name, last_name) VALUES (?,?)")) {
                // some database guys
                Stream.of("Arun Gupta", "Lukas Eder", "Josh Juneau", "Josh Long",
                        "Vlad Mihalcea")
                        .map(name -> name.split(" "))
                        .forEach(sa -> {
                            final String fn = sa[0];
                            final String ln = sa[1];
                            try {
                                pstm.setString(1, fn);
                                pstm.setString(2, ln);
                                pstm.addBatch();
                            } catch (SQLException e) {
                                logger.error("error creating a prepared statement with " +
                                        "arguments '" + fn + "' and" + " '" + ln + "'", e);
                            }
                        });
                pstm.executeBatch();
            }
            con.commit();

            logger.info("Querying for customer records where first_name = 'Josh' using " +
                    "plain jdbc:");
            try (final PreparedStatement pstmRead = con.prepareStatement("SELECT id, " +
                    "first_name, last_name FROM customers WHERE first_name = ?")) {
                pstmRead.setString(1, "Josh");
                final ResultSet rs = pstmRead.executeQuery();
                while (rs.next()) {
                    final Person customer = new Person(
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"));
                    logger.info("read value: " + customer.toString());
                }
            }
        } catch (SQLException e) {
            logger.error("something went wrong accessing the database", e);
        }
    }
}
