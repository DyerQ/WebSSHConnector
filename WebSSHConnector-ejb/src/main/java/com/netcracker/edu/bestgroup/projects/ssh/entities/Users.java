package com.netcracker.edu.bestgroup.projects.ssh.entities;

import javax.persistence.*;
import javax.persistence.Transient;
import java.beans.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@SequenceGenerator(name="seq", initialValue=1)
public class Users implements Serializable {


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Id private BigInteger id;

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
