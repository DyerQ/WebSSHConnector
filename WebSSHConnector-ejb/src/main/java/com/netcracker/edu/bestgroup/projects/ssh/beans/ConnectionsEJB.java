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
// FIXME rename to ConnectionsDAO?
public class ConnectionsEJB {

    @PersistenceContext(unitName = "appPersistenceUnit")
    private EntityManager entityManager;

    // FIXME is needed? See @findUserConnections
    @EJB
    private UsersEJB usersEJB;

    public void addNew(Connection connection) {
        entityManager.persist(connection);
    }

    public void delete(Connection connection) {
        if (entityManager.contains(connection)) {
            entityManager.remove(connection);
        } else {
            entityManager.remove(entityManager.merge(connection));
        }
    }

    public List<Connection> findUserConnections(User userClone) {
        // FIXME doubtful
        User user = usersEJB.findUserByLogin(userClone.getLogin());
        @SuppressWarnings("unchecked")
        TypedQuery<Connection> query = (TypedQuery<Connection>) entityManager.createQuery("SELECT c FROM Connection c WHERE c.user.userId = :userId");
        return query.setParameter("userId", user.getUserId()).getResultList();
    }

    public void save(Connection connection) {
        entityManager.merge(connection);
    }
}
