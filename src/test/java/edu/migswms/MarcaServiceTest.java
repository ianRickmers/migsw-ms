package edu.migswms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import edu.migswms.entities.MarcaEntity;
import edu.migswms.services.MarcaService;

@TestPropertySource(locations="classpath:test.properties")
public class MarcaServiceTest {
    MarcaService marcaService = new MarcaService();
    MarcaEntity marcaEntity = new MarcaEntity();

    @Test
    void subirMarca() throws ParseException{
        marcaEntity.setFecha("2020-01-01");
        marcaEntity.setHora("12");
        marcaEntity.setMinuto("00");
        marcaEntity.setRut("12.345.678-9");
        MarcaEntity marca = marcaService.subirMarca("2020/01/01;12:00;12.345.678-9");
        assertEquals(marcaEntity, marca);
    }

}
