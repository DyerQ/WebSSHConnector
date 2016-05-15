package com.netcracker.edu.bestgroup.projects.ssh.connect.impl;

import com.netcracker.edu.bestgroup.projects.ssh.connect.error.InvalidConnectionParametersException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.error.SessionInitializationException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.model.SSHSession;

public class SSHSessionFactory {
    private SSHSessionFactory() {
        throw new UnsupportedOperationException("Can't instantiate class");
    }

    /**
     * Creates the instance of SSHSession and instantiates the JSCh session
     *
     * @param connectionString in the format of 'username/password@host[:port]', where
     *                         [param] means that the parameter can be omitted
     *                         If port is omitted, default value of 22 is used
     *                         Connection without a password is unavailable
     * @throws InvalidConnectionParametersException on invalid connectionString,
     * @throws SessionInitializationException       on inability of connection
     */
    public static SSHSession openSSHSession(String connectionString)
            throws InvalidConnectionParametersException, SessionInitializationException {
        return new SSHSessionImpl(connectionString);
    }

    /**
     * Creates the instance of SSHSession and instantiates the JSCh session
     *
     * @param userName @NotNull name of the user on the host server
     * @param password password of specified user
     * @param host     @NotNull host server address
     * @param port     socket opening address
     * @throws InvalidConnectionParametersException on invalid connection parameters,
     * @throws SessionInitializationException       on inability of connection
     */
    public static SSHSession openSSHSession(String userName, String password, String host, Integer port)
            throws InvalidConnectionParametersException, SessionInitializationException {
        return new SSHSessionImpl(userName, password, host, port);
    }
}
