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
    Integer horaSalida=18;
    
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

    public void cambiarHoras(String rut,Integer marcaHora, Integer marcaMinuto){
        Optional<HoraExtraEntity> horaExtra = horaExtraRepository.findByRut(rut);
        marcaHora=marcaHora-horaSalida;
        if(horaExtra.isPresent()){
            HoraExtraEntity horaExtraEntity = horaExtra.get();
            horaExtraEntity.setCantidadHoras(horaExtraEntity.getCantidadHoras()+marcaHora);
            horaExtraEntity.setCantidadMinutos(horaExtraEntity.getCantidadMinutos()+marcaMinuto);
            horaExtraRepository.save(horaExtraEntity);
        }
        else{
            HoraExtraEntity descuentoEntity = new HoraExtraEntity(null,rut,marcaHora,marcaMinuto,0);
            horaExtraRepository.save(descuentoEntity);
        }
    }
    
    public void cambiarHorasExtra(Integer marcaHora, Integer marcaMinuto, String rut){
        if(marcaHora==horaSalida && marcaMinuto>0){
            cambiarHoras(rut,marcaHora,marcaMinuto);
        }
        if(marcaHora<horaSalida){
            cambiarHoras(rut,marcaHora,marcaMinuto);
        }
    }
}
