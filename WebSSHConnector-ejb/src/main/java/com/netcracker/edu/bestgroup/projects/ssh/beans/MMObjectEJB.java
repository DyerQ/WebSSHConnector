package com.netcracker.edu.bestgroup.projects.ssh.beans;

import com.netcracker.edu.bestgroup.projects.ssh.entities.MMObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MMObjectEJB {
    private static final Logger LOGGER = LoggerFactory.getLogger(MMObjectEJB.class);

    @PersistenceContext(unitName = "vasiliyPersistenceUnit")
    private EntityManager entityManager;

    public List<MMObject> findMMObjects() {
        @SuppressWarnings("unchecked")
        TypedQuery<MMObject> query = (TypedQuery<MMObject>) entityManager.createNamedQuery("MMObjects.findAll");
        List<MMObject> resultList = query.getResultList();
        int size = resultList != null ? resultList.size() : 0;
        MMObject firstResult = resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
        LOGGER.debug("Named query finder resolved query with {} results, first result is {}", size, firstResult);
        return resultList;
    }

    public MMObject addNew(MMObject mmObject) {
        entityManager.persist(mmObject);
        return mmObject;
    }

    public void save(MMObject mmObject) {
        entityManager.merge(mmObject);
    }
}
