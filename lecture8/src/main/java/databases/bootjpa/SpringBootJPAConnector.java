package databases.bootjpa;

import databases.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

/**
 * @author Created by tom on 29.11.2016.
 */
@Component
@Order(3)
public class SpringBootJPAConnector implements CommandLineRunner {


    private static final Logger logger = LoggerFactory.getLogger(SpringBootJPAConnector.class);

    private PersonDAO personDAO;

    public SpringBootJPAConnector(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @Override
    public void run(String... args) throws Exception {
        final Person p1 = Person.newInstance("Arun", "Gupta");
        final Person p2 = Person.newInstance("Lukas", "Eder");
        final Person p3 = Person.newInstance("Josh", "Juneau");
        final Person p4 = Person.newInstance("Josh", "Long");
        final Person p5 = Person.newInstance("Vlad", "Mihalcea");

        personDAO.save(p1);
        personDAO.save(p2);
        personDAO.save(p3);
        personDAO.save(p4);
        personDAO.save(p5);

        logger.info("Querying for customer records where first_name = 'Josh' using " +
                "spring-boot-JPA QBE (query by example):");
        final Person probe = new Person();
        probe.setFirstName("Josh");
        final ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.exact());
        personDAO.findAll(Example.of(probe, matcher))
                .forEach(p -> logger.info("read value: " + p.toString()));
    }
}
