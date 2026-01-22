package com.Cajero.Cajero.Repository;

import com.Cajero.Cajero.JPA.CajeroDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CajeroDineroRepository extends JpaRepository<CajeroDinero, Long>{

}
