package edu.migswms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
*/
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import edu.migswms.entities.InasistenciaEntity;
import edu.migswms.repositories.InasistenciaRepository;
import edu.migswms.services.InasistenciaService;

/*
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
*/
@TestPropertySource(locations="classpath:test.properties")
public class InasistenciaServiceTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private InasistenciaRepository inasistenciaRepository;
    
    InasistenciaService inasistenciaService= new InasistenciaService();

    @Test
    void existe(){
        InasistenciaEntity inasistenciaEntity = new InasistenciaEntity();
        inasistenciaEntity.setRut("12345678-9");
        inasistenciaEntity.setFecha("2020-01-01");
        inasistenciaEntity.setJustificada(0);
        entityManager.persist(inasistenciaEntity);
        entityManager.flush();
        assert(inasistenciaRepository.countByRutAndDate("12345678-9","2020-01-01")==1);
        //assert(inasistenciaService.existe("12345678-9", "2020-01-01")==false);
    }

    @Test
    void seDebeCrearInasistencia(){
        assert(inasistenciaService.seDebeCrearInasistencia(9, 11)==true);
        assert(inasistenciaService.seDebeCrearInasistencia(9, 10)==false);
        assert(inasistenciaService.seDebeCrearInasistencia(10, 10)==true);
    }

    @Test
    void crearInasistencia(){
        InasistenciaEntity inasistenciaEntity = new InasistenciaEntity();
        inasistenciaEntity.setRut("12345678-9");
        inasistenciaEntity.setFecha("2020-01-01");
        inasistenciaEntity.setJustificada(0);
        inasistenciaEntity=inasistenciaService.crearInasistencia( "12345678-9", "2020-01-01");
        assert(inasistenciaEntity.getRut()=="12345678-9");
        assert(inasistenciaEntity.getFecha()=="2020-01-01");
        assert(inasistenciaEntity.getJustificada()==0);
    }
}
