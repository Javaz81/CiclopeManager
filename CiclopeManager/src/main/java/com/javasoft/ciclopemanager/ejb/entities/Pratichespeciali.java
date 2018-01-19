/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.ciclopemanager.ejb.entities;

import com.javasoft.ciclopemanager.ejb.entities.util.DeepClonable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrea
 */
@Entity
@Table(name = "pratichespeciali")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pratichespeciali.findAll", query = "SELECT p FROM Pratichespeciali p")
    , @NamedQuery(name = "Pratichespeciali.findById", query = "SELECT p FROM Pratichespeciali p WHERE p.idPraticaSpeciale = :id")
    , @NamedQuery(name = "Pratichespeciali.findByDescrizione", query = "SELECT p FROM Pratichespeciali p WHERE p.descrizione = :descrizione")})
public class Pratichespeciali implements Serializable, DeepClonable<Pratichespeciali> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPraticaSpeciale")
    private Integer idPraticaSpeciale;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descrizione")
    private String descrizione;

    public Pratichespeciali() {
    }

    public Pratichespeciali(Integer idPraticaSpeciale) {
        this.idPraticaSpeciale = idPraticaSpeciale;
    }

    public Pratichespeciali(Integer idPraticaSpeciale, String descrizione) {
        this.idPraticaSpeciale = idPraticaSpeciale;
        this.descrizione = descrizione;
    }

    public Integer getIdPraticaSpeciale() {
        return idPraticaSpeciale;
    }

    public void setIdPraticaSpeciale(Integer idPraticaSpeciale) {
        this.idPraticaSpeciale = idPraticaSpeciale;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPraticaSpeciale != null ? idPraticaSpeciale.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pratichespeciali)) {
            return false;
        }
        Pratichespeciali other = (Pratichespeciali) object;
        if ((this.idPraticaSpeciale == null && other.idPraticaSpeciale != null) || (this.idPraticaSpeciale != null && !this.idPraticaSpeciale.equals(other.idPraticaSpeciale))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descrizione;
    }

    @Override
    public Pratichespeciali deepClone() {
        Pratichespeciali result = new Pratichespeciali();
        result.descrizione = this.descrizione;
        result.idPraticaSpeciale = this.idPraticaSpeciale;
        return result;
    }

    @Override
    public void restoreFromClone(Pratichespeciali clone) {
        this.descrizione = clone.descrizione;
        this.idPraticaSpeciale = clone.idPraticaSpeciale;
    }

    @Override
    public Object getId() {
        return this.idPraticaSpeciale;
    }
    
     @Override
    public void setId(Object cloneId){
        this.idPraticaSpeciale = (Integer) cloneId;
    }
}
