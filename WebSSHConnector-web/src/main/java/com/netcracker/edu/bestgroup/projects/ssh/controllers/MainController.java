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

   public boolean isProfilePage() {
        isProfilePage = (loggedIn)&(FacesContext.getCurrentInstance().getViewRoot()
                .getViewId().lastIndexOf("profile.xhtml") > -1);
        return isProfilePage;
    }
    boolean isProfilePage = false;

    public boolean getRegisterSuccess() {
        return RegisterSuccess;
    }

    boolean RegisterSuccess = false;

    @EJB
    private UsersEJB usersEJB;
    public String login(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        User tmp_User = usersEJB.findUserByLogin(user.getLogin());

        if (!(tmp_User.equals(null)) && tmp_User.getPassword().equals(user.getPassword())) {
            loggedIn = true;
            user = usersEJB.findUserByLogin(user.getLogin());
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("username", user.getUserName());
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("login", user.getLogin());
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Welcome", user.getLogin());
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
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

    public void registerUser(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        if(user.getUserName().equals(null)||user.getLogin().equals(null)||user.getPassword().equals(null)){
            RegisterSuccess = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Registration Error", "Invalid credentials");
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("RegisterSuccess", RegisterSuccess);
        }
        else{
            RegisterSuccess = true;
            usersEJB.addNew(user);
            login(event);
            context.addCallbackParam("RegisterSuccess", RegisterSuccess);
        }
    }
}
