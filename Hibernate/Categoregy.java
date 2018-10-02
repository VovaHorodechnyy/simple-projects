/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author s26-d
 */
@Entity
@Table(name="Categoregy") 
public class Categoregy {
    @Id 
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment") 
    @Column(name="ID")
    public Integer getId() { return id; }
    @Column(name="Name")
    public String getName(){ return name; }
    
    private Integer id;
    private String name;
    
    public void setId(int id)
    {
        this.id=id;
    }
     public void setName(String name)
    {
        this.name=name;
    }
   
    
}
