package edu.migswms.controllers;

import edu.migswms.entities.DescuentoEntity;
import edu.migswms.entities.EmpleadoEntity;
import edu.migswms.entities.MarcaEntity;
import edu.migswms.services.DescuentoService;
import edu.migswms.services.EmpleadoService;
import edu.migswms.services.InasistenciaService;
import edu.migswms.services.MarcaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/descuentos")
public class DescuentoController {

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    MarcaService marcaService;

    @Autowired
    DescuentoService descuentoService;

    @Autowired
    InasistenciaService inasistenciaService;

    @GetMapping("/upload")
    public String calcularDescuentos(){
        ArrayList<EmpleadoEntity>empleados=empleadoService.obtenerEmpleados();
        for(EmpleadoEntity empleado:empleados){
            String rut=empleado.getRut();
            ArrayList<MarcaEntity>marcasRut=marcaService.obtenerMarcaPorRut(rut);
            int n = marcasRut.size();
            for(int i=0;i<n;i+=2){
                int marcaHora=Integer.parseInt((marcasRut.get(i)).getHora());
                int marcaMinuto=Integer.parseInt((marcasRut.get(i)).getMinuto());
                descuentoService.cambiarDescuentos(marcaHora, marcaMinuto,rut);
                if(inasistenciaService.existe(rut, (marcasRut.get(i)).getFecha()))
                    inasistenciaService.crearInasistencia( marcaHora, marcaMinuto, rut, (marcasRut.get(i)));
                }
            }
        return("redirect:/");
    }
    
    @GetMapping("/listar")
        public String listar(Model model){
            ArrayList<DescuentoEntity>descuentos=descuentoService.obtenerDescuentos();
            model.addAttribute("descuentos",descuentos);

            return "descuento/listar";
    }
}
