package databases.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Created by tom on 22.11.2016.
 */
public class Person {
    private final Long id;
    private final String firstName, lastName;

    public Person(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}