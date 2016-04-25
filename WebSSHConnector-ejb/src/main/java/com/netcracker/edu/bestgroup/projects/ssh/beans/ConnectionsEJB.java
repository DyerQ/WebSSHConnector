package com.netcracker.edu.bestgroup.projects.ssh.beans;

import com.netcracker.edu.bestgroup.projects.ssh.entities.Connection;
import com.netcracker.edu.bestgroup.projects.ssh.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateful
public class ConnectionsEJB {
    @PersistenceContext(unitName = "appPersistenceUnit")
    private EntityManager entityManager;

    @EJB
    private UsersEJB usersEJB;

    public ConnectionsEJB() {
    }

    public Connection addNew(Connection connection) {
        entityManager.persist(connection);
        return connection;
    }

    public Connection delete(Connection connection) {
        Connection toBeRemoved = entityManager.merge(connection);
        entityManager.remove(toBeRemoved);
        return connection;
    }

    public List<Connection> findConnections() {
        @SuppressWarnings("unchecked")
        TypedQuery<Connection> query = (TypedQuery<Connection>) entityManager.createQuery("SELECT c FROM Connection c");
        return query.getResultList();

    }

    public List<Connection> findUserConnections(String login) {
        User user = usersEJB.findUserByLogin(login);
        @SuppressWarnings("unchecked")
        TypedQuery<Connection> query = (TypedQuery<Connection>) entityManager.createQuery("SELECT c FROM Connection c WHERE c.user.userId = :userId");
        return query.setParameter("userId", user.getUserId()).getResultList();
    }

    public void save(Connection connections) {
        entityManager.merge(connections);
    }
}
