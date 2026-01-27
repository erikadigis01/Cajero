package com.Cajero.Cajero.Security;

import com.Cajero.Cajero.JPA.Usuario;
import com.Cajero.Cajero.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String cuenta) throws UsernameNotFoundException {
        // Cambia el .orElse(null) por orElseThrow para evitar errores posteriores
        Usuario usuario = usuarioRepository.findByCuenta(cuenta)
            .orElseThrow(() -> new UsernameNotFoundException("La cuenta " + cuenta + " no existe en la BD"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (usuario.roll != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.roll.getNombre().toUpperCase()));
        }

        return new org.springframework.security.core.userdetails.User(
                usuario.getCuenta(),
                usuario.getNip(),
                authorities
        );
    }


}
