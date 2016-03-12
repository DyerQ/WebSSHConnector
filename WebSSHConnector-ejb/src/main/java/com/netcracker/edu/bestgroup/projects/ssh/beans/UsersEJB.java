package com.netcracker.edu.bestgroup.projects.ssh.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "UsersEJB")
public class UsersEJB {
    @PersistenceContext(unitName = "WebSSHConnect_USERS")
    private EntityManager entityManager;

    public UsersEJB() {
    }

}
