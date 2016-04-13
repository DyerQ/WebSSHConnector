package com.netcracker.edu.bestgroup.projects.ssh.controllers;


import com.netcracker.edu.bestgroup.projects.ssh.beans.ConnectionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connections;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

@ManagedBean
@ViewScoped
public class ConnectionsController {


    public ConnectionsController() {

    }

    private String login;



    private BigInteger id;
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
        if (login == null){
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage("INFO",  "LOGIN IS NULL") );
        }
        id = usersEJB.findUserByLogin(login).getId();


        connectionList = connectionsEJB.findUserConnections(login);
        connections.setUser_id(id);

    }


    public String addNewConnection(){
        connections = connectionsEJB.addNew(connections,login);
//        connectionList = connectionsEJB.findConnections();
        connectionList  = connectionsEJB.findUserConnections(login);
//        RequestContext.getCurrentInstance().update(":content:mmo_table");
        return "/test/connections.xhtml?user="+login+"&faces-redirect=true";
    }


    public String deleteConnection(Connections user){
        connectionsEJB.delete(user);
        connectionList=connectionsEJB.findUserConnections(login);
        return "/test/connections.xhtml?user="+user.getLogin()+"&faces-redirect=true";
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
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
