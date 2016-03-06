package com.netcracker.edu.bestgroup.projects.ssh.beans;

import com.netcracker.edu.bestgroup.projects.ssh.entities.MMObject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MMObjectEJB {
    @PersistenceContext(unitName = "WebSSHConnect_PU")
    private EntityManager entityManager;

    public List<MMObject> findMMObjects() {
        TypedQuery<MMObject> query = (TypedQuery<MMObject>) entityManager.createNamedQuery("findAllMMObjects");
        return query.getResultList();
    }

    public MMObject addNew(MMObject mmObject) {
        entityManager.persist(mmObject);
        return mmObject;
    }
}
