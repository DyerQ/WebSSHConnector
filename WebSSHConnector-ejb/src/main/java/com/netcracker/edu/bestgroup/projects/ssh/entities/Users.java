package com.netcracker.edu.bestgroup.projects.ssh.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
public class Users implements Serializable {

    @Id
    private BigInteger id;

    private String name;

    private String login;

    private String password;

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public BigInteger getId() {

        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
