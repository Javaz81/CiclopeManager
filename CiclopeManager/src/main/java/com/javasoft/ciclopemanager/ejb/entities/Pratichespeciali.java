/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.ciclopemanager.ejb.entities;

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
    , @NamedQuery(name = "Pratichespeciali.findByIdPraticaSpeciale", query = "SELECT p FROM Pratichespeciali p WHERE p.idPraticaSpeciale = :idPraticaSpeciale")
    , @NamedQuery(name = "Pratichespeciali.findByDescrizione", query = "SELECT p FROM Pratichespeciali p WHERE p.descrizione = :descrizione")})
public class Pratichespeciali implements Serializable {

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
        return "com.javasoft.ciclopemanager.ejb.entities.Pratichespeciali[ idPraticaSpeciale=" + idPraticaSpeciale + " ]";
    }
    
}
