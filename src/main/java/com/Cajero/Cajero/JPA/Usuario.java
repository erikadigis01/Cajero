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
@Table(name = "USUARIO")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long idUsuario;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @Column(name = "cuenta")
    private String cuenta;
    
    @Column(name = "nip")
    private int Nip;
    
    @Column(name = "saldodisponible")
    private float SaldoDisponible;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idroll", nullable = false)
    public Roll roll;
    
    public Usuario(){}
    
    public Usuario(Long idUsuario, String Nombre, 
                  String cuenta,int Nip, float SaldoDisponible){
        this.idUsuario = idUsuario;
        this.Nombre = Nombre;
        this.cuenta = cuenta;
        this.Nip = Nip;
        this.SaldoDisponible = SaldoDisponible;
    }
    
    public void setIdUsuario(Long idUsuario){this.idUsuario = idUsuario;}
    public Long getIdUsuario(){return idUsuario;}
    
    public void setNombre(String Nombre){this.Nombre = Nombre;}
    public String getNombre(){return Nombre;}
    
    public void setCuenta(String Cuenta){this.cuenta = Cuenta;}
    public String getCuenta(){return cuenta;}
    
    public void setNip(int Nip){this.Nip = Nip;}
    public int getNip(){return Nip;}
    
    public void setSaldoDisponible(float SaldoDisponible){ 
        this.SaldoDisponible = SaldoDisponible;
    }
    public float getSaldoDisponible(){return SaldoDisponible;}
    
}
