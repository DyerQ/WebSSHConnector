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

@ManagedBean
@SessionScoped
public class MainController {
    private User user = new User();
    boolean loggedIn = false;

    public boolean isProfilePage() {
        isProfilePage = (loggedIn) & (FacesContext.getCurrentInstance().getViewRoot()
                .getViewId().lastIndexOf("profile.xhtml") > -1);
        return isProfilePage;
    }

    boolean isProfilePage = false;

    public boolean getRegisterSuccess() {
        return registerSuccess;
    }

    boolean registerSuccess = false;

    @EJB
    private UsersEJB usersEJB;

    public void login(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        User tmp_User = usersEJB.findUserByLogin(user.getLogin());

        if (tmp_User != null && tmp_User.getPassword().equals(user.getPassword())) {
            loggedIn = true;
            user = usersEJB.findUserByLogin(user.getLogin());
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("username", user.getUserName());
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("login", user.getLogin());
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Welcome", user.getLogin());
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Logging Error", "Invalid credentials");
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
        }
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
        if (user.getUserName() == null || user.getLogin() == null || user.getPassword() == null) {
            registerSuccess = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Registration Error", "Invalid credentials");
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("RegisterSuccess", registerSuccess);
        } else {
            registerSuccess = true;
            usersEJB.addNew(user);
            login(event);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            context.addCallbackParam("RegisterSuccess", registerSuccess);
        }
    }
}
