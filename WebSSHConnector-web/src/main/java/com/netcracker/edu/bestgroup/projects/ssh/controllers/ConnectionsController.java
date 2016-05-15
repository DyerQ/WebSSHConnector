package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.ConnectionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;
import com.netcracker.edu.bestgroup.projects.ssh.pojo.ConnectionPOJO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class ConnectionsController {

    private User currentUser;

    private Connection fakeConnection;

    private ConnectionPOJO connectionToAdd;

    private ConnectionPOJO connectionToEdit;

    @EJB
    private ConnectionsEJB connectionsEJB;

    @EJB
    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String login = req.getParameter("login");
        if (login == null || (login.compareTo("") == 0)) {
            currentUser = usersEJB.getFakeUserInstance();
        } else {
            currentUser = usersEJB.findUserByLogin(login);
            currentUser.setConnectionList(connectionsEJB.findUserConnections(currentUser));
        }

        fakeConnection = new Connection();
        fakeConnection.setPort(22);
        fakeConnection.setUser(currentUser);
    }

    public void startAddConnection() {
        connectionToAdd = new ConnectionPOJO(fakeConnection.getConnectionId(),
                fakeConnection.getUser(),
                fakeConnection.getLogin(),
                fakeConnection.getPassword(),
                fakeConnection.getHostName(),
                fakeConnection.getPort());
    }

    public void applyAddConnection() {
        Connection connection = new Connection();
        connection.setConnectionId(connectionToAdd.getConnectionId());
        connection.setUser(connectionToAdd.getUser());
        connection.setLogin(connectionToAdd.getLogin());
        connection.setPassword(connectionToAdd.getPassword());
        connection.setHostName(connectionToAdd.getHostName());
        connection.setPort(connectionToAdd.getPort());
        connectionsEJB.addNew(connection);
        currentUser.setConnectionList(connectionsEJB.findUserConnections(currentUser));
        connectionToAdd = null;
    }

    public void deleteConnection(Connection connection) {
        connectionsEJB.delete(connection);
        currentUser.setConnectionList(connectionsEJB.findUserConnections(currentUser));
    }

    public void startEditConnection(Connection connection) {
        connectionToEdit = new ConnectionPOJO(connection.getConnectionId(),
                connection.getUser(),
                connection.getLogin(),
                connection.getPassword(),
                connection.getHostName(),
                connection.getPort());
    }

    public void applyEditConnection() {
        Connection connection = new Connection();
        connection.setConnectionId(connectionToEdit.getConnectionId());
        connection.setUser(connectionToEdit.getUser());
        connection.setLogin(connectionToEdit.getLogin());
        connection.setPassword(connectionToEdit.getPassword());
        connection.setHostName(connectionToEdit.getHostName());
        connection.setPort(connectionToEdit.getPort());
        connectionsEJB.save(connection);

        currentUser.setConnectionList(connectionsEJB.findUserConnections(currentUser));
        connectionToEdit = null;
    }

    public ConnectionPOJO getConnectionToAdd() {
        return connectionToAdd;
    }

    public void setConnectionToAdd(ConnectionPOJO connectionToAdd) {
        this.connectionToAdd = connectionToAdd;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ConnectionPOJO getConnectionToEdit() {
        return connectionToEdit;
    }

    public void setConnectionToEdit(ConnectionPOJO connectionToEdit) {
        this.connectionToEdit = connectionToEdit;
    }
}
