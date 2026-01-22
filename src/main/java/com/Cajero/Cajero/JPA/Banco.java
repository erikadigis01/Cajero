package com.Cajero.Cajero.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BANCO")
public class Banco {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbanco")
    private Long idBanco;
    
    @Column(name = "nombre")
    private String Nombre;
    
    public Banco(){}
    
    public Banco(Long idBanco, String Nombre){
        this.idBanco = idBanco;
        this.Nombre = Nombre;
    
    }
    
    public void setIdBanco(Long idBanco) {
        this.idBanco = idBanco;
    }
    public Long getIdBanco(){return idBanco;}
    
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    public String getNombre(){return Nombre;}
}
