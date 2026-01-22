package com.Cajero.Cajero.Repository;

import com.Cajero.Cajero.JPA.Roll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RollRepository extends JpaRepository< Roll, Long>{

}
