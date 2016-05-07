package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;
import org.primefaces.context.RequestContext;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class MainController {
    private User user = new User();
    boolean loggedIn = false;

    @EJB
    private UsersEJB usersEJB;

    public String login(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        List<User> usersList = usersEJB.findUsers();
        List<User> tmp = new ArrayList<>();
        for (User e : usersList) {
            if (e.getLogin().equals(user.getLogin()) && e.getPassword().equals(user.getPassword())) {
                tmp.add(e);
            }
        }

        if ((!tmp.isEmpty())) {
            loggedIn = true;
            user = usersEJB.findUserByLogin(user.getLogin());
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("username", user.getUserName());
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Welcome", user.getLogin());
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("test/connections.xhtml?user=" + user.getLogin() + "&faces-redirect=true");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Logging Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
        return "test/connections.xhtml?user=" + user.getLogin() + "&faces-redirect=true";
    }

    public void logout() {

        loggedIn = false;
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        loggedIn = false;
    }

    public User getUser() {
        return user;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void connect() {

    }

    public String registerUser(ActionEvent event) {
        usersEJB.addNew(user);
        login(event);
        return "main.xhtml?user" + user.getLogin() + "&faces-redirect=true";
    }
}
