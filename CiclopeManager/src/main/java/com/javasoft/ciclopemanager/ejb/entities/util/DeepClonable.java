/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.ciclopemanager.ejb.entities.util;

/**
 *
 * @author andrea
 * @param <T>
 */
public interface DeepClonable<T extends Object> {
    T deepClone();
    void restoreFromClone(T clone);
    Object getId();
}
