/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.ciclopemanager.ejb.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "articolo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articolo.findAll", query = "SELECT a FROM Articolo a")
    , @NamedQuery(name = "Articolo.findByIdArticolo", query = "SELECT a FROM Articolo a WHERE a.idArticolo = :idArticolo")
    , @NamedQuery(name = "Articolo.findByDescrizione", query = "SELECT a FROM Articolo a WHERE a.descrizione = :descrizione")
    , @NamedQuery(name = "Articolo.findByScortaRimanente", query = "SELECT a FROM Articolo a WHERE a.scortaRimanente = :scortaRimanente")
    , @NamedQuery(name = "Articolo.findByScortaMinima", query = "SELECT a FROM Articolo a WHERE a.scortaMinima = :scortaMinima")
    , @NamedQuery(name = "Articolo.findByUnitaDiMisura", query = "SELECT a FROM Articolo a WHERE a.unitaDiMisura = :unitaDiMisura")
    , @NamedQuery(name = "Articolo.findByApprovvigionamento", query = "SELECT a FROM Articolo a WHERE a.approvvigionamento = :approvvigionamento")})
public class Articolo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArticolo")
    private Integer idArticolo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descrizione")
    private String descrizione;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "scorta_rimanente")
    private BigDecimal scortaRimanente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scorta_minima")
    private BigDecimal scortaMinima;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "unita_di_misura")
    private String unitaDiMisura;
    @Column(name = "approvvigionamento")
    private BigDecimal approvvigionamento;

    public Articolo() {
    }

    public Articolo(Integer idArticolo) {
        this.idArticolo = idArticolo;
    }

    public Articolo(Integer idArticolo, String descrizione, BigDecimal scortaRimanente, BigDecimal scortaMinima, String unitaDiMisura) {
        this.idArticolo = idArticolo;
        this.descrizione = descrizione;
        this.scortaRimanente = scortaRimanente;
        this.scortaMinima = scortaMinima;
        this.unitaDiMisura = unitaDiMisura;
    }

    public Integer getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(Integer idArticolo) {
        this.idArticolo = idArticolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getScortaRimanente() {
        return scortaRimanente;
    }

    public void setScortaRimanente(BigDecimal scortaRimanente) {
        this.scortaRimanente = scortaRimanente;
    }

    public BigDecimal getScortaMinima() {
        return scortaMinima;
    }

    public void setScortaMinima(BigDecimal scortaMinima) {
        this.scortaMinima = scortaMinima;
    }

    public String getUnitaDiMisura() {
        return unitaDiMisura;
    }

    public void setUnitaDiMisura(String unitaDiMisura) {
        this.unitaDiMisura = unitaDiMisura;
    }

    public BigDecimal getApprovvigionamento() {
        return approvvigionamento;
    }

    public void setApprovvigionamento(BigDecimal approvvigionamento) {
        this.approvvigionamento = approvvigionamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArticolo != null ? idArticolo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articolo)) {
            return false;
        }
        Articolo other = (Articolo) object;
        if ((this.idArticolo == null && other.idArticolo != null) || (this.idArticolo != null && !this.idArticolo.equals(other.idArticolo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft.ciclopemanager.ejb.entities.Articolo[ idArticolo=" + idArticolo + " ]";
    }
    
}
