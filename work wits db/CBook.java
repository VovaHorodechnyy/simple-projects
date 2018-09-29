/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author Volodya
 */
public class CBook {
    
    public int id;
    public String name;
    public String desc;
    
    public CBook()
    {
    
    }
    public CBook(int id,String name,String desc)
    {
        this.id=id;
        this.desc=desc;
        this.name=name;
    }
    
}