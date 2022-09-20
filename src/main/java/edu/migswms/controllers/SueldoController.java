package edu.migswms.controllers;

import edu.migswms.entities.EmpleadoEntity;
import edu.migswms.entities.SueldoEntity;
import edu.migswms.repositories.InasistenciaRepository;
import edu.migswms.services.DescuentoService;
import edu.migswms.services.EmpleadoService;
import edu.migswms.services.HoraExtraService;
import edu.migswms.services.SueldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sueldos")
public class SueldoController {

    @Autowired
    private SueldoService sueldoService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private HoraExtraService horaExtraService;

    @Autowired
    private DescuentoService descuentoService;

    @Autowired
    private InasistenciaRepository inasistenciaService;

    @GetMapping("/listar")
    public String listar(Model model){
        List<SueldoEntity>sueldos=sueldoService.getAllSueldos();
        model.addAttribute("sueldos",sueldos);
        return "sueldo/listar";
    }

    @GetMapping("/upload")
    public String upload() throws ParseException{
        sueldoService.resetearSueldos();
        ArrayList<EmpleadoEntity> empleados = empleadoService.obtenerEmpleados();
        for(EmpleadoEntity empleado:empleados){
            String rut=empleado.getRut();
            SueldoEntity sueldo=new SueldoEntity(null,empleado.getRut(),empleado.getNombres(),empleado.getApellidos(),0,0,0,0,0,0,0,0,0);
            sueldo.setSueldoFijo(sueldoService.calcularSueldoBase(empleado,sueldo));
            ArrayList<Integer> bonoAndSueldo=sueldoService.calcularBonificacionTiempoServicio(empleado, sueldo);
            sueldo.setAnosServicio(bonoAndSueldo.get(0));
            sueldo.setBonificacionAnosServicio(bonoAndSueldo.get(1));
            if (horaExtraService.obtenerPorRut(rut).size()!=0)
                sueldo=sueldoService.montoHorasExtra(empleado,horaExtraService.obtenerPorRut(rut).get(0),sueldo);
            if (descuentoService.obtenerPorRut(rut).size()!=0)
                sueldo=sueldoService.montoDescuentos(inasistenciaService.countInasistencias(rut),descuentoService.obtenerPorRut(rut).get(0),sueldo);
            sueldo=sueldoService.calcularSueldoNeto(sueldo);
            sueldo=sueldoService.calcularCotizacionPrevisional(sueldo);
            sueldo=sueldoService.calcularCotizacionSalud(sueldo);
            sueldo=sueldoService.calcularMontoFinal(sueldo);
            sueldoService.addSueldo(sueldo);
        }
        return "redirect:/";
    }
}
