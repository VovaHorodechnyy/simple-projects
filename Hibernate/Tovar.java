/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author s26-d
 */
@Entity
@Table(name="Tovar")

public class Tovar implements java.io.Serializable 
{
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(name="Name")
    private String name;
    
    @Column(name="Price")
    private Integer price;
    
    @ManyToOne
    @JoinColumn(name="idCategoregy")
    private Categoregy cat;
    
    
    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public int getPrice()
    {
        return price;
    }
    public Categoregy getCat()
    {
        return cat;
    }
    
    public void setId(int id)
    {
        this.id=id;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public void setPrice(int price)
    {
        this.price=price;
    }
    public void setCat(Categoregy cat)
    {
        this.cat=cat;
    }
    
}
