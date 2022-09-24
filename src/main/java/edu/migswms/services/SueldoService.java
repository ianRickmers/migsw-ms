package edu.migswms.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import edu.migswms.entities.DescuentoEntity;
import edu.migswms.entities.EmpleadoEntity;
import edu.migswms.entities.HoraExtraEntity;
import edu.migswms.entities.SueldoEntity;

@Service
public class SueldoService {


    public Integer calcularSueldoBase(EmpleadoEntity empleado, SueldoEntity sueldo) {
        String categoria = empleado.getCategoria();
        Integer sueldoBase = 0;
        if (categoria.equals("A")) {
            sueldoBase = 1700000;
            return sueldoBase;
        } else if (categoria.equals("B")) {
            sueldoBase = 1200000;
            return sueldoBase;
        } else if (categoria.equals("C")) {
            sueldoBase = 800000;
            return sueldoBase;
        }
        return sueldoBase;
    }

    // Calcula la bonificacion por antiguedad de un empleado
    public ArrayList<Integer> calcularBonificacionTiempoServicio(EmpleadoEntity empleado, SueldoEntity sueldo)
            throws ParseException {
        Integer bonificacion = 0;
        ArrayList<Integer> anosAndBono = new ArrayList<>();
        Date fechaActual = new Date();
        Date fechaIngreso = new SimpleDateFormat("yyyy-MM-dd").parse(empleado.getFechaIngreso());
        Integer diff = (int) ((TimeUnit.DAYS.convert((fechaActual.getTime() - fechaIngreso.getTime()),
                TimeUnit.MILLISECONDS)) / 365);
        if (diff < 5) {
            bonificacion = 0;
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 5 && diff < 10) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.05);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 10 && diff < 15) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.08);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 15 && diff < 20) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.11);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 20 && diff < 25) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.14);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        } else if (diff >= 25) {
            bonificacion = (int) Math.round(sueldo.getSueldoFijo() * 0.17);
            anosAndBono.add(diff);
            anosAndBono.add(bonificacion);
            return anosAndBono;
        }
        return anosAndBono;
    }

    public SueldoEntity montoHorasExtra(EmpleadoEntity empleado, HoraExtraEntity horaExtra, SueldoEntity sueldo) {
        if (horaExtra.getAutorizada() == 0) {
            sueldo.setMontoHorasExtra(0);
            return sueldo;
        }
        if (horaExtra.getAutorizada() == 1) {
            String categoria = empleado.getCategoria();
            if (categoria.equals("A")) {
                sueldo.setMontoHorasExtra(horaExtra.getCantidadHoras() * 25000);
                return sueldo;
            } else if (categoria.equals("B")) {
                sueldo.setMontoHorasExtra(horaExtra.getCantidadHoras() * 20000);
                return sueldo;
            } else if (categoria.equals("C")) {
                sueldo.setMontoHorasExtra(horaExtra.getCantidadHoras() * 10000);
                return sueldo;
            }
        }
        return sueldo;
    }

    public SueldoEntity montoDescuentos(Integer inasistencias, DescuentoEntity descuento, SueldoEntity sueldo) {
        Integer sueldoBase = sueldo.getSueldoFijo();
        double montoDescuento10 = descuento.getDesc10() * 0.01 * sueldoBase;
        double montoDescuento25 = descuento.getDesc25() * 0.03 * sueldoBase;
        double montoDescuento45 = descuento.getDesc45() * 0.06 * sueldoBase;
        Integer montoDescuento = (int) Math
                .round((inasistencias * 0.15 * sueldoBase) + montoDescuento10 + montoDescuento25 + montoDescuento45);
        sueldo.setMontoDescuentos(montoDescuento);
        return sueldo;
    }

    public SueldoEntity calcularSueldoNeto(SueldoEntity sueldo) {
        Integer sueldoNeto = sueldo.getSueldoFijo() + sueldo.getBonificacionAnosServicio() + sueldo.getMontoHorasExtra()
                - sueldo.getMontoDescuentos();
        sueldo.setSueldoBruto(sueldoNeto);
        return sueldo;
    }

    public SueldoEntity calcularCotizacionPrevisional(SueldoEntity sueldo) {
        Integer cotizacionPrevisional = (int) Math.round(sueldo.getSueldoBruto() * 0.1);
        sueldo.setCotizacionPrevisional(cotizacionPrevisional);
        return sueldo;
    }

    public SueldoEntity calcularCotizacionSalud(SueldoEntity sueldo) {
        Integer cotizacionSalud = (int) Math.round(sueldo.getSueldoBruto() * 0.08);
        sueldo.setCotizacionSalud(cotizacionSalud);
        return sueldo;
    }

    public SueldoEntity calcularMontoFinal(SueldoEntity sueldo) {
        Integer montoFinal = sueldo.getSueldoBruto() - sueldo.getCotizacionPrevisional() - sueldo.getCotizacionSalud();
        sueldo.setMontoFinal(montoFinal);
        return sueldo;
    }
}
