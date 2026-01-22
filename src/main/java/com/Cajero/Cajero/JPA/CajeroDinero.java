package com.Cajero.Cajero.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CAJERODINERO")
public class CajeroDinero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcajero", nullable = false)
    public Cajero cajero;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddinero", nullable = false)
    public Dinero dinero;
    
    @Column(name = "cantidaddisponible")
    private int CantidadDisponible;
    
    public CajeroDinero(){}
    
    public CajeroDinero(Long id, int CantidadDisponible){
        this.id = id;
        this.CantidadDisponible = CantidadDisponible;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){return id;}
    
    public void setCantidadDisponible(int CantidadDisponible) {
        this.CantidadDisponible = CantidadDisponible;
    }
    public int getCantidadDisponible(){return CantidadDisponible;}
    
    
}
