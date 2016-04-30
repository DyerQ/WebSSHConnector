package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.SessionEJB;
import com.netcracker.edu.bestgroup.projects.ssh.connect.SSHCommandResult;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.InvalidConnectionParametersException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionInitializationException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionStateException;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean
public class SessionController {
    @EJB
    SessionEJB sessionEJB;

    private List<String> output = new ArrayList<>();

    public void connect() throws InvalidConnectionParametersException, SessionInitializationException {
        Connection connection = new Connection();
        connection.setLogin("root");
        connection.setPassword("netcracker");
        connection.setHostName("127.0.0.1");
        connection.setPort(22);
        sessionEJB.openNewSession(connection);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful connection"));
    }

    public void execute() throws SessionStateException {
        SSHCommandResult result = sessionEJB.executeCommand("man pwd");
        output.add(String.valueOf(result.isErroneous()));
        output.addAll(result.getOutput());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Execution"));
    }

    public void disconnect() {
        sessionEJB.closeSession();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Disconnect"));
    }

    public List<String> getOutput() {
        return output;
    }
}