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
import java.util.List;

@ManagedBean
@ViewScoped
public class ConnectionsController {
    private String login;
    private User currentUser;
    private List<Connection> connectionList;
    private Connection connection = new Connection();

    @EJB
    private ConnectionsEJB connectionsEJB;
    @EJB

    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        login = req.getParameter("user");
        if (login == null) {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage("INFO", "LOGIN IS NULL"));
        }
        currentUser = usersEJB.findUserByLogin(login);
        connectionList = connectionsEJB.findUserConnections(login);
        connection.setUser(currentUser);
    }


    public String addNewConnection() {
        connection = connectionsEJB.addNew(connection);
        connectionList = connectionsEJB.findUserConnections(login);
        return "test/connection.xhtml?user=" + login + "&faces-redirect=true";
    }


    public String deleteConnection(Connection user) {
        connectionsEJB.delete(user);
        connectionList = connectionsEJB.findUserConnections(login);
        return "test/connection.xhtml?user=" + user.getLogin() + "&faces-redirect=true";
    }

    public void saveRow(RowEditEvent event) {
        Connection editedUser = ((Connection) event.getObject());
        connectionsEJB.save(editedUser);
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
