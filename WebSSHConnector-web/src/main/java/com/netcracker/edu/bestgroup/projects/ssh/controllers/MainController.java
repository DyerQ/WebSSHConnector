package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Users;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class MainController {
    private Users user = new Users();
    boolean loggedIn = false;

    private List<Users> usersList;
    @EJB
    private UsersEJB usersEJB;

    public String login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        List<Users> usersList = usersEJB.findUsers();
        List<Users> tmp = new ArrayList<Users>();
        for (Users e: usersList){
            if(e.getLogin().equals(user.getLogin()) && e.getPassword().equals(user.getPassword())){
                tmp.add(e);
            }
        }

        if((!tmp.isEmpty())) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Welcome", user.getLogin());
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Andrey/test/connections.xhtml?user="+user.getLogin()+"&faces-redirect=true");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
        return "/test/connections.xhtml?user="+"&faces-redirect=true";
    }

            /*   public String login1() {
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


    public void logout() {

        loggedIn = false;
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Andrey/index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        loggedIn = false;
    }

    public Users getUser() {
        return user;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

}
