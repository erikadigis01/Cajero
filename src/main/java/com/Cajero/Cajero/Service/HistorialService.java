package com.Cajero.Cajero.Service;

import com.Cajero.Cajero.JPA.Historial;
import com.Cajero.Cajero.Repository.HistorialRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistorialService {
    
    @Autowired
    private HistorialRepository historialRepository;
    
    @Transactional
    public List<Historial> getAll(){
        return historialRepository.findAll();
    }
    
    @Transactional
    public Optional<Historial> getById(Long IdHistorial) {
        return historialRepository.findById(IdHistorial);
    }
    
    @Transactional
    public Historial add(Historial historial){
        return historialRepository.save(historial);
    }
    
    @Transactional
    public Historial update(Historial historial){
        Historial find = historialRepository.findById(historial.getIdHistorial())
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        return historialRepository.save(historial);
    }
    
    @Transactional
    public void delete(Long IdHistorial){
        historialRepository.deleteById(IdHistorial);
    }
}
