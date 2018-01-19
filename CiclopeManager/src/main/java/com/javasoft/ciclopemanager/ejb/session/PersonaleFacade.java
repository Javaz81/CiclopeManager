/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.ciclopemanager.ejb.session;

import com.javasoft.ciclopemanager.ejb.entities.Personale;
import com.javasoft.ciclopemanager.ejb.session.exception.FacadeException;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrea
 */
@Stateless
public class PersonaleFacade extends AbstractFacade<Personale> {

    @PersistenceContext(unitName = "com.javasoft_CiclopeManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaleFacade() {
        super(Personale.class);
    }

    @Override
    public boolean isIdChanged(Personale entity, Personale other) {
        return !(entity.getId().equals(other.getId()));
    }
    
    //This "edit" method prevent ID entity overwriting the other one.
    public void edit(Personale entity, Personale beforeEdit) throws FacadeException {
        //check if id is changed
        if (this.isIdChanged(entity, beforeEdit)) {
            List result
                    = em.createNamedQuery("Personale.findById")
                            .setParameter("id", entity.getIdPersonale())
                            .getResultList();
            //if an item with the id is present yet, then fails... 
            if (!result.isEmpty()) {
                throw new FacadeException(ResourceBundle.getBundle("/Bundle")
                        .getString("IdNotUpdatable"));
            }else{
                //Remove item and recreate it with new id
                Object newId = entity.getId();
                entity.setId(beforeEdit.getId());
                super.remove(entity);
                Personale newItem = entity.deepClone();
                newItem.setId(newId);
                super.create(newItem);
            }
        } else {
            //otherwise try merging changed entity.
            super.edit(entity);
        }
    }

}
