package com.Cajero.Cajero.Repository;

import com.Cajero.Cajero.JPA.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialRepository extends JpaRepository <Historial, Long>{

}
