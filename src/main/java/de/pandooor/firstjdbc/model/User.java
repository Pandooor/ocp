package de.pandooor.firstjdbc.model;

import java.io.Serializable;

public class User extends AbstractEntity implements Serializable {

    //add serialVersionUID

    private String firstname;
    private String lastname;

    public User() {

    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(int id, String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        setId(id);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
