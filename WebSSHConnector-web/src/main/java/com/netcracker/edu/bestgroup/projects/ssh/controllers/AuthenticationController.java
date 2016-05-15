package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class AuthenticationController {

    private User user = new User();

    public boolean isProfilePage() {
        return FacesContext.getCurrentInstance().getViewRoot()
                .getViewId().lastIndexOf("profile.xhtml") > -1;
    }

    @EJB
    private UsersEJB usersEJB;

    public String login() {
        // FIXME single fucking user please
        List<User> usersList = usersEJB.findUsers();
        List<User> tmp = new ArrayList<>();
        for (User e : usersList) {
            if (e.getLogin().equals(user.getLogin()) && e.getPassword().equals(user.getPassword())) {
                tmp.add(e);
            }
        }

        if ((!tmp.isEmpty())) {
            user = usersEJB.findUserByLogin(user.getLogin());
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("login", user.getLogin());
            return "main.xhtml?faces-redirect=true";
        }
        // FIXME throw an exception or smth to let know that login or password are incorrect
        throw new RuntimeException("login or password are incorrect");
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        return "main.xhtml?faces-redirect=true";
    }

    public String registerUser() {
        usersEJB.addNew(user);
        login();
        return "main.xhtml?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().containsKey("login");
    }
}
