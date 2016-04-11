package com.netcracker.edu.bestgroup.projects.ssh.controllers;


import com.netcracker.edu.bestgroup.projects.ssh.beans.ConnectionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connections;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ManagedBean
@ViewScoped
public class ConnectionsController {


    public ConnectionsController() {

    }

    private String login;



    private String id;
    private HtmlInputText inputComponent = new HtmlInputText();
    private List<Connections> connectionList;
    private Connections connections = new Connections();
    @EJB
    private ConnectionsEJB connectionsEJB;
    @EJB
    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {


        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        login = req.getParameter("user");
        connectionList = connectionsEJB.findUserConnections(login);
        id = usersEJB.findUserByLogin(login).getId().toString();

    }


    public String addNewConnection(){
        connections = connectionsEJB.addNew(connections,login);
//        connectionList = connectionsEJB.findConnections();
        connectionList  = connectionsEJB.findUserConnections(login);
        return "/test/connections.xhtml?user="+login+"&faces-redirect=true";
    }


    public String deleteConnection(Connections user){
        connectionsEJB.delete(user);
        connectionList=connectionsEJB.findUserConnections(login);
        return "/test/connections.xhtml?user="+login+"&faces-redirect=true";
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
