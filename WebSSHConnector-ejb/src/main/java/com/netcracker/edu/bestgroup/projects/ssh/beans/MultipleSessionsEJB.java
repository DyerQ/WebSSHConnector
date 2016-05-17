package com.netcracker.edu.bestgroup.projects.ssh.beans;

import com.netcracker.edu.bestgroup.projects.ssh.connect.impl.SSHSessionFactory;
import com.netcracker.edu.bestgroup.projects.ssh.connect.model.SSHCommandResult;
import com.netcracker.edu.bestgroup.projects.ssh.connect.model.SSHSession;
import com.netcracker.edu.bestgroup.projects.ssh.connect.error.InvalidConnectionParametersException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.error.SessionInitializationException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.error.SessionStateException;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Stateful
public class MultipleSessionsEJB {
    private Map<User, Map<Connection, SSHSession>> usersSessions;

    @PostConstruct
    private void postConstruct() {
        usersSessions = new LinkedHashMap<>();
    }

    public Map<Connection, SSHSession> getUserSessions(User user) {
        if (usersSessions.containsKey(user)) {
            return usersSessions.get(user);
        } else {
            return Collections.emptyMap();
        }
    }

    public boolean openNewSession(Connection connection) throws InvalidConnectionParametersException, SessionInitializationException {
        User user = connection.getUser();
        if (usersSessions.containsKey(user)) {
            Map<Connection, SSHSession> userSessions = usersSessions.get(user);
            if (!userSessions.containsKey(connection)) {
                userSessions.put(connection, newSessionByConnection(connection));
            } else {
                return false;
            }
        } else {
            Map<Connection, SSHSession> userSessions = new LinkedHashMap<>();
            userSessions.put(connection, newSessionByConnection(connection));
            usersSessions.put(user, userSessions);
        }
        return true;
    }

    private SSHSession newSessionByConnection(Connection connection) throws InvalidConnectionParametersException, SessionInitializationException {
        String login = connection.getLogin();
        String password = connection.getPassword();
        String hostName = connection.getHostName();
        int port = connection.getPort();
        return SSHSessionFactory.openSSHSession(login, password, hostName, port);
    }

    public SSHCommandResult executeCommand(Connection connection, String command) throws SessionStateException {
        User user = connection.getUser();
        if (!usersSessions.containsKey(user)) {
            return null;
        }

        Map<Connection, SSHSession> userSessions = usersSessions.get(user);
        if (!userSessions.containsKey(connection)) {
            return null;
        }

        SSHSession sshSession = userSessions.get(connection);
        return sshSession.executeCommand(command);
    }

    public boolean closeConnection(Connection connection) {
        User user = connection.getUser();
        Map<Connection, SSHSession> userSessions = getUserSessions(user);
        if (userSessions.containsKey(connection)) {
            SSHSession sshSession = userSessions.get(connection);
            sshSession.close();
            userSessions.remove(connection);
            return true;
        } else {
            return false;
        }
    }

    @PreDestroy
    private void preDestroy() {
        for (Map<Connection, SSHSession> sessionMap : usersSessions.values()) {
            for (SSHSession sshSession : sessionMap.values()) {
                sshSession.close();
            }
        }
    }
}
