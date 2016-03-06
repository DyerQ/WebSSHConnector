package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import com.netcracker.edu.bestgroup.projects.ssh.beans.MMObjectEJB;
import com.netcracker.edu.bestgroup.projects.ssh.entities.MMObject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class MMObjectController {

    @EJB
    private MMObjectEJB mmObjectEJB;

    private MMObject mmObject = new MMObject();

    private List<MMObject> mmObjectList = new ArrayList<>();

    public List<MMObject> getMMObjectList() {
        mmObjectList = mmObjectEJB.findMMObjects();
        return mmObjectList;
    }

    public String viewMMObject() {
        return "mmObjectList.xhtml";
    }

    public String addNewMMObject() {
        mmObject = mmObjectEJB.addNew(mmObject);
        mmObjectList = mmObjectEJB.findMMObjects();
        return "mmObjectList.xhtml";
    }

    public MMObjectEJB getMmObjectEJB() {
        return mmObjectEJB;
    }

    public MMObject getMmObject() {
        return mmObject;
    }

    public List<MMObject> getMmObjectList() {
        return mmObjectList;
    }

    public void setMmObjectEJB(MMObjectEJB mmObjectEJB) {
        this.mmObjectEJB = mmObjectEJB;
    }

    public void setMmObject(MMObject mmObject) {
        this.mmObject = mmObject;
    }

    public void setMmObjectList(List<MMObject> mmObjectList) {
        this.mmObjectList = mmObjectList;
    }
}