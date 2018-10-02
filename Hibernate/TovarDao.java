/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author s26-d
 */
public interface TovarDao 
{
    public void addTovar(Tovar tovar) throws SQLException; 
    public void updateTovar(Tovar tovar) throws SQLException; 
    public Categoregy getTovarById(Long id) throws SQLException; 
    public List getAllTovars() throws SQLException; 
    public void deleteTovar(Tovar tovar) throws SQLException; 
}
