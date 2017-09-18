package com.invictigen.testhibernateexception;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    EntityManager entityManager;

    @Before
    public void setup(){

        testQuery();
        testQueryCount();
    }



    @Transactional
    public void testQuery(){

        hasNamedQuery(entityManager, "Person.findForName");
    }


    @Transactional
    public void testQueryCount(){

        hasNamedQuery(entityManager, "Person.findForNameCount");
    }


    @org.junit.Test
    public void testQueryCountNoTransaction(){

        hasNamedQuery(entityManager, "Person.findForNameCount");
    }


    private static boolean hasNamedQuery(EntityManager em, String queryName) {

		/*
		 * See DATAJPA-617, we have to use a dedicated em for the lookups to avoid a
		 * potential rollback of the running tx.
		 */
        EntityManager lookupEm = em.getEntityManagerFactory().createEntityManager();

        try {
            lookupEm.createNamedQuery(queryName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } finally {
            lookupEm.close();
        }
    }
}
