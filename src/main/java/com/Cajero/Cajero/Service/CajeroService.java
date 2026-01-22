package com.Cajero.Cajero.Service;

import com.Cajero.Cajero.JPA.Cajero;
import com.Cajero.Cajero.JPA.Usuario;
import com.Cajero.Cajero.Repository.CajeroRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CajeroService {
    
    @Autowired
    private CajeroRepository cajeroRepository;
    
    @Transactional
    public List<Cajero> getAll(){
        return cajeroRepository.findAll();
    }
    
    @Transactional
    public void addCajero(Long IdBanco){
        cajeroRepository.AddCajero(IdBanco);
    }
    
    @Transactional
    public void delete(Long IdCajero){
        cajeroRepository.deleteById(IdCajero);
    }
    
    @Transactional 
    public void rellenarCajero(Long IdCajero){
        cajeroRepository.RecargarCajero(IdCajero);
    }
    
    @Transactional
    public Optional<Cajero> getById(Long IdCajero) {
        return  cajeroRepository.findById(IdCajero);
    }
    
}
