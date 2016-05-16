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
    private Connection connectionToEdit = connection.clone();
    {
        connection.setPort(22);
        connectionToEdit.setPort(22);
    }

    @EJB
    private ConnectionsEJB connectionsEJB;
    @EJB
    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {

        String login = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login");
        if (login == null || (login.compareTo("") == 0) ) {
            currentUser = usersEJB.getFakeUserInstance();
            connection.setUser(currentUser);
        } else {
            currentUser = usersEJB.findUserByLogin(login);
            connection.setUser(currentUser);
            currentUser.setConnectionList(connectionsEJB.findUserConnections(currentUser.getLogin()));

        }



    }


    public void addNewConnection() {
        connectionsEJB.addNew(connection);
        currentUser.setConnectionList(connectionsEJB.findUserConnections(currentUser.getLogin()));
        clearConnection();
    }
    public void clearConnection(){
        connection = new Connection();
        connection.setPort(22);
        connection.setUser(currentUser);

    }

    public void deleteConnection(Connection connection) {
        connectionsEJB.delete(connection);
        currentUser.setConnectionList(connectionsEJB.findUserConnections(currentUser.getLogin()));
    }

    public void saveRow(RowEditEvent event) {
        Connection editedConnection = ((Connection) event.getObject());
        connectionsEJB.save(editedConnection);
    }

    public void editConnection(Connection conn) {
        connectionToEdit = conn.clone();

    }

    public void cancelEdit() {
        connectionsEJB.save(connectionToEdit);
        currentUser.setConnectionList(connectionsEJB.findUserConnections(currentUser.getLogin()));
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

    public Connection getConnectionToEdit() {
        return connectionToEdit;
    }

    public void setConnectionToEdit(Connection connectionToEdit) {
        this.connectionToEdit = connectionToEdit;
    }


}
