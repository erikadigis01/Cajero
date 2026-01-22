package com.Cajero.Cajero.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "DINERO")
public class Dinero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddinero")
    private Long idDinero;
    
    @Column(name = "denominacion")
    private float Denominacion;
    
    @Column(name = "cantidadPermitida")
    private int CantidadPermitida;
    
    @OneToMany(mappedBy = "dinero", fetch = FetchType.LAZY)
    public List<CajeroDinero> transacciones;
    
    public Dinero(){}
    
    public Dinero(Long idDinero,float Denominacion, int CantidadPermitida){
        this.idDinero = idDinero;
        this.Denominacion = Denominacion;
        this.CantidadPermitida = CantidadPermitida;
    }
    
    public void setIDDinero(Long idDinero){this.idDinero = idDinero;}
    public Long getIdDinero(){return idDinero;}
    
    public void setDenominacion(float Denominacion){ 
        this.Denominacion = Denominacion;
    }
    public float getDenominacion(){return Denominacion;}
    
    public void setCantidadPermitida(int CantidadPermitida){
        
        this.CantidadPermitida = CantidadPermitida;}
    public int getCantidadPermitida(){return CantidadPermitida;}

}
