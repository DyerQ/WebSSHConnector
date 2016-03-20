package com.netcracker.edu.bestgroup.projects.ssh.controllers;


import com.netcracker.edu.bestgroup.projects.ssh.beans.ConnectionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connections;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class ConnetctionsController {

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

    private List<Connections> connectionList;
    private Connections connections = new Connections();
    @EJB
    private ConnectionsEJB connectionsEJB;

    @PostConstruct
    public void postConstruct() {
        connectionList = connectionsEJB.findConnections();
    }

    public String addNewConnection(){
        connections = connectionsEJB.addNew(connections);
        connectionList  = connectionsEJB.findConnections();
        return "/test/connections.xhtml";
    }

    public String deleteConnection(Connections user){
        connectionsEJB.delete(user);
        connectionList=connectionsEJB.findConnections();
        return "/test/connections.xhtml";
    }

    public void saveRow(RowEditEvent event) {
        Connections editedUser = ((Connections) event.getObject());
        connectionsEJB.save(editedUser);
    }
}
