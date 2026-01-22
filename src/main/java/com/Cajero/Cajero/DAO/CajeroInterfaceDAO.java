package com.Cajero.Cajero.DAO;

import com.Cajero.Cajero.JPA.Result;


public interface CajeroInterfaceDAO {
    
    Result Retirar(Long IdCajero, Long IdUsuario, float CantidadRetiro);

}
