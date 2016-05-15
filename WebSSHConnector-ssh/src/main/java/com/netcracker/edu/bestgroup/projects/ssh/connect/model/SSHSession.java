package com.netcracker.edu.bestgroup.projects.ssh.connect.model;

import com.netcracker.edu.bestgroup.projects.ssh.connect.error.SessionStateException;

/**
 * This class provides the ability to initiate SSHSessions with certain connection parameters
 * It does not instantiate a TERM instance, having ability only to execute commands one by one
 * After the object is used, session should be closed by invoking @method close()
 * <p>
 * Implements AutoCloseable interface for convenience
 */
public interface SSHSession extends AutoCloseable {
    /**
     * Executes the specified command in a separate exec channel
     *
     * @param command command that will be sent to an external terminal
     * @return object with information on whether the command invocation was erroneous and external terminal output
     * @throws SessionStateException if this session can't create or operate exec channels
     */
    SSHCommandResult executeCommand(String command) throws SessionStateException;

    @Override
    void close();
}