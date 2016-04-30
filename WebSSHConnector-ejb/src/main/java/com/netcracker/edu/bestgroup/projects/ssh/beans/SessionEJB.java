package com.netcracker.edu.bestgroup.projects.ssh.beans;

import com.netcracker.edu.bestgroup.projects.ssh.connect.SSHCommandResult;
import com.netcracker.edu.bestgroup.projects.ssh.connect.SSHSession;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.InvalidConnectionParametersException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionInitializationException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionStateException;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;

import javax.ejb.Stateful;


@Stateful
public class SessionEJB {

    private SSHSession activeSession;

    public boolean hasActiveSession() {
        return this.activeSession != null;
    }

    public void openNewSession(Connection connection) throws InvalidConnectionParametersException, SessionInitializationException {
        if (hasActiveSession()) {
            closeSession();
        }
        activeSession = new SSHSession(connection.getLogin(), connection.getPassword(), connection.getHostName(), connection.getPort());
    }

    public SSHCommandResult executeCommand(String command) throws SessionStateException {
        if (!hasActiveSession()) {
            throw new IllegalStateException("No active session");
        }
        return activeSession.executeCommand(command);
    }

    public void closeSession() {
        if (hasActiveSession()) {
            activeSession.close();
        }
    }


}
