package com.Cajero.Cajero.RestController;

import com.Cajero.Cajero.JPA.Result;
import com.Cajero.Cajero.JPA.Usuario;
import com.Cajero.Cajero.Service.UsuarioService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {
    
    @Autowired
    UsuarioService usuarioService;
    
    @GetMapping("/detail/{idUsuario}")
    public ResponseEntity detailUsuario(@PathVariable("idUsuario") int idUsuario){
        
        Result result = new Result();
        
        try {
            
            long id = (long)idUsuario;
            
            Optional<Usuario> usuario = usuarioService.getById(id);
            
            if(usuario.isPresent()) {
                result.correct = true;
                result.errorMessage = "Se encontro un usuario";
                result.object = usuario;
                result.status = 200;
                
            } else {
                result.correct = false;
                result.status = 404;
                result.errorMessage = "No se encontro un usuario con ese id";
            
            }
            
        } catch (Exception ex) {
        
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.status = 500;
        
        }
        
        return ResponseEntity.status(result.status).body(result);
    }

}
