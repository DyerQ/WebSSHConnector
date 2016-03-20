package com.netcracker.edu.bestgroup.projects.ssh.beans;

import com.netcracker.edu.bestgroup.projects.ssh.entities.Connections;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless(name = "ConnectionsEJB")
public class ConnectionsEJB {
    @PersistenceContext(unitName = "appPersistenceUnit")
    private EntityManager entityManager;
    public ConnectionsEJB() {
    }

    public Connections addNew(Connections connections){
        entityManager.persist(connections);
        return connections;
    }
    public Connections delete(Connections connections){
        Connections toBeRemoved = entityManager.merge(connections);
        entityManager.remove(toBeRemoved);
        return connections;
    }

    public List<Connections> findConnections(){
        @SuppressWarnings("unchecked")
        TypedQuery<Connections> query = (TypedQuery<Connections>) entityManager.createNamedQuery("Connections.findAll");
        List<Connections> resultList = query.getResultList();
        entityManager.flush();
        return resultList;

    }
    public void save(Connections connections) {
        entityManager.merge(connections);
    }
}
