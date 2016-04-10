package com.netcracker.edu.bestgroup.projects.ssh.beans;

import com.netcracker.edu.bestgroup.projects.ssh.entities.Users;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.AssertFalse;
import java.util.List;

@Stateful(name = "UsersEJB")
public class UsersEJB {
    @PersistenceContext(unitName = "appPersistenceUnit")
    private EntityManager entityManager;

    public UsersEJB() {
    }
    public Users addNew(Users user){
        entityManager.persist(user);
        return user;
    }
    public Users delete(Users user){
        Users toBeRemoved = entityManager.merge(user);
        entityManager.remove(toBeRemoved);
        return user;
    }
    public List<Users> findUsers(){
        TypedQuery<Users> query = (TypedQuery<Users>) entityManager.createNamedQuery("Users.findAll");
        List<Users> resultList = query.getResultList();
        //entityManager.flush();
        return resultList;
    }

    public Users findUserByLogin(String login){
        @SuppressWarnings("unchecked")
        TypedQuery<Users> query = (TypedQuery<Users>)entityManager.createQuery("SELECT c FROM Users c WHERE c.login = :login");
        return query.setParameter("login", login).getSingleResult();
    }

    public void save(Users user) {
        entityManager.merge(user);
    }
}
