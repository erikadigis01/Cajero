package com.Cajero.Cajero.Service;

import com.Cajero.Cajero.JPA.Banco;
import com.Cajero.Cajero.Repository.BancoRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BancoService {
    
    @Autowired
    BancoRepository bancoRepository;
    
    @Transactional
    public Optional<Banco> getById (Long IdBanco){
        return bancoRepository.findById(IdBanco);
    }

}
