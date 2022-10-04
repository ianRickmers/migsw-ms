package edu.migswms.services;

import edu.migswms.entities.InasistenciaEntity;
import edu.migswms.repositories.InasistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InasistenciaService {
    @Autowired
    InasistenciaRepository inasistenciaRepository;

    public boolean seDebeCrearInasistencia(Integer marcaHora, Integer marcaMinuto){
        if(marcaHora==9 && marcaMinuto>10 || marcaHora>9){
            return true;
        }
        return false;
    }

    public InasistenciaEntity crearInasistencia( String rut, String fecha){
            return new InasistenciaEntity(null,rut,fecha,0);
    }
    
}
