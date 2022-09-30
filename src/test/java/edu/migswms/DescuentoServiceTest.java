package edu.migswms;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import edu.migswms.entities.DescuentoEntity;
import edu.migswms.services.DescuentoService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations="classpath:test.properties")
class DescuentoServiceTest {
    DescuentoService descuentoService = new DescuentoService();
    DescuentoEntity descuentoEntity = new DescuentoEntity();

    @Test
    void cambiarDesc10(){
        descuentoEntity.setRut("12345678-9");
        descuentoEntity.setDesc10(0);
        descuentoEntity.setDesc25(0);
        descuentoEntity.setDesc45(0);
        descuentoEntity = descuentoService.cambiarDesc10(descuentoEntity);
        assertEquals(1, descuentoEntity.getDesc10());
    }

    @Test
    void cambiarDesc25(){
        descuentoEntity.setRut("12345678-9");
        descuentoEntity.setDesc10(0);
        descuentoEntity.setDesc25(0);
        descuentoEntity.setDesc45(0);
        descuentoEntity = descuentoService.cambiarDesc25(descuentoEntity);
        assertEquals(1, descuentoEntity.getDesc25());
    }

    @Test
    void cambiarDesc45(){
        descuentoEntity.setRut("12345678-9");
        descuentoEntity.setDesc10(0);
        descuentoEntity.setDesc25(0);
        descuentoEntity.setDesc45(0);
        descuentoEntity = descuentoService.cambiarDesc45(descuentoEntity);
        assertEquals(1, descuentoEntity.getDesc45());
    }

    @Test
    void cambiarDescuentos(){
        descuentoEntity.setRut("12345678-9");
        descuentoEntity.setDesc10(0);
        descuentoEntity.setDesc25(0);
        descuentoEntity.setDesc45(0);
        descuentoEntity = descuentoService.cambiarDescuentos(8, 11, descuentoEntity);
        assertEquals(1, descuentoEntity.getDesc10());
        assertEquals(0, descuentoEntity.getDesc25());
        assertEquals(0, descuentoEntity.getDesc45());

        descuentoEntity = descuentoService.cambiarDescuentos(8, 30, descuentoEntity);
        assertEquals(1, descuentoEntity.getDesc10());
        assertEquals(1, descuentoEntity.getDesc25());
        assertEquals(0, descuentoEntity.getDesc45());

        descuentoEntity = descuentoService.cambiarDescuentos(8, 55, descuentoEntity);
        assertEquals(1, descuentoEntity.getDesc10());
        assertEquals(1, descuentoEntity.getDesc25());
        assertEquals(1, descuentoEntity.getDesc45());

        descuentoEntity = descuentoService.cambiarDescuentos(9, 3, descuentoEntity);
        assertEquals(1, descuentoEntity.getDesc10());
        assertEquals(1, descuentoEntity.getDesc25());
        assertEquals(2, descuentoEntity.getDesc45());

        descuentoEntity = descuentoService.cambiarDescuentos(9, 30, descuentoEntity);
        assertEquals(1, descuentoEntity.getDesc10());
        assertEquals(1, descuentoEntity.getDesc25());
        assertEquals(2, descuentoEntity.getDesc45());
    }
} 