package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.MultipleSessionsEJB;
import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.InvalidConnectionParametersException;
import com.netcracker.edu.bestgroup.projects.ssh.connect.exceptions.SessionInitializationException;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

@ManagedBean
@ViewScoped
public class UserSessionsController {
    private User authorizedUser;

    private Connection openedConnection;

    private Map<Tab, Connection> openedConnections;

    private TabView tabView;

    @EJB
    private UsersEJB usersEJB;

    @EJB
    private MultipleSessionsEJB multipleSessionsEJB;

    public TabView getTabView() {
        return tabView;
    }

    public void setTabView(TabView tabView) {
        this.tabView = tabView;
    }

    @PostConstruct
    private void postConstruct() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String login = (String) sessionMap.get("login");
        if (login == null) {
            authorizedUser = usersEJB.getFakeUserInstance();
        } else {
            authorizedUser = usersEJB.findUserByLogin(login);
        }
        openedConnections = new LinkedHashMap<>();
        tabView = new TabView();
        tabView.setScrollable(true);
    }

    public boolean connect(Connection connection) {
        try {
            if (!openedConnections.containsValue(connection)) {
                if (multipleSessionsEJB.openNewSession(connection)) {
                    Tab tab = constructNewTab();
                    tab.setTitle(connection.toString());
                    tabView.getChildren().add(tab);
                    openedConnections.put(tab, connection);
                    return true;
                }
            } else {
                FacesMessage message = new FacesMessage("This connection is already active");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (InvalidConnectionParametersException | SessionInitializationException e) {
            FacesMessage message = new FacesMessage("Could not connect to " + connection,
                    "Detailed message: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return false;
    }

    private Tab constructNewTab() {
        Tab tab = new Tab();
        // TODO add dataList, inputText & button with defaultCommand for this inputText
        return tab;
    }


    public boolean closeConnection(Connection connection) {
        return multipleSessionsEJB.closeConnection(connection);
    }

    public void onTabChange(TabChangeEvent event) {
        Tab activeTab = event.getTab();
        openedConnection = openedConnections.get(activeTab);
    }

    public void onTabClose(TabCloseEvent event) {
        Tab closedTab = event.getTab();
        Connection connection = openedConnections.remove(closedTab);
        if (connection != null) {
            multipleSessionsEJB.closeConnection(connection);
        }
    }

    @PreDestroy
    private void preDestroy() {
        for (Connection openedConnection : openedConnections.values()) {
            multipleSessionsEJB.closeConnection(openedConnection);
        }
    }

    private BigInteger fakeIdCounter = BigInteger.ONE;

    // TODO remove
    public void fakeConnect() {
        connect(new Connection(fakeIdCounter, "root", "netcracker", "localhost", 22, usersEJB.getFakeUserInstance()));
    }
}
