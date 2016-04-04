package com.netcracker.edu.bestgroup.projects.ssh.controllers;


import com.netcracker.edu.bestgroup.projects.ssh.beans.ConnectionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connections;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Users;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import java.util.List;

@ManagedBean
@ViewScoped
public class ConnetctionsController {



    private String login;
    private List<Connections> connectionList;
    private Connections connections = new Connections();
    @EJB
    private ConnectionsEJB connectionsEJB;
    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {

        connectionList = connectionsEJB.findConnections();
        //connections.setUser(usersEJB.findUserByLogin(login));
    }


    public String addNewConnection(){
        connections = connectionsEJB.addNew(connections,login);
        connectionList = connectionsEJB.findConnections();
//        connectionList  = connectionsEJB.findUserConnections(login);
        return "/test/connections.xhtml";
    }
    public String addTestConnection(){
        Connections connections = new Connections("q",11,"q","q","q",usersEJB.findUserByLogin("q"));
        connectionsEJB.addNew(connections,login);
        return "/test/connections.xhtml";
    }

    public String deleteConnection(Connections user){
        connectionsEJB.delete(user);
        connectionList=connectionsEJB.findUserConnections(login);
        return "/test/connections.xhtml";
    }

    public void saveRow(RowEditEvent event) {
        Connections editedUser = ((Connections) event.getObject());
        connectionsEJB.save(editedUser);
    }




    public List<Connections> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connections> connectionList) {
        this.connectionList = connectionList;
    }

    public Connections getConnections() {
        return connections;
    }

    public void setConnections(Connections connections) {
        this.connections = connections;
    }

    public ConnectionsEJB getConnectionsEJB() {
        return connectionsEJB;
    }

    public void setConnectionsEJB(ConnectionsEJB connectionsEJB) {
        this.connectionsEJB = connectionsEJB;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
