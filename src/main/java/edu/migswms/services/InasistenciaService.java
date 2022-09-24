package edu.migswms.services;

import edu.migswms.entities.InasistenciaEntity;
import edu.migswms.entities.MarcaEntity;
import edu.migswms.repositories.InasistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InasistenciaService {
    @Autowired
    InasistenciaRepository inasistenciaRepository;

    public Boolean existe(String rut, String fecha){
        if (inasistenciaRepository.countByRutAndDate(rut,fecha)==0){
            return false;
        }
        else{
            return true;
        }
    }

    public void crearInasistencia(Integer marcaHora, Integer marcaMinuto, String rut, MarcaEntity marca){
        if(marcaHora==9 && marcaMinuto>10){
            InasistenciaEntity inasistencia=new InasistenciaEntity(null,rut,(marca).getFecha(),0);
            inasistenciaRepository.save(inasistencia);
        }
        if(marcaHora>9){
            InasistenciaEntity inasistencia=new InasistenciaEntity(null,rut,(marca).getFecha(),0);
            inasistenciaRepository.save(inasistencia);
        }
    }
    
}
