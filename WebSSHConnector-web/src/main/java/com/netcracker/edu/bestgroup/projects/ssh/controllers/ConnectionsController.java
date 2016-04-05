package com.netcracker.edu.bestgroup.projects.ssh.controllers;


import com.netcracker.edu.bestgroup.projects.ssh.beans.ConnectionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connections;
import org.primefaces.event.RowEditEvent;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ConnectionsController {


    public ConnectionsController() {

    }

    private String login;
    private List<Connections> connectionList;
    private Connections connections = new Connections();
    @EJB
    private ConnectionsEJB connectionsEJB;
    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {


        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        login = req.getParameter("user");
        if (login == null) {
            String message = "Bad request.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }
        connectionList = connectionsEJB.findUserConnections(login);

//
//        connectionList = new ArrayList<>();
//        connectionList.add(new Connections(new BigInteger(String.valueOf(1)),"q",11,"q","q","q",new BigInteger(String.valueOf(11))));
//        connectionList.add(new Connections(new BigInteger(String.valueOf(1)),"q",11,"q","q","q",new BigInteger(String.valueOf(11))));
//        connectionList.add(new Connections(new BigInteger(String.valueOf(1)),login,11,"q","q","q",new BigInteger(String.valueOf(11))));


    }

//    public void prerender() {
//        EventBus eventBus = EventBusFactory.getDefault().eventBus();
//        eventBus.publish("/setlogin", login);
//    }

//    public void findConnections(){
//        connectionList = connectionsEJB.findUserConnections(login);
//        if (login == null) {
//            String message = "Bad request.";
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
//            return;
//        }
//
////        connectionList = connectionsEJB.findUserConnections(login);
//        connectionList = new ArrayList<>();
//        connectionList.add(new Connections(new BigInteger(String.valueOf(1)),"q",11,"q","q","q",new BigInteger(String.valueOf(11))));
//        connectionList.add(new Connections(new BigInteger(String.valueOf(1)),"q",11,"q","q","q",new BigInteger(String.valueOf(11))));
//        connectionList.add(new Connections(new BigInteger(String.valueOf(1)),login,11,"q","q","q",new BigInteger(String.valueOf(11))));
//
////        return "/test/connections.xhtml";
//
//    }

    public String addNewConnection(){
        connections = connectionsEJB.addNew(connections,login);
//        connectionList = connectionsEJB.findConnections();
        connectionList  = connectionsEJB.findUserConnections(login);
        return "/test/connections.xhtml";
    }
//    public String addTestConnection(){
//        Connections connections = new Connections("q",11,"q","q","q",usersEJB.findUserByLogin("q"));
//        connectionsEJB.addNew(connections,login);
//        return "/test/connections.xhtml";
//    }

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
