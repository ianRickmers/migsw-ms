package edu.migswms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import edu.migswms.entities.HoraExtraEntity;
import edu.migswms.services.HoraExtraService;

@TestPropertySource(locations="classpath:test.properties")
class HoraExtraServiceTest {
    HoraExtraService horaExtraService = new HoraExtraService();
    HoraExtraEntity horaExtraEntity = new HoraExtraEntity();

    @Test
    void cambiarHoras(){
        horaExtraEntity.setRut("12345678-9");
        horaExtraEntity.setCantidadHoras(21);
        horaExtraEntity.setCantidadMinutos(50);
        horaExtraEntity.setAutorizada(0);
        horaExtraEntity = horaExtraService.cambiarHoras(horaExtraEntity, 19, 30);
        assertEquals(23, horaExtraEntity.getCantidadHoras());
        assertEquals(20, horaExtraEntity.getCantidadMinutos());

        horaExtraEntity = horaExtraService.cambiarHoras(horaExtraEntity, 19, 30);
        assertEquals(24, horaExtraEntity.getCantidadHoras());
        assertEquals(50, horaExtraEntity.getCantidadMinutos());
    }

    @Test
    void cambiarHorasExtra(){
        horaExtraEntity.setRut("12345678-9");
        horaExtraEntity.setCantidadHoras(21);
        horaExtraEntity.setCantidadMinutos(50);
        horaExtraEntity.setAutorizada(0);
        horaExtraEntity = horaExtraService.cambiarHorasExtra(18, 0, horaExtraEntity);
        assertEquals(21, horaExtraEntity.getCantidadHoras());
        assertEquals(50, horaExtraEntity.getCantidadMinutos());

        horaExtraEntity = horaExtraService.cambiarHorasExtra(18, 10, horaExtraEntity);
        assertEquals(22, horaExtraEntity.getCantidadHoras());
        assertEquals(0, horaExtraEntity.getCantidadMinutos());

        horaExtraEntity = horaExtraService.cambiarHorasExtra(19, 10, horaExtraEntity);
        assertEquals(23, horaExtraEntity.getCantidadHoras());
        assertEquals(10, horaExtraEntity.getCantidadMinutos());

    }
    
}
