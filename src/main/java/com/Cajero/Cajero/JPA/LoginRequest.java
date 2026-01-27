package com.Cajero.Cajero.JPA;


public class LoginRequest {
    private String cuenta;
    private String nip;
    
    public LoginRequest(){}
    
    public LoginRequest(String cuenta, String nip){
        this.cuenta = cuenta;
        this.nip = nip;
    }
    
    public String getCuenta() { return cuenta; }
    public void setCuenta(String cuenta ) { this.cuenta = cuenta; }
    public String getNip() { return nip; }
    public void setNip(String nip) { this.nip = nip; }
}
