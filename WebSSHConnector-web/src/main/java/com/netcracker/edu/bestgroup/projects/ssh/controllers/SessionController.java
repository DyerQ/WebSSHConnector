package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.MultipleSessionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.connect.error.InvalidConnectionParametersException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.error.SessionInitializationException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.error.SessionStateException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.model.SSHCommandResult;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;
import com.netcracker.edu.bestgroup.projects.ssh.output.SSHCommandResultAdapter;
import com.netcracker.edu.bestgroup.projects.ssh.output.SSHCommandResultAdapterProducer;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ViewScoped
@ManagedBean
public class SessionController {
    private static final String ECHO_USER_NAME_COMMAND = "echo $USER";
    private static final String CURRENT_DIRECTORY_COMMAND = "pwd";
    @EJB
    MultipleSessionsEJB multipleSessionsEJB;

    private Connection activeConnection = null;
    private Date connectionDate = null;

    private String command;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    private List<SSHCommandResultAdapter> output = new ArrayList<>();

    public void connect(Connection connection) throws InvalidConnectionParametersException, SessionInitializationException {
        if (activeConnection == null) {
            multipleSessionsEJB.openNewSession(connection);
            activeConnection = connection;
            connectionDate = new Date();
            FacesMessage message = new FacesMessage("Connected to " + connection);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage("Could not connect to " + connection,
                    "Another connection is active: " + activeConnection);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void executeCommand() throws SessionStateException {
        if (activeConnection != null) {
            // we expect that default commands will be successful
            SSHCommandResult userNameCommandResult = multipleSessionsEJB.executeCommand(activeConnection,
                    ECHO_USER_NAME_COMMAND);
            String userName = userNameCommandResult.getOutput().get(0);
            SSHCommandResult directoryCommandResult = multipleSessionsEJB.executeCommand(activeConnection,
                    CURRENT_DIRECTORY_COMMAND);
            String directory = directoryCommandResult.getOutput().get(0);
            SSHCommandResultAdapterProducer userInputOutputBuilder = SSHCommandResultAdapterProducer
                    .getUserInputAdapterProducer(userName, directory);
            output.add(userInputOutputBuilder.convert(command));
            SSHCommandResult result = multipleSessionsEJB.executeCommand(activeConnection, this.command);
            SSHCommandResultAdapterProducer outputBuilder;
            if (result.isErroneous()) {
                outputBuilder = SSHCommandResultAdapterProducer.getErrorAdapterProducer();
            } else {
                outputBuilder = SSHCommandResultAdapterProducer.getPlainAdapterProducer();
            }
            output.addAll(outputBuilder.convertAll(result.getOutput()));
            this.command = null;

        } else {
            FacesMessage message = new FacesMessage("Could not execute command",
                    "Could not execute command, no active connection at the moment");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void disconnect() {
        if (activeConnection != null) {
            multipleSessionsEJB.closeConnection(activeConnection);
            activeConnection = null;
            connectionDate = null;
        } else {
            FacesMessage message = new FacesMessage("Nothing to disconnect", "There is no active connection");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public Connection getActiveConnection() {
        return activeConnection;
    }

    public String getActiveConnectionInformation() {
        if (activeConnection != null) {
            return activeConnection + " connected at " + DATE_FORMAT.format(connectionDate);
        } else {
            return "No active connection";
        }
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<SSHCommandResultAdapter> getOutput() {
        return output;
    }
}