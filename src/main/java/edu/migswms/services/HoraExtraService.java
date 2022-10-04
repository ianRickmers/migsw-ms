package edu.migswms.services;

import edu.migswms.entities.HoraExtraEntity;
import edu.migswms.repositories.HoraExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoraExtraService {
    @Autowired
    HoraExtraRepository horaExtraRepository;
    Integer horaSalida=18;
    
    public HoraExtraEntity cambiarHoras(HoraExtraEntity horaExtra,Integer marcaHora, Integer marcaMinuto){
        marcaHora = marcaHora - horaSalida;
        horaExtra.setCantidadHoras(horaExtra.getCantidadHoras() + marcaHora);
        horaExtra.setCantidadMinutos(horaExtra.getCantidadMinutos() + marcaMinuto);
        if (horaExtra.getCantidadMinutos() >= 60) {
            horaExtra.setCantidadHoras(horaExtra.getCantidadHoras() + 1);
            horaExtra.setCantidadMinutos(horaExtra.getCantidadMinutos() - 60);
        }
        return horaExtra;
    }
    
    public HoraExtraEntity cambiarHorasExtra(Integer marcaHora, Integer marcaMinuto, HoraExtraEntity horaExtra){
        if(marcaHora.equals(horaSalida) && marcaMinuto>0){
            horaExtra=cambiarHoras(horaExtra,marcaHora,marcaMinuto);
            return horaExtra;
        }
        if(marcaHora>horaSalida){
            horaExtra=cambiarHoras(horaExtra,marcaHora,marcaMinuto);
            return horaExtra;
        }
        return horaExtra;
    }
}
