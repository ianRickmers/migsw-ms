package edu.migswms.services;

import edu.migswms.entities.EmpleadoEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RRHH {

    /* 
    public List<EmpleadoEntity> leerArchivo() {
        return null;
    }*/

    // Calcula el sueldo base de un empleado
    public double calcularSueldoBase(EmpleadoEntity empleado){
        String categoria = empleado.getCategoria();
        double sueldoBase = 0;
        if(categoria.equals("A")){
            return 1700000;
        } else if(categoria.equals("B")){
            return 1200000;
        } else if(categoria.equals("C")){
            return 800000;
        }
        return sueldoBase;
    }

    //Calcula la bonificacion por antiguedad de un empleado
    public double calcularBonificacionTiempoServicio(EmpleadoEntity empleado) throws ParseException{
        double bonificacion = 0;
        Date fechaActual = new Date();
        Date fechaIngreso = new SimpleDateFormat("yyyy-MM-dd").parse(empleado.getFechaIngreso());  
        double diff = (fechaIngreso.getTime() - fechaActual.getTime())/ (1000 * 60 * 60 * 24 * 365);
        if(diff<5){
            return bonificacion = 0;
        }
        if(diff>=5 && diff<10){
            return bonificacion = calcularSueldoBase(empleado)*0.05;
        }
        if(diff>=10 && diff<15){
            return bonificacion = calcularSueldoBase(empleado)*0.08;
        }
        if(diff>=15 && diff<20){
           return bonificacion = calcularSueldoBase(empleado)*0.11;
        }
        if(diff>=20 && diff<25){
           return bonificacion = calcularSueldoBase(empleado)*0.14;
        }
        if(diff>=25){
           return bonificacion = calcularSueldoBase(empleado)*0.17;
        }
        return bonificacion;
    }

    // calcula el descuento por cotizacion previsional
    public Double calcularDescuentoPension(EmpleadoEntity empleado){
        Double descuento = calcularSueldoBase(empleado)*0.1;
        return descuento;
    }

    // calcula el descuento por salud
    public Double calcularDescuentoSalud(EmpleadoEntity empleado){
        Double descuento = calcularSueldoBase(empleado)*0.08;
        return descuento;
    }
    
    //Calcula el sueldo final del empleado
    public double calcularSueldoFinal(EmpleadoEntity empleado) throws ParseException{
        double sueldoFinal = 0;
        sueldoFinal = calcularSueldoBase(empleado) + calcularBonificacionTiempoServicio(empleado) - calcularDescuentoSalud(empleado) - calcularDescuentoPension(empleado);
        return sueldoFinal;
    }
}
