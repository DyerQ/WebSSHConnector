package com.netcracker.edu.bestgroup.projects.ssh.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class StubButtonController {
    private List<String> values = new ArrayList<>();

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void printHello(ActionEvent actionEvent) {
        values.add("Hello");
    }
}
