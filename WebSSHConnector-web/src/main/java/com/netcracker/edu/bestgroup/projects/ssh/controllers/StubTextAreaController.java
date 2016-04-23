package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped

public class StubTextAreaController {
    private List<String> data = new ArrayList<>();
    private String inputString = new String();

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public List<String> getData() {
        return data;
    }

    public void addToData(){
        this.data.add(this.inputString);
        this.inputString = null;

    }
}
