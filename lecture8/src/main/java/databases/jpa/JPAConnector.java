package databases.jpa;

import databases.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * @author Created by tom on 01.12.2016.
 */
@Component
@Order(4)
@Transactional
public class JPAConnector implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(JPAConnector.class);

    @PersistenceContext
    private EntityManager em;


    @Override
    public void run(String... args) throws Exception {
        final Person p1 = Person.newInstance("Arun", "Gupta");
        final Person p2 = Person.newInstance("Lukas", "Eder");
        final Person p3 = Person.newInstance("Josh", "Juneau");
        final Person p4 = Person.newInstance("Josh", "Long");
        final Person p5 = Person.newInstance("Vlad", "Mihalcea");

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p5);

        logger.info("Querying for customer records where first_name = 'Josh' using JPA:");
        final Query query = em.createQuery("from Person where firstName=:fn");
        query.setParameter("fn", "Josh");

        query.getResultList().forEach(o -> logger.info("read value: " + o.toString()));
    }
}
