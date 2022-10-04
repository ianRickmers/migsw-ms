package edu.migswms;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class SueldoServiceTest {
    
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
        assertEquals(1700000,sueldo);

        empleadoEntity.setCategoria("B");
        sueldo = sueldoService.calcularSueldoBase(empleadoEntity);
        assertEquals(1200000,sueldo);

        empleadoEntity.setCategoria("C");
        sueldo = sueldoService.calcularSueldoBase(empleadoEntity);
        assertEquals(800000,sueldo);
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
        assertEquals(7,anosServicio);
        assertEquals(85000,bonificacionAnosServicio);

        date_string = "2022-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assertEquals(3,anosServicio);
        assertEquals(0,bonificacionAnosServicio);

        date_string = "2031-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assertEquals(12,anosServicio);
        assertEquals(136000,bonificacionAnosServicio);

        date_string = "2037-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assertEquals(18,anosServicio);
        assertEquals(187000,bonificacionAnosServicio);

        date_string = "2041-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assertEquals(22,anosServicio);
        assertEquals(238000,bonificacionAnosServicio);

        date_string = "2050-01-01";
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
        sueldo = sueldoService.calcularBonificacionTiempoServicio(empleadoEntity,sueldoEntity,fechaActual);
        anosServicio=sueldo.get(0);
        bonificacionAnosServicio=sueldo.get(1);
        assertEquals(31,anosServicio);
        assertEquals(289000,bonificacionAnosServicio);
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
        assertEquals(100000,sueldoEntity.getMontoHorasExtra());

        sueldoEntity.setCategoria("B");
        sueldoEntity = sueldoService.montoHorasExtra( horaExtraEntity, sueldoEntity);
        assertEquals(80000,sueldoEntity.getMontoHorasExtra());

        sueldoEntity.setCategoria("C");
        sueldoEntity = sueldoService.montoHorasExtra( horaExtraEntity, sueldoEntity);
        assertEquals(40000,sueldoEntity.getMontoHorasExtra());

        horaExtraEntity.setAutorizada(0);
        sueldoEntity = sueldoService.montoHorasExtra( horaExtraEntity, sueldoEntity);
        assertEquals(0,sueldoEntity.getMontoHorasExtra());
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
        assertEquals(697000,sueldoEntity.getMontoDescuentos());
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
        assertEquals(1188000,sueldoEntity.getSueldoBruto());
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
        assertEquals(118800,sueldoEntity.getCotizacionPrevisional());
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
        assertEquals(95040,sueldoEntity.getCotizacionSalud());
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
        assertEquals(974160,sueldoEntity.getMontoFinal());
    }
}
