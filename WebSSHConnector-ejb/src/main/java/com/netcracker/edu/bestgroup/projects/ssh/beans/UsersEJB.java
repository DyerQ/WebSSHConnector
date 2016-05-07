package com.netcracker.edu.bestgroup.projects.ssh.beans;

import com.netcracker.edu.bestgroup.projects.ssh.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

@Stateless
public class UsersEJB {
    @PersistenceContext(unitName = "appPersistenceUnit")
    private EntityManager entityManager;

    public User addNew(User user) {
        entityManager.persist(user);
        return user;
    }

    public User delete(User user) {
        User toBeRemoved = entityManager.merge(user);
        entityManager.remove(toBeRemoved);
        return user;
    }

    public List<User> findUsers() {
        @SuppressWarnings("unchecked")
        TypedQuery<User> query = (TypedQuery<User>) entityManager.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }

    public User findUserByLogin(String login) {
        @SuppressWarnings("unchecked")
        TypedQuery<User> query = (TypedQuery<User>) entityManager.createQuery("SELECT c FROM User c WHERE c.login = :login");
        return query.setParameter("login", login).getSingleResult();
    }

    public void save(User user) {
        entityManager.merge(user);
    }

    public User getFakeUserInstance() {
        User user = new User();
        user.setUserId(BigInteger.ZERO);
        return user;
    }
}
