package edu.migswms.controllers;

import edu.migswms.entities.DescuentoEntity;
import edu.migswms.entities.EmpleadoEntity;
import edu.migswms.entities.InasistenciaEntity;
import edu.migswms.entities.MarcaEntity;
import edu.migswms.repositories.DescuentoRepository;
import edu.migswms.repositories.EmpleadoRepository;
import edu.migswms.repositories.InasistenciaRepository;
import edu.migswms.repositories.MarcaRepository;
import edu.migswms.services.DescuentoService;
import edu.migswms.services.InasistenciaService;

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
    DescuentoRepository descuentoRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    InasistenciaRepository inasistenciaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    DescuentoService descuentoService;

    @Autowired
    InasistenciaService inasistenciaService;

    @GetMapping("/upload")
    public String calcularDescuentos(){
        descuentoRepository.deleteAll();
        ArrayList<EmpleadoEntity>empleados=(ArrayList<EmpleadoEntity>) empleadoRepository.findAll();
        for(EmpleadoEntity empleado:empleados){
            String rut=empleado.getRut();
            ArrayList<MarcaEntity>marcasRut=marcaRepository.findByRut(rut);
            int n = marcasRut.size();
            for(int i=0;i<n;i+=2){
                int marcaHora=Integer.parseInt((marcasRut.get(i)).getHora());
                int marcaMinuto=Integer.parseInt((marcasRut.get(i)).getMinuto());
                ArrayList<DescuentoEntity>descuentos=descuentoRepository.findByRut(rut);
                if(!descuentos.isEmpty()){
                    DescuentoEntity descuento=descuentos.get(0);
                    descuentoService.cambiarDescuentos(marcaHora, marcaMinuto,descuento);
                    descuentoRepository.save(descuento);
                }
                else{
                    DescuentoEntity descuento=new DescuentoEntity(null,rut,0,0,0);
                    descuentoService.cambiarDescuentos(marcaHora, marcaMinuto,descuento);
                    descuentoRepository.save(descuento);
                }
                if(inasistenciaService.seDebeCrearInasistencia(marcaHora, marcaMinuto) && inasistenciaRepository.countByRutAndDate(rut, (marcasRut.get(i).getFecha())) == 0){
                    InasistenciaEntity inasistenciaEntity=inasistenciaService.crearInasistencia( rut, (marcasRut.get(i).getFecha()));
                    inasistenciaRepository.save(inasistenciaEntity);
                }
            }
        }
        return("redirect:/");
    }
    
    @GetMapping("/listar")
        public String listar(Model model){
            ArrayList<DescuentoEntity>descuentos=(ArrayList<DescuentoEntity>) descuentoRepository.findAll();
            model.addAttribute("descuentos",descuentos);

            return "descuento/listar";
    }
}
