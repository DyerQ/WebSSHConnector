package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.MMObjectEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.MMObject;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean(name = "mmObjectController")
@RequestScoped
public class MMObjectController {

    @EJB
    private MMObjectEJB mmObjectEJB;

    private MMObject mmObject = new MMObject();

    private List<MMObject> mmObjectList;

    @PostConstruct
    private void postConstruct() {
        mmObjectList = mmObjectEJB.findMMObjects();
    }

    public String addNewMMObject() {
        mmObject = mmObjectEJB.addNew(mmObject);
        mmObjectList = mmObjectEJB.findMMObjects();
        return "/test/mmobjects.xhtml";
    }

    public MMObject getMmObject() {
        return mmObject;
    }

    public List<MMObject> getMmObjectList() {
        return mmObjectList;
    }


    public void setMmObject(MMObject mmObject) {
        this.mmObject = mmObject;
    }

    public void setMmObjectList(List<MMObject> mmObjectList) {
        this.mmObjectList = mmObjectList;
    }
}