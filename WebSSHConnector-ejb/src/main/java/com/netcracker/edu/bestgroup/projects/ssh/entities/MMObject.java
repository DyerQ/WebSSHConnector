package com.netcracker.edu.bestgroup.projects.ssh.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@NamedQuery(name = "findAllMMObjects", query = "select o from MMObject o")
public class MMObject implements Serializable {
    private static final long serialVersionUID = -4359441295184175462L;

    @Id
    private BigInteger id;

    private BigInteger parentId;

    private String name;

    private String description;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
