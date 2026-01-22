package com.Cajero.Cajero.RestController;

import com.Cajero.Cajero.JPA.Cajero;
import com.Cajero.Cajero.JPA.Result;
import com.Cajero.Cajero.Service.BancoService;
import com.Cajero.Cajero.Service.CajeroService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CajeroRestController {
    
    @Autowired
    CajeroService cajeroService;
    
    @Autowired
    BancoService bancoService;
    
    @PostMapping("/cajeros")
    public ResponseEntity addCajero(@RequestBody Map<String, Object> payload){
    
        Result result = new Result();
        
        try {
            
            Integer id = (Integer) payload.get("IdBanco");
            long idBanco = id;
            cajeroService.addCajero(idBanco);
            result.correct = true;
            result.status = 201;
            
        
        } catch (Exception ex) {
            
            result.correct = false;
            result.status = 501;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @DeleteMapping("/cajeros/{idCajero}")
    public ResponseEntity deleteCajero(@PathVariable("idCajero") int idCajero){
        
        Result result = new Result();
        
        try {
            
            long id = (long) idCajero;
            cajeroService.delete(id);
            result.correct = true;
            result.status = 201;
            
        
        } catch (Exception ex) {
            
            result.correct = false;
            result.status = 501;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @PostMapping("/cajeros/{idCajero}")
    public ResponseEntity rellenarCajero(@PathVariable("idCajero") int idCajero){
    
        Result result = new Result();
        
         try {
            
            long id = (long) idCajero;
            Optional<Cajero> optionalCajero = cajeroService.getById(id);
            
            if(optionalCajero.isPresent()) {
                
                cajeroService.rellenarCajero(id);
                result.correct = true;
                result.status = 201;
                result.errorMessage = "Se relleno el cajero";
            } else {
                
                result.correct = false;
                result.status = 404;
                result.errorMessage = "No existe ese cajero";
            }
            
        
        } catch (Exception ex) {
            
            result.correct = false;
            result.status = 501;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    @PostMapping("/cajeros/retirar/{idCajero}")
    public ResponseEntity retirarCajero(@PathVariable("idCajero") int idCajero,
                                        @RequestBody Map<String, Object> datos){
    
        Result result = new Result();
        
         try {
            
            long id = (long) idCajero;
            Optional<Cajero> optionalCajero = cajeroService.getById(id);
            
            if(optionalCajero.isPresent()) {
                
                Integer idUsuario = (Integer) datos.get("IdUsuario");
                long idUser = (long) idUsuario;
                int cantidadRetiro = (int) datos.get("CantidadRetiro");
                float cantidad = cantidadRetiro;
                
                result.correct = true;
                result.status = 201;
                result.errorMessage = "Se retiro dinero";
            } else {
                
                result.correct = false;
                result.status = 404;
                result.errorMessage = "No existe ese cajero";
            }
            
        
        } catch (Exception ex) {
            
            result.correct = false;
            result.status = 501;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
}
