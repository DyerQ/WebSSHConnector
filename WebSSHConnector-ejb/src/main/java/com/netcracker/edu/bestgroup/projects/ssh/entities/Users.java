package com.netcracker.edu.bestgroup.projects.ssh.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@SequenceGenerator(name="seq", initialValue=1)
public class Users implements Serializable {


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Id
    @Column(name = "ID")
    private BigInteger id;

    private String name;

    private String login;

    private String password;

//    @OneToMany(mappedBy = "user")
//    @JoinTable(
//            name="USER_CONNECTIONS",
//            joinColumns={ @JoinColumn(name="ID", referencedColumnName="ID") },
//            inverseJoinColumns={ @JoinColumn(name="CONNECTION_ID", referencedColumnName="CONNECTION_ID", unique=true) }
//    )
    //private List<Connections> connections;

//    public void addConnection(Connections connections){
//        this.connections.add(connections);
//        if(connections.getUser() != this){
//            connections.setUser(this);
//        }
//    }

    public Users(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Users() {
    }

//    public List<Connections> getConnections() {
//        return connections;
//    }
//
//    public void setConnections(List<Connections> connections) {
//        this.connections = connections;
//    }

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
