/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.ciclopemanager.ejb.session;

import com.javasoft.ciclopemanager.ejb.entities.Cliente;
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
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "com.javasoft_CiclopeManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    @Override
    public boolean isIdChanged(Cliente entity, Cliente other) {
        return !(entity.getId().equals(other.getId()));
    }
    
    //This "edit" method prevent ID entity overwriting the other one.
    public void edit(Cliente entity, Cliente beforeEdit) throws FacadeException {
        //check if id is changed
        if (this.isIdChanged(entity, beforeEdit)) {
            List result
                    = em.createNamedQuery("Cliente.findById")
                            .setParameter("id", entity.getId())
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
                Cliente newItem = entity.deepClone();
                newItem.setId(newId);
                super.create(newItem);
            }
        } else {
            //otherwise try merging changed entity.
            super.edit(entity);
        }
    }
}
