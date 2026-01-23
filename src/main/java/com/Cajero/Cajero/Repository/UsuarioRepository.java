package com.Cajero.Cajero.Repository;

import com.Cajero.Cajero.JPA.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    Optional<Usuario> findByUsername(String cuenta);

    Boolean existsByUsername(String cuenta);

    Boolean existsByEmail(String cuenta);

}
