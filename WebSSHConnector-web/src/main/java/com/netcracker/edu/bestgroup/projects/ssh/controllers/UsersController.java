package com.netcracker.edu.bestgroup.projects.ssh.controllers;


import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Users;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class UsersController {

    private List<Users> usersList;
    private Users user = new Users();
    @EJB
    private UsersEJB usersEJB;
    boolean isLoginPage = (FacesContext.getCurrentInstance().getViewRoot()
            .getViewId().lastIndexOf("login.xhtml") > -1);

    @PostConstruct
    public void postConstruct() {
        usersList = usersEJB.findUsers();
    }
    public String addNewUser(){
        user = usersEJB.addNew(user);
        usersList  = usersEJB.findUsers();
        return "/test/testcrud.xhtml";
    }

    public String login() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        usersList = usersEJB.findUsers();
        List <Users> tmp = new ArrayList<Users>();
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
            return "success";
        } else {
            return "invalid";
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        FacesContext
                .getCurrentInstance()
                .getApplication()
                .getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null,
                        "/login.xhtml");
        return "login";
    }

    public String deleteUser(Users user){
        usersEJB.delete(user);
        usersList=usersEJB.findUsers();
        return "/test/testcrud.xhtml";
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void saveRow(RowEditEvent event) {
        Users editedUser = ((Users) event.getObject());
        usersEJB.save(editedUser);
    }
}
