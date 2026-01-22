package com.Cajero.Cajero.Repository;

import com.Cajero.Cajero.JPA.Dinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DineroRepository extends JpaRepository<Dinero, Long>  {

}
