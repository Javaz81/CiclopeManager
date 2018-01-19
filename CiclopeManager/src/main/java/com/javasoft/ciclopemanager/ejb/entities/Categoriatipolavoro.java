/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.ciclopemanager.ejb.entities;

import com.javasoft.ciclopemanager.ejb.entities.util.DeepClonable;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andrea
 */
@Entity
@Table(name = "categoriatipolavoro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoriatipolavoro.findAll", query = "SELECT c FROM Categoriatipolavoro c")
    , @NamedQuery(name = "Categoriatipolavoro.findById", query = "SELECT c FROM Categoriatipolavoro c WHERE c.idCategoriaTipoLavoro = :id")
    , @NamedQuery(name = "Categoriatipolavoro.findByNome", query = "SELECT c FROM Categoriatipolavoro c WHERE c.nome = :nome")})
public class Categoriatipolavoro implements Serializable,
        DeepClonable<Categoriatipolavoro>{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCategoriaTipoLavoro")
    private Integer idCategoriaTipoLavoro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria", fetch = FetchType.EAGER)
    private Collection<Tipolavoro> tipolavoroCollection;

    public Categoriatipolavoro() {
    }

    public Categoriatipolavoro(Integer idCategoriaTipoLavoro) {
        this.idCategoriaTipoLavoro = idCategoriaTipoLavoro;
    }

    public Categoriatipolavoro(Integer idCategoriaTipoLavoro, String nome) {
        this.idCategoriaTipoLavoro = idCategoriaTipoLavoro;
        this.nome = nome;
    }
    
    
    public Integer getIdCategoriaTipoLavoro() {
        return idCategoriaTipoLavoro;
    }

    public void setIdCategoriaTipoLavoro(Integer idCategoriaTipoLavoro) {
        this.idCategoriaTipoLavoro = idCategoriaTipoLavoro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public Collection<Tipolavoro> getTipolavoroCollection() {
        return tipolavoroCollection;
    }

    public void setTipolavoroCollection(Collection<Tipolavoro> tipolavoroCollection) {
        this.tipolavoroCollection = tipolavoroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoriaTipoLavoro != null ? idCategoriaTipoLavoro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriatipolavoro)) {
            return false;
        }
        Categoriatipolavoro other = (Categoriatipolavoro) object;
        if ((this.idCategoriaTipoLavoro == null && other.idCategoriaTipoLavoro != null) || (this.idCategoriaTipoLavoro != null && !this.idCategoriaTipoLavoro.equals(other.idCategoriaTipoLavoro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    @Override
    public Categoriatipolavoro deepClone() {
        Categoriatipolavoro result = new Categoriatipolavoro();
        result.idCategoriaTipoLavoro = this.idCategoriaTipoLavoro;
        result.nome = this.nome;
        result.tipolavoroCollection = this.tipolavoroCollection;
        return result;
    }

    @Override
    public void restoreFromClone(Categoriatipolavoro clone) {
        this.idCategoriaTipoLavoro = clone.idCategoriaTipoLavoro;
        this.nome = clone.nome;
        this.tipolavoroCollection = clone.tipolavoroCollection;
    }

    @Override
    public Object getId() {
        return this.idCategoriaTipoLavoro;
    }
    
}
