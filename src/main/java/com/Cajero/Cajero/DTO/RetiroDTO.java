package com.Cajero.Cajero.DTO;


public class RetiroDTO {
    
    private Long idUser;
    private int idCajero;
    private double cantidadRetiro;
    
    public RetiroDTO(){}
    
     public RetiroDTO( Long idUser,int idCajero, double cantidadRetiro){
         this.idCajero = idCajero;
         this.idUser = idUser;
         this.cantidadRetiro = cantidadRetiro;
     }
     
     public void setIdCajero(int idCajero){this.idCajero = idCajero;}
     public int getIdCajero(){return idCajero;}
     
     public void setIdUser(Long idUser){this.idUser = idUser;}
     public Long getIdUser(){return idUser;}
     
     public void setCantidadRetiro(double cantidadRetiro){
         this.cantidadRetiro = cantidadRetiro;
     }
     public double getCantidadRetiro(){return cantidadRetiro;}

}
