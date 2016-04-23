package com.netcracker.edu.bestgroup.projects.ssh.beans;

import com.netcracker.edu.bestgroup.projects.ssh.entities.Connections;
import com.netcracker.edu.bestgroup.projects.ssh.entities.Users;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateful(name = "ConnectionsEJB")
public class ConnectionsEJB {
    @PersistenceContext(unitName = "appPersistenceUnit")
    private EntityManager entityManager;
    @EJB
    private UsersEJB usersEJB;
    public ConnectionsEJB() {
    }

    public Connections addNew(Connections connections, String userLogin){
//        Users users = usersEJB.findUserByLogin(userLogin);
//        connections.setUser_id(users.getId());
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
     public List<Connections> findUserConnections(String login){
         Users userTmp = usersEJB.findUserByLogin(login);
         TypedQuery<Connections> query = (TypedQuery<Connections>)entityManager.createQuery("SELECT c FROM Connections c WHERE c.user_id = :user_id");
         return query.setParameter("user_id", userTmp.getId()).getResultList();
    }
//    public List<Connections> findUserConnections(){
//        Users userTmp = usersEJB.findUserByLogin("qq");
//        TypedQuery<Connections> query = (TypedQuery<Connections>)entityManager.createQuery("SELECT c FROM Connections c WHERE c.user_id = :user_id");
//        return query.setParameter("user_id", userTmp.getId()).getResultList();
//    }
    public void save(Connections connections) {
        entityManager.merge(connections);
    }
}
