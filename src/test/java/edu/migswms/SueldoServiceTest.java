package edu.migswms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import edu.migswms.entities.DescuentoEntity;
import edu.migswms.entities.EmpleadoEntity;
import edu.migswms.entities.HoraExtraEntity;
import edu.migswms.entities.SueldoEntity;
import edu.migswms.services.SueldoService;

@TestPropertySource(locations="classpath:test.properties")
public class SueldoServiceTest {
    
    SueldoService sueldoService = new SueldoService();
    SueldoEntity sueldoEntity = new SueldoEntity();
    EmpleadoEntity empleadoEntity = new EmpleadoEntity();
    HoraExtraEntity horaExtraEntity = new HoraExtraEntity();
    DescuentoEntity descuentoEntity = new DescuentoEntity();
    
    @Test
    void calcularSueldoBase() {
        empleadoEntity.setRut("12.345.678-9");
        empleadoEntity.setNombres("Juan");
        empleadoEntity.setApellidos("Perez");
        empleadoEntity.setCategoria("A");
        empleadoEntity.setFechaIngreso("2019-01-01");
        Integer sueldo = sueldoService.calcularSueldoBase(empleadoEntity);
        assert(sueldo==1700000);

        empleadoEntity.setCategoria("B");
        sueldo = sueldoService.calcularSueldoBase(empleadoEntity);
        assert(sueldo==1200000);

        empleadoEntity.setCategoria("C");
        sueldo = sueldoService.calcularSueldoBase(empleadoEntity);
        assert(sueldo==800000);
    }
    
    @Test
    void calcularBonificacionTiempoServicio() throws ParseException {
        sueldoEntity.setRut("12.345.678-9");
        sueldoEntity.setCategoria("A");
        sueldoEntity.setSueldoFijo(1700000);
        sueldoEntity.setAnosServicio(5);
        empleadoEntity.setRut("12.345.678-9");
        empleadoEntity.setNombres("Juan");
        empleadoEntity.setApellidos("Perez");
        empleadoEntity.setCategoria("A");
        empleadoEntity.setFechaIngreso("2019-01-01");
        String date_string = "2026-01-01";
        Date fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        ArrayList <Integer> sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        Integer anosServicio=sueldo.get(0);
        Integer bonificacionAnosServicio=sueldo.get(1);
        assert(anosServicio==7 && bonificacionAnosServicio==85000);

        date_string = "2022-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assert(anosServicio==3 && bonificacionAnosServicio==0);

        date_string = "2031-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assert(anosServicio==12 && bonificacionAnosServicio==136000);

        date_string = "2037-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assert(anosServicio==18 && bonificacionAnosServicio==187000);

        date_string = "2041-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assert(anosServicio==22 && bonificacionAnosServicio==238000);

        date_string = "2050-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assert(anosServicio==31 && bonificacionAnosServicio==289000);
    }
    
    @Test
    void montoHorasExtra(){

        horaExtraEntity.setRut("12.345.678-9");
        horaExtraEntity.setCantidadHoras(4);
        horaExtraEntity.setCantidadMinutos(43);
        horaExtraEntity.setAutorizada(1);

        sueldoEntity.setRut("12.345.678-9");
        sueldoEntity.setCategoria("A");
        sueldoEntity.setSueldoFijo(1700000);
        sueldoEntity.setAnosServicio(5);
        sueldoEntity.setBonificacionAnosServicio(85000);

        sueldoEntity = sueldoService.montoHorasExtra( horaExtraEntity, sueldoEntity);
        assert(sueldoEntity.getMontoHorasExtra()==100000);

        sueldoEntity.setCategoria("B");
        sueldoEntity = sueldoService.montoHorasExtra( horaExtraEntity, sueldoEntity);
        assert(sueldoEntity.getMontoHorasExtra()==80000);

        sueldoEntity.setCategoria("C");
        sueldoEntity = sueldoService.montoHorasExtra( horaExtraEntity, sueldoEntity);
        assert(sueldoEntity.getMontoHorasExtra()==40000);

        horaExtraEntity.setAutorizada(0);
        sueldoEntity = sueldoService.montoHorasExtra( horaExtraEntity, sueldoEntity);
        assert(sueldoEntity.getMontoHorasExtra()==0);
    }

    @Test
    void montoDescuentos(){
        Integer inasistencias=2;
        descuentoEntity.setRut("12.345.678-9");
        descuentoEntity.setDesc10(2);
        descuentoEntity.setDesc25(1);
        descuentoEntity.setDesc45(1);

        sueldoEntity.setRut("12.345.678-9");

        sueldoEntity.setCategoria("A");
        sueldoEntity.setSueldoFijo(1700000);
        sueldoEntity.setAnosServicio(5);
        sueldoEntity.setBonificacionAnosServicio(85000);
        sueldoEntity= sueldoService.montoDescuentos(inasistencias, descuentoEntity, sueldoEntity);
        assert(sueldoEntity.getMontoDescuentos()==697000);
    }

    @Test
    void calcularSueldoNeto(){
        sueldoEntity.setRut("12.345.678-9");
        sueldoEntity.setCategoria("A");
        sueldoEntity.setSueldoFijo(1700000);
        sueldoEntity.setAnosServicio(5);
        sueldoEntity.setBonificacionAnosServicio(85000);
        sueldoEntity.setMontoHorasExtra(100000);
        sueldoEntity.setMontoDescuentos(697000);
        sueldoEntity= sueldoService.calcularSueldoNeto(sueldoEntity);
        assert(sueldoEntity.getSueldoBruto()==1188000);
    }

    @Test
    void calcularCotizacionPrevisional(){
        sueldoEntity.setRut("12.345.678-9");
        sueldoEntity.setCategoria("A");
        sueldoEntity.setSueldoFijo(1700000);
        sueldoEntity.setAnosServicio(5);
        sueldoEntity.setBonificacionAnosServicio(85000);
        sueldoEntity.setMontoHorasExtra(100000);
        sueldoEntity.setMontoDescuentos(697000);
        sueldoEntity.setSueldoBruto(1188000);
        sueldoEntity= sueldoService.calcularCotizacionPrevisional(sueldoEntity);
        assert(sueldoEntity.getCotizacionPrevisional()==118800);
    }

    @Test
    void calcularCotizacionSalud(){
        sueldoEntity.setRut("12.345.678-9");
        sueldoEntity.setCategoria("A");
        sueldoEntity.setSueldoFijo(1700000);
        sueldoEntity.setAnosServicio(5);
        sueldoEntity.setBonificacionAnosServicio(85000);
        sueldoEntity.setMontoHorasExtra(100000);
        sueldoEntity.setMontoDescuentos(697000);
        sueldoEntity.setSueldoBruto(1188000);
        sueldoEntity= sueldoService.calcularCotizacionSalud(sueldoEntity);
        assert(sueldoEntity.getCotizacionSalud()==95040);
    }

    @Test
    void calcularMontoFinal(){
        sueldoEntity.setRut("12.345.678-9");
        sueldoEntity.setCategoria("A");
        sueldoEntity.setSueldoFijo(1700000);
        sueldoEntity.setAnosServicio(5);
        sueldoEntity.setBonificacionAnosServicio(85000);
        sueldoEntity.setMontoHorasExtra(100000);
        sueldoEntity.setMontoDescuentos(697000);
        sueldoEntity.setSueldoBruto(1188000);
        sueldoEntity.setCotizacionPrevisional(118800);
        sueldoEntity.setCotizacionSalud(95040);
        sueldoEntity= sueldoService.calcularMontoFinal(sueldoEntity);
        assert(sueldoEntity.getMontoFinal()==974160);
    }
}
