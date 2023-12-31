package com.example.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Account;

@Repository
@Transactional
public class AccountDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    @Transactional(readOnly = true)
    public Account findAccount(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(Account.class, userName);
    }

    public void save(Account account) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(account);
    }
}

