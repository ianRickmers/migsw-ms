package edu.migswms.services;

import edu.migswms.entities.InasistenciaEntity;
import edu.migswms.repositories.InasistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class InasistenciaService {
    @Autowired
    InasistenciaRepository inasistenciaRepository;

    public ArrayList<InasistenciaEntity> obtenerInasistencias(){
        return (ArrayList<InasistenciaEntity>) inasistenciaRepository.findAll();
    }

    public InasistenciaEntity guardarInasistencia(InasistenciaEntity inasistencia){
        return inasistenciaRepository.save(inasistencia);
    }

    public Optional<InasistenciaEntity> obtenerPorId(Long id){
        return inasistenciaRepository.findById(id);
    }

    public boolean eliminarInasistencia(Long id) {
        try{
            inasistenciaRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
}
