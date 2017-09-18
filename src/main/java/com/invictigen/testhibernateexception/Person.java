package com.invictigen.testhibernateexception;

import javax.persistence.*;


@NamedQueries(

        @NamedQuery(
                name = "Person.findForName",
                query = "select p from com.invictigen.testhibernate.Person p where p.firstName=:name"

        )

)
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}