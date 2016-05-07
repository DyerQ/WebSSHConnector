package com.netcracker.edu.bestgroup.projects.ssh.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "Connections")
@SequenceGenerator(name = "ConnectionPKGenerator", sequenceName = "connection_ids_seq", allocationSize = 1)
public class Connection implements Serializable {
    private static final long serialVersionUID = 5975416545613143307L;

    @Id
    @Column(name = "connection_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ConnectionPKGenerator")
    private BigInteger connectionId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "host_name")
    private String hostName;

    @Basic
    private int port;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connection that = (Connection) o;

        return connectionId.equals(that.connectionId);

    }

    @Override
    public int hashCode() {
        return connectionId.hashCode();
    }
}
