package com.netcracker.edu.bestgroup.projects.ssh.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "Users")
public class User implements Serializable {
    private static final long serialVersionUID = 1945342493774132278L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private BigInteger userId;

    @Column(nullable = false)
    private String login;

    @Column(name = "user_name")
    private String userName;

    @Column(nullable = false)
    private String password;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
