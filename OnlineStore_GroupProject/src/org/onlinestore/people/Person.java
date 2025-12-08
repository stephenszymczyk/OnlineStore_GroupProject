package org.onlinestore.people;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private String username;
    private String password;

    public Person() {}
    
    //managers and customers will both have unique usernames and an account password
    public Person(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}