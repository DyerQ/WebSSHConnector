package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class UsersController {

    private List<User> usersList;
    private User user = new User();
    @EJB
    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {
        usersList = usersEJB.findUsers();
    }

    public String addNewUser() {
        user = usersEJB.addNew(user);
        usersList = usersEJB.findUsers();
        return "test/testcrud.xhtml";
    }

    public String deleteUser(User user) {
        usersEJB.delete(user);
        usersList = usersEJB.findUsers();
        return "test/testcrud.xhtml";
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void saveRow(RowEditEvent event) {
        User editedUser = ((User) event.getObject());
        usersEJB.save(editedUser);
    }
}
