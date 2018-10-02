/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author s26-d
 */

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author s26-d
 */
public class HibernateUtil 
{ 
    private static SessionFactory sessionFactory = null; 
    static 
    { 
        try
        { //creates the session factory from hibernate.cfg.xml
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory(); 
        } catch (Exception e)
        {
            e.printStackTrace(); 
        } 
    }
    public static SessionFactory getSessionFactory() 
    { return sessionFactory; } 
}
