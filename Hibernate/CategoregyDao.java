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
public interface CategoregyDao 
{
    public void addCategoregy(Categoregy student) throws SQLException; 
    public void updateCategoregy(Categoregy student) throws SQLException; 
    public Categoregy getCategoregyById(Long id) throws SQLException; 
    public List getAllCategoregies() throws SQLException; 
    public void deleteCategoregy(Categoregy student) throws SQLException; 
    
}
