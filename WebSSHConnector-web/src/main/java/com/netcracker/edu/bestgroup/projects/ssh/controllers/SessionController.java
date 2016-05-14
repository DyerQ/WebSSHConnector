package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.UserSessionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.connect.SSHCommandResult;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.InvalidConnectionParametersException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionInitializationException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionStateException;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;
import com.netcracker.edu.bestgroup.projects.ssh.output.SSHCommandOutput;
import com.netcracker.edu.bestgroup.projects.ssh.output.SSHCommandOutputBuilder;

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
    UserSessionsEJB userSessionsEJB;

    private Connection activeConnection = null;
    private Date connectionDate = null;

    private String command;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    private List<SSHCommandOutput> output = new ArrayList<>();

    public void connect(Connection connection) throws InvalidConnectionParametersException, SessionInitializationException {
        if (activeConnection == null) {
            userSessionsEJB.openNewSession(connection);
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
            User user = activeConnection.getUser();
            SSHCommandResult userNameCommandResult = userSessionsEJB.executeCommand(user, ECHO_USER_NAME_COMMAND);
            // we expect that it'll be successful
            String userName = userNameCommandResult.getOutput().get(0);
            SSHCommandResult directoryCommandResult = userSessionsEJB.executeCommand(user, CURRENT_DIRECTORY_COMMAND);
            String directory = directoryCommandResult.getOutput().get(0);
            SSHCommandOutputBuilder userInputOutputBuilder = SSHCommandOutputBuilder.getUserInputOutputBuilder(userName, directory);
            output.add(userInputOutputBuilder.convert(command));
            SSHCommandResult result = userSessionsEJB.executeCommand(user, this.command);
            SSHCommandOutputBuilder outputBuilder;
            if (result.isErroneous()) {
                outputBuilder = SSHCommandOutputBuilder.getErrorOutputBuilder();
            } else {
                outputBuilder = SSHCommandOutputBuilder.getPlainOutputBuilder();
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
            userSessionsEJB.closeSession(activeConnection.getUser());
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
            return "";
        }
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<SSHCommandOutput> getOutput() {
        return output;
    }
}