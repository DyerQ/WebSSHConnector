package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ManagedBean
@ViewScoped
public class ProfileController {
    private User currentUser;

    @EJB
    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String login = req.getParameter("login");
        currentUser = usersEJB.findUserByLogin(login);

    }

    public void updateUserProfile() throws IOException {
        usersEJB.save(currentUser);
        FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
    }

    public void deleteUserProfile() throws IOException {
        usersEJB.delete(currentUser);
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}