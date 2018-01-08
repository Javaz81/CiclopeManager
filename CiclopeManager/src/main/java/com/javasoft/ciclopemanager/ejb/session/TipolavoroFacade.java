/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.ciclopemanager.ejb.session;

import com.javasoft.ciclopemanager.ejb.entities.Tipolavoro;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrea
 */
@Stateless
public class TipolavoroFacade extends AbstractFacade<Tipolavoro> {

    @PersistenceContext(unitName = "com.javasoft_CiclopeManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipolavoroFacade() {
        super(Tipolavoro.class);
    }
    
}
