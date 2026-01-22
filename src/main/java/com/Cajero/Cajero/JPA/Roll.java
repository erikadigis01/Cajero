package com.Cajero.Cajero.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROLL")
public class Roll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idroll")
    private Long idRoll;
    
     @Column(name = "nombre")
    private String Nombre;
    
    public Roll(){}
    
    public Roll(Long idRoll, String Nombre){
        this.idRoll = idRoll;
        this.Nombre = Nombre;
    }
    
    public void setIdRoll(Long idRoll){this.idRoll = idRoll;}
    public Long getIdRoll(){return idRoll;}
    
    public void setNombre(String Nombre) {this.Nombre = Nombre;}
    public String getNombre(){return Nombre;}

}
