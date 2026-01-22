package com.Cajero.Cajero.DAO;

import com.Cajero.Cajero.JPA.Result;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteImplementacionDAO implements CajeroInterfaceDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional
    @Override
    public Result Retirar(Long IdCajero, Long IdUsuario, float CantidadRetiro) {
        return jdbcTemplate.execute("{CALL DescontarDineroCajero(?,?,?,?,?)}", 
            (CallableStatementCallback<Result>) callableStatement -> {

                Result resultSP = new Result();
                try {
                    callableStatement.setLong(1, IdCajero);
                    callableStatement.setLong(2, IdUsuario);

                    callableStatement.setFloat(3, CantidadRetiro);
                    callableStatement.registerOutParameter(3, Types.FLOAT);
                    callableStatement.registerOutParameter(4, Types.NUMERIC);
                    callableStatement.registerOutParameter(5, Types.REF_CURSOR);
                    callableStatement.execute();

                    float cantidadFinal = callableStatement.getFloat(3);
                    Long idDinero = callableStatement.getLong(4);

                    ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                    resultSP.objects = new ArrayList<>();
                    while (resultSet.next()) {
                        resultSP.objects.add(resultSet.getFloat("Denominacion"));
                    }
                    resultSet.close();

                    resultSP.correct = true;
                } catch (Exception ex) {
                    resultSP.correct = false;
                    resultSP.errorMessage = ex.getLocalizedMessage();
                    resultSP.ex = ex;
                }
                return resultSP;
            });
    }


}
