package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.UsersEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@ManagedBean
@ViewScoped
public class ProfileController {
    private User currentUser;

    private String avatarSex;
    private String avatarColor;

    @EJB
    private UsersEJB usersEJB;

    @PostConstruct
    public void postConstruct() {
        String login = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login");
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
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    public void setAvatar() {
        Map<String, String> manMap = new LinkedHashMap<>();
        manMap.put("Blond", "resources/img/man_blond.jpg");
        manMap.put("Ginger", "resources/img/man_ginger.jpg");
        manMap.put("Brunet", "resources/img/man_brunet.jpg");

        Map<String, String> womanMap = new LinkedHashMap<>();
        womanMap.put("Blond", "resources/img/woman_blond.jpg");
        womanMap.put("Ginger", "resources/img/woman_ginger.jpg");
        womanMap.put("Brunet", "resources/img/woman_brunet.jpg");

        Map<String, Map<String, String>> outerMap = new LinkedHashMap<>();
        outerMap.put("Male", manMap);
        outerMap.put("Female", womanMap);

        if (avatarSex == null || avatarSex.isEmpty() || avatarColor == null || avatarColor.isEmpty()) {
            currentUser.setAvatar("resources/img/standard.jpg");
        } else {
            currentUser.setAvatar(outerMap.get(avatarSex).get(avatarColor));
        }
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getAvatarSex() {
        return avatarSex;
    }

    public void setAvatarSex(String avatarSex) {
        this.avatarSex = avatarSex;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }
}