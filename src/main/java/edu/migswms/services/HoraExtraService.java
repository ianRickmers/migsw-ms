package edu.migswms.services;

import edu.migswms.entities.HoraExtraEntity;
import edu.migswms.repositories.HoraExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class HoraExtraService {
    @Autowired
    HoraExtraRepository horaExtraRepository;

    public ArrayList<HoraExtraEntity> obtenerHorasExtras(){
        return (ArrayList<HoraExtraEntity>) horaExtraRepository.findAll();
    }

    public HoraExtraEntity guardarHoraExtra(HoraExtraEntity horaExtra){
        return horaExtraRepository.save(horaExtra);
    }

    public Optional<HoraExtraEntity> obtenerPorId(Long id){
        return horaExtraRepository.findById(id);
    }

    public boolean eliminarHoraExtra(Long id) {
        try{
            horaExtraRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }

}
