/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicaphp;

import java.util.List;

/**
 *
 * @author DELL
 */
public interface CrudRepository <T>{
    List<T> getAll();
    boolean create(T entity);
    boolean update (T entity);
    boolean delete(String ID);
    
}
