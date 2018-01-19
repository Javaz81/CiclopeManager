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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "veicolo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Veicolo.findAll", query = "SELECT v FROM Veicolo v")
    , @NamedQuery(name = "Veicolo.findById", query = "SELECT v FROM Veicolo v WHERE v.idVeicolo = :id")
    , @NamedQuery(name = "Veicolo.findByMarca", query = "SELECT v FROM Veicolo v WHERE v.marca = :marca")
    , @NamedQuery(name = "Veicolo.findByModello", query = "SELECT v FROM Veicolo v WHERE v.modello = :modello")
    , @NamedQuery(name = "Veicolo.findByTarga", query = "SELECT v FROM Veicolo v WHERE v.targa = :targa")
    , @NamedQuery(name = "Veicolo.findByAnno", query = "SELECT v FROM Veicolo v WHERE v.anno = :anno")
    , @NamedQuery(name = "Veicolo.findByTipo", query = "SELECT v FROM Veicolo v WHERE v.tipo = :tipo")
    , @NamedQuery(name = "Veicolo.findByMatricola", query = "SELECT v FROM Veicolo v WHERE v.matricola = :matricola")})
public class Veicolo implements Serializable, DeepClonable<Veicolo> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVeicolo")
    private Integer idVeicolo;
    @Size(max = 45)
    @Column(name = "marca")
    private String marca;
    @Size(max = 45)
    @Column(name = "modello")
    private String modello;
    @Size(max = 10)
    @Column(name = "targa")
    private String targa;
    @Column(name = "anno")
    private Integer anno;
    @Size(max = 21)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "matricola")
    private String matricola;
    @JoinColumn(name = "cliente", referencedColumnName = "idCliente")
    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente cliente;

    public Veicolo() {
    }

    public Veicolo(Integer idVeicolo) {
        this.idVeicolo = idVeicolo;
    }

    public Veicolo(Integer idVeicolo, String matricola) {
        this.idVeicolo = idVeicolo;
        this.matricola = matricola;
    }

    public Integer getIdVeicolo() {
        return idVeicolo;
    }

    public void setIdVeicolo(Integer idVeicolo) {
        this.idVeicolo = idVeicolo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVeicolo != null ? idVeicolo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Veicolo)) {
            return false;
        }
        Veicolo other = (Veicolo) object;
        if ((this.idVeicolo == null && other.idVeicolo != null) || (this.idVeicolo != null && !this.idVeicolo.equals(other.idVeicolo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.marca + " " 
                + this.modello + " " 
                + this.matricola + " "
                + this.targa + " ["
                + this.tipo + "] ";
    }

    @Override
    public Veicolo deepClone() {
       Veicolo result = new Veicolo();
       result.anno = this.anno;
       result.cliente = this.cliente;
       result.idVeicolo = this.idVeicolo;
       result.marca = this.marca;
       result.matricola =this.matricola;
       result.modello = this.modello;
       result.targa = this.targa;
       result.tipo = this.tipo;
       return result;
    }

    @Override
    public void restoreFromClone(Veicolo clone) {
        this.anno=clone.anno;
        this.cliente = clone.cliente;
        this.idVeicolo = clone.idVeicolo;
        this.marca = clone.marca;
        this.matricola = clone.matricola;
        this.modello = clone.modello;
        this.targa = clone.targa;
        this.tipo = clone.tipo;
    }

    @Override
    public Object getId() {
        return this.idVeicolo;
    }
    
     @Override
    public void setId(Object cloneId){
        this.idVeicolo = (Integer) cloneId;
    }
}
