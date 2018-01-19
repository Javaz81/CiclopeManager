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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "personale")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personale.findAll", query = "SELECT p FROM Personale p")
    , @NamedQuery(name = "Personale.findById", query = "SELECT p FROM Personale p WHERE p.idPersonale = :id")
    , @NamedQuery(name = "Personale.findByNome", query = "SELECT p FROM Personale p WHERE p.nome = :nome")
    , @NamedQuery(name = "Personale.findByCognome", query = "SELECT p FROM Personale p WHERE p.cognome = :cognome")
    , @NamedQuery(name = "Personale.findByPosizione", query = "SELECT p FROM Personale p WHERE p.posizione = :posizione")})
public class Personale implements Serializable, DeepClonable<Personale> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPersonale")
    private Integer idPersonale;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Cognome")
    private String cognome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "posizione")
    private String posizione;

    public Personale() {
    }

    public Personale(Integer idPersonale) {
        this.idPersonale = idPersonale;
    }

    public Personale(Integer idPersonale, String nome, String cognome, String posizione) {
        this.idPersonale = idPersonale;
        this.nome = nome;
        this.cognome = cognome;
        this.posizione = posizione;
    }

    public Integer getIdPersonale() {
        return idPersonale;
    }

    public void setIdPersonale(Integer idPersonale) {
        this.idPersonale = idPersonale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPosizione() {
        return posizione;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonale != null ? idPersonale.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personale)) {
            return false;
        }
        Personale other = (Personale) object;
        if ((this.idPersonale == null && other.idPersonale != null) || (this.idPersonale != null && !this.idPersonale.equals(other.idPersonale))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome + " " + this.cognome;
    }

    @Override
    public Personale deepClone() {
        Personale result = new Personale();
        result.cognome = this.cognome;
        result.idPersonale = this.idPersonale;
        result.nome = this.nome;
        result.posizione = this.posizione;
        return result;
    }

    @Override
    public void restoreFromClone(Personale clone) {
        this.cognome = clone.cognome;
        this.idPersonale = clone.idPersonale;
        this.nome = clone.nome;
        this.posizione = clone.posizione;        
    }

    @Override
    public Integer getId(){
        return this.getIdPersonale();
    }
 
    
}
