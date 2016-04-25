package com.netcracker.edu.bestgroup.projects.ssh.connect;

import com.jcraft.jsch.*;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.ConnectionStringFormatException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.InvalidConnectionParametersException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionInitializationException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionStateException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class provides the ability to initiate SSHSessions with certain connection parameters
 * It does not instantiate a TERM instance, having ability only to execute commands one by one
 * After the object is used, session should be closed by invoking @method close()
 * <p>
 * Implements AutoCloseable interface for convenience
 */
public class SSHSession implements AutoCloseable {
    private Session session;

    /**
     * Creates the instance of SSHSession and instantiates the JSCh session
     *
     * @param connectionString in the format of 'username/password@host[:port]', where
     *                         [param] means that the parameter can be omitted
     *                         If port is omitted, default value of 22 is used
     *                         Connection without a password is unavailable
     * @throws InvalidConnectionParametersException on invalid connectionString
     */
    public SSHSession(String connectionString) throws InvalidConnectionParametersException, SessionInitializationException {
        if (!connectionString.matches("^[^/@:]+/[^/@:]+@[^/@:]+(:\\d+)?$")) {
            throw new ConnectionStringFormatException("Format of connectionString is " +
                    "username/password@host[:port]");
        }

        int userNameEndIndex = connectionString.contains("/") ? connectionString.indexOf('/') : connectionString.indexOf('@');
        String userName = connectionString.substring(0, userNameEndIndex);

        // by matching to the pattern we ensured that the length of password is more than 0
        String password = connectionString.substring(connectionString.indexOf('/') + 1, connectionString.indexOf('@'));

        if (!connectionString.contains(":")) {
            connectionString = connectionString + ":" + Const.DEFAULT_PORT;
        }
        String host = connectionString.substring(connectionString.indexOf('@') + 1, connectionString.indexOf(':'));

        int port = Integer.parseInt(connectionString.substring(connectionString.indexOf(':') + 1));
        if (port < Const.PORT_MIN_VALUE || port > Const.PORT_MAX_VALUE) {
            throw new InvalidConnectionParametersException("port = " + port + " is invalid value");
        }

        this.session = initSession(userName, password, host, port);
    }

    /**
     * Creates the instance of SSHSession and instantiates the JSCh session
     *
     * @param userName @NotNull name of the user on the host server
     * @param password password of specified user
     * @param host     @NotNull host server address
     * @param port     socket opening address
     * @throws InvalidConnectionParametersException on invalid connection parameters
     */
    public SSHSession(String userName, String password, String host, Integer port) throws InvalidConnectionParametersException, SessionInitializationException {
        if (userName == null || userName.isEmpty()) {
            throw new InvalidConnectionParametersException("userName can't be null");
        }

        if (password == null || password.isEmpty()) {
            throw new InvalidConnectionParametersException("password can't be null");
        }

        if (host == null || host.isEmpty()) {
            throw new InvalidConnectionParametersException("host can't be null");
        }

        if (port == null) {
            port = Const.DEFAULT_PORT;
        }
        if (port < Const.PORT_MIN_VALUE || port > Const.PORT_MAX_VALUE) {
            throw new InvalidConnectionParametersException("port = " + port + " is invalid value");
        }

        this.session = initSession(userName, password, host, port);
    }

    private Session initSession(String userName, String password, String host, int port) throws SessionInitializationException {
        try {
            JSch jSch = new JSch();
            session = jSch.getSession(userName, host, port);
            session.setPassword(password);
            UserInfo userInfo = new UserInfoImpl();
            session.setUserInfo(userInfo);
        } catch (JSchException ignored) {
            // it is guaranteed that userName and host are not null
            // exception is never thrown
        }
        if (session != null) {
            try {
                session.connect();
                return session;
            } catch (JSchException e) {
                throw new SessionInitializationException("Could not initiate session. Detailed message is: " + e.getMessage());
            }
        } else {
            throw new SessionInitializationException("Could not instantiate session");
        }
    }

    /**
     * Executes the specified command in a separate exec channel
     *
     * @param command command that will be sent to an external terminal
     * @return object with information on whether the command invocation was erroneous and external terminal output
     * @throws SessionStateException if this session can't create or operate exec channels
     */
    public SSHCommandResult executeCommand(String command) throws SessionStateException {
        if (!command.isEmpty()) {
            ChannelExec channel = openExecChannel();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            boolean isErroneous = printExecResult(channel, command, new PrintStream(outputStream));
            channel.disconnect();
            String rawOutput = new String(outputStream.toByteArray());
            if (rawOutput.isEmpty()) {
                return new SSHCommandResult(false, Collections.<String>emptyList());
            }
            String[] linesOutput = rawOutput.split("\\r?\\n");
            return new SSHCommandResult(isErroneous, Arrays.asList(linesOutput));
        } else {
            return null;
        }
    }

    /**
     * This method has to be invoked after all desired commands are executed
     */
    @Override
    public void close() {
        session.disconnect();
    }

    private ChannelExec openExecChannel() throws SessionStateException { // one command - one execution channel
        try {
            ChannelExec channel = (ChannelExec) session.openChannel("exec");

            channel.setInputStream(null);
            channel.setErrStream(new ByteArrayOutputStream());

            return channel;
        } catch (JSchException e) {
            throw new SessionStateException("Could not open exec channel. Detailed message is " + e.getMessage());
        }
    }

    private boolean printExecResult(ChannelExec channel, String command, PrintStream destination) throws SessionStateException {
        InputStream err;
        InputStream in;
        try {
            channel.setCommand(command);
            err = channel.getErrStream();
            in = channel.getInputStream();
            channel.connect();
        } catch (JSchException e) {
            throw new SessionStateException("Could not connect to exec channel for command '" + command +
                    "'. Detailed message is " + e.getMessage());
        } catch (IOException e) {
            throw new SessionStateException("Could not retrieve input streams from exec channel for command '" + command +
                    "'. Detailed message is " + e.getMessage());
        }

        boolean isErroneous = false;
        while (true) {
            flushOutputStream(in, destination);
            if (flushOutputStream(err, destination)) {
                isErroneous = true;
            }

            if (channel.isClosed()) {
                break;
            }
            try {
                Thread.sleep(Const.DEFAULT_CHANNEL_SCANNING_DELAY_MS);
            } catch (InterruptedException ignored) {
                // okay, finishing already
            }
        }

        return isErroneous;
    }

    private boolean flushOutputStream(InputStream from, PrintStream to) {
        boolean success = false;

        try {
            while (from.available() > 0) {
                byte[] tmp = new byte[1024];
                int i = from.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                to.print(new String(tmp, 0, i));
                success = true;
            }
        } catch (IOException ignored) {
            // stream is already closed, IOError occurred or data can't be read. so bad
        }
        return success;
    }
}
