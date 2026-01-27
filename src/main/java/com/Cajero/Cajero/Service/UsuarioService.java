package com.Cajero.Cajero.Service;

import com.Cajero.Cajero.JPA.Usuario;
import com.Cajero.Cajero.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Transactional
    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }
    
    @Transactional
    public Optional<Usuario> getById(Long IdUsuario) {
        return  usuarioRepository.findById(IdUsuario);
    }
    
    @Transactional
    public Usuario add(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public Usuario update(Long id, Usuario usuarioActualizado) {
        Usuario findUsuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioRepository.save(usuarioActualizado);
    }
    
    @Transactional
    public Optional<Usuario> getByCuenta(String cuenta) {
        return  usuarioRepository.findByCuenta(cuenta);
    }
    
    @Transactional
    public void delete(Long IdUsuario){
        usuarioRepository.deleteById(IdUsuario);
    }
}
