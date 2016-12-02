package databases.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

/**
 * @author Created by tom on 30.11.2016.
 */
@Entity
public class PersonPicture {

    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "picture")
    private byte[] data;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "personPicture")
    @JoinColumn(name = "id")
    @MapsId
    private Person person;

    public PersonPicture() {
    }

    public PersonPicture(Person person) {
        this.person = person;
    }

    public PersonPicture(byte[] data, Person person) {
        this.data = data;
        this.person = person;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
