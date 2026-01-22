package com.Cajero.Cajero.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;


@Entity
@Table(name = "CAJERO")
public class Cajero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcajero")
    private Long idCajero;
    
    @Column(name = "cantidaddisponible")
    private float CantidadDisponible;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idbanco", nullable = false)
    public Banco banco;
    
    @JsonIgnore
    @OneToMany(mappedBy = "cajero", fetch = FetchType.LAZY)
    public List<CajeroDinero> transacciones;
    
    public Cajero(){}
    
    public Cajero(Long idCajero,float CantidadDisponible,  Banco banco){
        this.idCajero = idCajero;
        this.CantidadDisponible = CantidadDisponible;
        this.banco = banco;
    
    }
    
    public void setIdCajero(Long idCajero) {
        this.idCajero = idCajero;
    }
    public Long getIdCajero(){return idCajero;}
    
    public void setCantidadDisponible(float CantidadDisponible) {
        this.CantidadDisponible = CantidadDisponible;
    }
    public float getCantidadDisponible(){return CantidadDisponible;}
    
    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    
    public Banco getBanco(){return banco;}
}
