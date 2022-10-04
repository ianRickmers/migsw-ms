package edu.migswms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.springframework.test.context.TestPropertySource;

import edu.migswms.entities.InasistenciaEntity;
import edu.migswms.services.InasistenciaService;

/*
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
*/
@TestPropertySource(locations="classpath:test.properties")
class InasistenciaServiceTest {
    
    InasistenciaService inasistenciaService= new InasistenciaService();

    @Test
    void seDebeCrearInasistencia(){
        assertEquals(true, inasistenciaService.seDebeCrearInasistencia(9, 11));
        assertEquals(false, inasistenciaService.seDebeCrearInasistencia(9, 10));
        assertEquals(true, inasistenciaService.seDebeCrearInasistencia(10, 10));
    }

    @Test
    void crearInasistencia(){
        InasistenciaEntity inasistenciaEntity = new InasistenciaEntity();
        inasistenciaEntity.setRut("12345678-9");
        inasistenciaEntity.setFecha("2020-01-01");
        inasistenciaEntity.setJustificada(0);
        inasistenciaEntity=inasistenciaService.crearInasistencia( "12345678-9", "2020-01-01");
        assertEquals("12345678-9",inasistenciaEntity.getRut());
        assertEquals("2020-01-01",inasistenciaEntity.getFecha());
        assertEquals(0,inasistenciaEntity.getJustificada());
    }
}
