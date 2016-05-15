package com.netcracker.edu.bestgroup.projects.ssh.pojo;

import com.netcracker.edu.bestgroup.projects.ssh.entities.User;

import java.math.BigInteger;

/**
 * This class is used during edit of a connection in a dialog
 */
public class ConnectionPOJO {
    private BigInteger connectionId;

    private User user;

    private String login;

    private String password;

    private String hostName;

    private int port;

    public ConnectionPOJO(BigInteger connectionId, User user, String login, String password, String hostName, int port) {
        this.connectionId = connectionId;
        this.user = user;
        this.login = login;
        this.password = password;
        this.hostName = hostName;
        this.port = port;
    }

    public BigInteger getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(BigInteger connectionId) {
        this.connectionId = connectionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
