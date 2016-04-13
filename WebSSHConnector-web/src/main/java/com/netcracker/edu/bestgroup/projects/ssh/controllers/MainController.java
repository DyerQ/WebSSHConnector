package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Users;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class MainController {
    boolean isLoginPage = (FacesContext.getCurrentInstance().getViewRoot()
            .getViewId().lastIndexOf("login.xhtml") > -1);
    private Users user = new Users();
    boolean dbl = true;
    boolean loggedIn = false;
    @EJB
    private UsersEJB usersEJB;


    public void connect() {

    }

    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        List<Users> usersList = usersEJB.findUsers();
        List<Users> tmp = new ArrayList<Users>();
        for (Users e: usersList){
            if(e.getLogin().equals(user.getLogin())){
                tmp.add(e);
            }
        }

        if((!tmp.isEmpty())) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", user.getLogin());
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }

/*    public String login1() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        List<Users> usersList = usersEJB.findUsers();
        List<Users> tmp = new ArrayList<Users>();
        for (Users e: usersList){
            if(e.getLogin().equals(user.getLogin())){
                tmp.add(e);
            }
        }
        if (isLoginPage && (!tmp.isEmpty())) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("username", user.getLogin());
            if (session == null) {
                FacesContext
                        .getCurrentInstance()
                        .getApplication()
                        .getNavigationHandler()
                        .handleNavigation(FacesContext.getCurrentInstance(),
                                null, "/login.xhtml");
            } else {
                Object currentUser = session.getAttribute("name");
                if (!isLoginPage && (currentUser == null || currentUser == "")) {
                    FacesContext
                            .getCurrentInstance()
                            .getApplication()
                            .getNavigationHandler()
                            .handleNavigation(
                                    FacesContext.getCurrentInstance(), null,
                                    "/login.xhtml");
                }
            }
            isLoggedOn = true;
            return "success";
        } else {
            return "error";
        }
    }*/


    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        FacesContext
                .getCurrentInstance()
                .getApplication()
                .getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null,
                        "/login.xhtml");
        loggedIn = false;
        return "login";
    }

    public Users getUser() {
        return user;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public boolean getDbl() {
        return dbl;
    }
}
