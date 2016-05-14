package com.netcracker.edu.bestgroup.projects.ssh.beans;


import com.netcracker.edu.bestgroup.projects.ssh.connect.SSHCommandResult;
import com.netcracker.edu.bestgroup.projects.ssh.connect.SSHSession;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.InvalidConnectionParametersException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionInitializationException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionStateException;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import java.util.LinkedHashMap;
import java.util.Map;

@Stateful
public class UserSessionsEJB {

    private Map<User, SSHSession> activeSessions;

    @PostConstruct
    private void postConstruct() {
        activeSessions = new LinkedHashMap<>();
    }

    public boolean hasActiveSession(User user) {
        return activeSessions.containsKey(user);
    }

    // does not support auto-close of previous session
    public void openNewSession(Connection connection) throws InvalidConnectionParametersException, SessionInitializationException {
        User user = connection.getUser();
        if (hasActiveSession(user)) {
            closeSession(user);
        }
        SSHSession session = new SSHSession(connection.getLogin(), connection.getPassword(), connection.getHostName(), connection.getPort());
        activeSessions.put(user, session);
    }

    public SSHCommandResult executeCommand(User user, String command) throws SessionStateException {
        if (!hasActiveSession(user)) {
            throw new IllegalStateException("No active session");
        }
        SSHSession session = activeSessions.get(user);
        return session.executeCommand(command);
    }

    public void closeSession(User user) {
        if (hasActiveSession(user)) {
            SSHSession session = activeSessions.get(user);
            session.close();
            activeSessions.remove(user);
        }
    }


}
