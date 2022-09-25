package edu.migswms;

import org.junit.jupiter.api.Test;

import edu.migswms.entities.HoraExtraEntity;
import edu.migswms.services.HoraExtraService;

public class HoraExtraServiceTest {
    HoraExtraService horaExtraService = new HoraExtraService();
    HoraExtraEntity horaExtraEntity = new HoraExtraEntity();

    @Test
    void cambiarHoras(){
        horaExtraEntity.setRut("12345678-9");
        horaExtraEntity.setCantidadHoras(21);
        horaExtraEntity.setCantidadMinutos(50);
        horaExtraEntity.setAutorizada(0);
        horaExtraEntity = horaExtraService.cambiarHoras(horaExtraEntity, 19, 30);
        assert(horaExtraEntity.getCantidadHoras() == 23 && horaExtraEntity.getCantidadMinutos() == 20);

        horaExtraEntity = horaExtraService.cambiarHoras(horaExtraEntity, 19, 30);
        assert(horaExtraEntity.getCantidadHoras() == 24 && horaExtraEntity.getCantidadMinutos() == 50);
    }

    @Test
    void cambiarHorasExtra(){
        horaExtraEntity.setRut("12345678-9");
        horaExtraEntity.setCantidadHoras(21);
        horaExtraEntity.setCantidadMinutos(50);
        horaExtraEntity.setAutorizada(0);
        horaExtraEntity = horaExtraService.cambiarHorasExtra(18, 0, horaExtraEntity);
        assert(horaExtraEntity.getCantidadHoras() == 21 && horaExtraEntity.getCantidadMinutos() == 50);

        horaExtraEntity = horaExtraService.cambiarHorasExtra(18, 10, horaExtraEntity);
        assert(horaExtraEntity.getCantidadHoras() == 22 && horaExtraEntity.getCantidadMinutos() == 0);

        horaExtraEntity = horaExtraService.cambiarHorasExtra(19, 10, horaExtraEntity);
        assert(horaExtraEntity.getCantidadHoras() == 23 && horaExtraEntity.getCantidadMinutos() ==10);
    }
    
}
