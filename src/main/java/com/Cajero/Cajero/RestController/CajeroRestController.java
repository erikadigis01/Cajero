package com.Cajero.Cajero.RestController;

import com.Cajero.Cajero.DAO.ClienteImplementacionDAO;
import com.Cajero.Cajero.DTO.RetiroDTO;
import com.Cajero.Cajero.JPA.Cajero;
import com.Cajero.Cajero.JPA.Result;
import com.Cajero.Cajero.Service.CajeroService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CajeroRestController {
    
    @Autowired
    CajeroService cajeroService;
    
    @Autowired
    ClienteImplementacionDAO clienteImplementacionDAO;
    
    @GetMapping("/cajeros")
    public ResponseEntity getAll(){
        Result result = new Result();
            
        try {
            
            List<Cajero> cajeros = cajeroService.getAll();
            if(!cajeros.isEmpty()) {
                
                result.correct = true;
                result.errorMessage = "Se encontraron cajeros";
                result.status = 200;
                result.objects = cajeros;
            
            } else {
                
                result.correct = false;
                result.errorMessage = "No hay cajeros";
                result.status = 400;
            
            }
        
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        
        return ResponseEntity.status(result.status).body(result);
    
    }
    
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
    @PostMapping("/cajeros/retirar")
    public ResponseEntity<Result> retirarCajero(@RequestBody RetiroDTO retiro) {

        Result result = new Result();

        try {
            long idCajeroLong = retiro.getIdCajero(); 
            Optional<Cajero> optionalCajero = cajeroService.getById(idCajeroLong);

            if (optionalCajero.isPresent()) {
                Long idUsuario = retiro.getIdUser();
                Float cantidadRetiro = (float) retiro.getCantidadRetiro();
                result = clienteImplementacionDAO.Retirar(idCajeroLong, idUsuario, cantidadRetiro);
                result.status = result.correct ? 200 : 400;
                result.correct = true;

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
