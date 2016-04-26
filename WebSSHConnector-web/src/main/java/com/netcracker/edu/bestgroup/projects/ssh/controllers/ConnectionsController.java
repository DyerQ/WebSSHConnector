package com.netcracker.edu.bestgroup.projects.ssh.controllers;


import com.netcracker.edu.bestgroup.projects.ssh.beans.ConnectionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class ConnectionsController {
    private User currentUser;
    private Connection connection = new Connection();

    {
        connection.setPort(22);
    }

    @EJB
    private ConnectionsEJB connectionsEJB;
    @EJB
    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String login = req.getParameter("user");
        if (login == null) {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage("INFO", "LOGIN IS NULL"));
        }
        currentUser = usersEJB.findUserByLogin(login);
        connection.setUser(currentUser);
    }


    public void addNewConnection() {
        connectionsEJB.addNew(connection);

        connection = new Connection();
        connection.setPort(22);
        connection.setUser(currentUser);
    }


    public void deleteConnection(Connection connection) {
        connectionsEJB.delete(connection);
    }

    public void saveRow(RowEditEvent event) {
        Connection editedConnection = ((Connection) event.getObject());
        connectionsEJB.save(editedConnection);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}