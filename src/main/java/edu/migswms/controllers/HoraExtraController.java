package edu.migswms.controllers;

import edu.migswms.entities.HoraExtraEntity;
import edu.migswms.services.HoraExtraService;
import edu.migswms.services.UploadService;
import edu.migswms.entities.EmpleadoEntity;
import edu.migswms.entities.MarcaEntity;
import edu.migswms.services.EmpleadoService;
import edu.migswms.services.MarcaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/horas_extras")
public class HoraExtraController {

    @Autowired
    HoraExtraService horaExtraService;

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    MarcaService marcaService;

    @Autowired
    private UploadService upload;

    @GetMapping("/listar")
    public String listar(Model model){
        List<HoraExtraEntity>horasExtras=horaExtraService.obtenerHorasExtras();
        System.out.println(horasExtras);
        model.addAttribute("horasExtras",horasExtras);
        return "hora_extra/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("horaExtra",new HoraExtraEntity());
        return "hora_extra/form";
    }

    @PostMapping("/guardar")
    public String crear(HoraExtraEntity horaExtra){
        horaExtraService.guardarHoraExtra(horaExtra);
        return "redirect:/horas_extras/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable long id, Model model){
        Optional<HoraExtraEntity> horaExtra=horaExtraService.obtenerPorId(id);
        model.addAttribute("horaExtra",horaExtra);
        return "hora_extra/form";

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable long id){
        horaExtraService.eliminarHoraExtra(id);
        return "redirect:/horas_extras/listar";
    }

    @GetMapping("/upload")
    public String home() {
		return "hora_extra/upload";
	}

    @PostMapping("/cargar")
    public String carga( @RequestParam("archivos") MultipartFile file, RedirectAttributes ms) {
		upload.saveAutorizacion(file);
		ms.addFlashAttribute("mensaje", "Archivo guardado correctamente");
		return "redirect:/hora_extra/upload";
	}

    @GetMapping("/calcular")
    public String calcularDescuentos(){
        ArrayList<EmpleadoEntity>empleados=empleadoService.obtenerEmpleados();
        for(EmpleadoEntity empleado:empleados){
            String rut=empleado.getRut();
            ArrayList<MarcaEntity>marcasRut=marcaService.obtenerMarcaPorRut(rut);
            int n = marcasRut.size();
            for(int i=1;i<n;i+=2){
                int marcaHora=Integer.parseInt((marcasRut.get(i)).getHora());
                int marcaMinuto=Integer.parseInt((marcasRut.get(i)).getMinuto());
                horaExtraService.cambiarHorasExtra(marcaHora, marcaMinuto,rut);
                }
            }
        return("redirect:/");
    }
}
