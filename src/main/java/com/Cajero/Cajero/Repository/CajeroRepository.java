package com.Cajero.Cajero.Repository;

import com.Cajero.Cajero.JPA.Cajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface CajeroRepository extends JpaRepository <Cajero, Long>{
    
    @Procedure
    void AddCajero(Long idBanco);
    
    @Procedure
    void RecargarCajero(Long idCajero);
    
    
}
