package com.netcracker.edu.bestgroup.projects.ssh.entities;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigInteger;

public class Connections implements Serializable {

    @Id
    private BigInteger id;

    private String hostName;

    private Integer port;

    private String connectionType;

    private String login;

    private String password;

    public BigInteger getId() {
        return id;
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

    public void setId(BigInteger id){
        this.id = id;
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
