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

    @PostConstruct
    public void postConstruct() {
        usersList = usersEJB.findUsers();
    }
    public String addNewUser(){
        user = usersEJB.addNew(user);
        usersList  = usersEJB.findUsers();
        return "/test/testcrud.xhtml";
    }

    public void registerUser(){
        user = usersEJB.addNew(user);
        usersList  = usersEJB.findUsers();
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
