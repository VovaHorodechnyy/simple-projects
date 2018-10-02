/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author s26-d
 */
public class CategoregyDaoImpl implements CategoregyDao {

    @Override
    public void addCategoregy(Categoregy cat) throws SQLException {
       Session session = null;
       try
        {
                session = (Session) HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(cat); //update 
                session.getTransaction().commit(); 
        }catch(Exception ex)
        {
        
        }
       finally
       {
           if (session != null && session.isOpen()) 
           {session.close(); }

       }   
    }

    @Override
    public void updateCategoregy(Categoregy cat) throws SQLException {
        Session session = null;
        try 
        { 
            session = (Session) HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(cat);
            
            session.getTransaction().commit();
        } catch (Exception e) 
        { }
        finally 
        { 
            if (session != null && session.isOpen()) 
            { session.close(); } 
        }  
    }   

    @Override
    public Categoregy getCategoregyById(Long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getAllCategoregies() throws SQLException {
        Session session = null;
        List<Categoregy> studs = new ArrayList<Categoregy>();
        try 
        { 
            session = (Session) HibernateUtil.getSessionFactory().openSession();
            studs = session.createCriteria(Categoregy.class).list();
        }catch (Exception e) 
        {
        
        }
        finally
        { 
            if (session != null && session.isOpen()) 
            { session.close(); } 
        }
                return studs;      
    }

    @Override
    public void deleteCategoregy(Categoregy cat) throws SQLException {
        Session session = null;
        try 
        { 
            session = (Session) HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(cat);
            
            session.getTransaction().commit();
        } catch (Exception e) 
        { }
        finally 
        { 
            if (session != null && session.isOpen()) 
            { session.close(); } 
        } 
    }
    
}
