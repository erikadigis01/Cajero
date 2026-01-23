package com.Cajero.Cajero.JPA;


public class LoginRequest {
    private String email;
    private int nip;
    
    public LoginRequest(){}
    
    public LoginRequest(String email, int nip){
        this.email = email;
        this.nip = nip;
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getNip() { return nip; }
    public void setNip(int nip) { this.nip = nip; }
}
