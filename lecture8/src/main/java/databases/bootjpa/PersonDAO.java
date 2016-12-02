package databases.bootjpa;

import databases.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Created by tom on 28.11.2016.
 */
public interface PersonDAO extends JpaRepository<Person, Long> {
    List<Person> findByFirstName(String firstName);
}
