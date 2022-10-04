package edu.migswms.controllers;

import edu.migswms.entities.DescuentoEntity;
import edu.migswms.entities.EmpleadoEntity;
import edu.migswms.entities.HoraExtraEntity;
import edu.migswms.entities.SueldoEntity;
import edu.migswms.repositories.DescuentoRepository;
import edu.migswms.repositories.EmpleadoRepository;
import edu.migswms.repositories.HoraExtraRepository;
import edu.migswms.repositories.InasistenciaRepository;
import edu.migswms.repositories.SueldoRepository;
import edu.migswms.services.SueldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sueldos")
public class SueldoController {

    @Autowired
    private SueldoService sueldoService;

    @Autowired
    private InasistenciaRepository inasistenciaService;

    @Autowired
    private SueldoRepository sueldoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private HoraExtraRepository horaExtraRepository;

    @Autowired
    private DescuentoRepository descuentoRepository;

    @GetMapping("/listar")
    public String listar(Model model){
        List<SueldoEntity>sueldos=sueldoRepository.findAll();
        model.addAttribute("sueldos",sueldos);
        return "sueldo/listar";
    }

    @GetMapping("/upload")
    public String upload() throws ParseException{
        sueldoRepository.deleteAll();
        ArrayList<EmpleadoEntity> empleados = (ArrayList<EmpleadoEntity>) empleadoRepository.findAll();
        for(EmpleadoEntity empleado:empleados){
            String rut=empleado.getRut();
            SueldoEntity sueldo=new SueldoEntity(null,empleado.getRut(),empleado.getNombres(),empleado.getApellidos(),empleado.getCategoria(),0,0,0,0,0,0,0,0,0);
            sueldo.setSueldoFijo(sueldoService.calcularSueldoBase(empleado));
            Date fechaActual=new Date();
            ArrayList<Integer> bonoAndSueldo=sueldoService.calcularBonificacionTiempoServicio(empleado, sueldo, fechaActual);
            sueldo.setAnosServicio(bonoAndSueldo.get(0));
            sueldo.setBonificacionAnosServicio(bonoAndSueldo.get(1));
            ArrayList<HoraExtraEntity> horasExtras= horaExtraRepository.findByRut(rut);
            ArrayList<DescuentoEntity> descuentos= descuentoRepository.findByRut(rut);
            if (!horasExtras.isEmpty())
                sueldo=sueldoService.montoHorasExtra(horasExtras.get(0),sueldo);
            if (!descuentos.isEmpty())
                sueldo=sueldoService.montoDescuentos(inasistenciaService.countInasistencias(rut),descuentos.get(0),sueldo);
            sueldo=sueldoService.calcularSueldoNeto(sueldo);
            sueldo=sueldoService.calcularCotizacionPrevisional(sueldo);
            sueldo=sueldoService.calcularCotizacionSalud(sueldo);
            sueldo=sueldoService.calcularMontoFinal(sueldo);
            sueldoRepository.save(sueldo);
        }
        return "redirect:/";
    }
}
