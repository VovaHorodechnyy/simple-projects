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
public class TovarDaoImpl implements TovarDao
{

    @Override
    public void addTovar(Tovar tovar) throws SQLException {
      
       Session session = null;
       try
        {
                session = (Session) HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(tovar); //update 
                session.getTransaction().commit(); 
        }
       catch(Exception ex)
        {
        
        }
       finally
       {
           if (session != null && session.isOpen()) 
           {session.close(); }

       }   
    }

    @Override
    public void updateTovar(Tovar tovar) throws SQLException {
        Session session = null;
        try 
        { 
            session = (Session) HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(tovar);
            
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
    public Categoregy getTovarById(Long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getAllTovars() throws SQLException {
     Session session = null;
        List<Tovar> studs = new ArrayList<Tovar>();
        try 
        { 
            session = (Session) HibernateUtil.getSessionFactory().openSession();
            studs = session.createCriteria(Tovar.class).list();
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
    public void deleteTovar(Tovar tovar) throws SQLException {
        Session session = null;
        try 
        { 
            session = (Session) HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(tovar);
            
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
