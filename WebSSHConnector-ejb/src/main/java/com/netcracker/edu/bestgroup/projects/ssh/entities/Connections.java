package com.netcracker.edu.bestgroup.projects.ssh.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@SequenceGenerator(name="seq", initialValue=1)
public class Connections implements Serializable {


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Id
    private BigInteger connection_id;

    public Connections() {
    }

    public Connections(BigInteger connection_id,String hostName, Integer port, String connectionType, String login, String password, BigInteger user_id) {
        this.connection_id = connection_id;
        this.hostName = hostName;
        this.port = port;
        this.connectionType = connectionType;
        this.login = login;
        this.password = password;
        this.user_id = user_id;
    }

//    public BigInteger getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(BigInteger user_id) {
//        this.user_id = user_id;
//    }
//
//    private BigInteger user_id;

    private String hostName;

    private Integer port;

    private String connectionType;

    private String login;

    private String password;

    private BigInteger user_id;

    public BigInteger getUser_id() {
        return user_id;
    }

    public void setUser_id(BigInteger user_id) {
        this.user_id = user_id;
    }

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name ="USER_ID")
   // private Users user;

//    public Users getUser() {
//        return user;
//    }
//
//    public void setUser(Users user) {
//        this.user = user;
//        if(user.getConnections().contains(this)){
//            user.getConnections().add(this);
//        }
//    }


    public BigInteger getConnection_id() {
        return connection_id;
    }

    public String getHostName() {
        return hostName;
    }

    public Integer getPort() {
        return port;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setConnection_id(BigInteger id){
        this.connection_id = id;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
