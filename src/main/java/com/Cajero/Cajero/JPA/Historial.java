package com.Cajero.Cajero.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Historial")
public class Historial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhistorial")
    private Long idHistorial;
    
    @Column(name = "retiro")
    private float Retiro;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", nullable = false)
    public Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcajero", nullable = false)
    public Cajero cajero;
    
    public Historial(){}
    
    public Historial(Long idHistorial, float Retiro){
        this.idHistorial = idHistorial;
        this.Retiro = Retiro;
    }
    
    public void setIdHistorial(Long idHistorial){
        this.idHistorial = idHistorial;
    }
    public Long getIdHistorial(){return idHistorial;}
    
    public void setRetiro(float Retiro){this.Retiro = Retiro;}
    public float getRetiro(){return Retiro;}
}
