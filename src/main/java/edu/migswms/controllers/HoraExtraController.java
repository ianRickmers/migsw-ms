package edu.migswms.controllers;

import edu.migswms.entities.HoraExtraEntity;
import edu.migswms.services.HoraExtraService;
import edu.migswms.UploadHelper;
import edu.migswms.entities.EmpleadoEntity;
import edu.migswms.entities.MarcaEntity;
import edu.migswms.repositories.EmpleadoRepository;
import edu.migswms.repositories.HoraExtraRepository;
import edu.migswms.repositories.MarcaRepository;

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
    HoraExtraRepository horaExtraRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    private UploadHelper upload;

    @GetMapping("/listar")
    public String listar(Model model){
        List<HoraExtraEntity>horasExtras=horaExtraRepository.findAll();
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
        horaExtraRepository.save(horaExtra);
        return "redirect:/horas_extras/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable long id, Model model){
        Optional<HoraExtraEntity> horaExtra=horaExtraRepository.findById(id);
        model.addAttribute("horaExtra",horaExtra);
        return "hora_extra/form";

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable long id){
        horaExtraRepository.deleteById(id);
        return "redirect:/horas_extras/listar";
    }

    @GetMapping("/upload")
    public String home() {
		return "hora_extra/upload";
	}

    @PostMapping("/cargar")
    public String carga( @RequestParam("archivos") MultipartFile file, RedirectAttributes ms) {
		upload.saveData(file);
		ms.addFlashAttribute("mensaje", "Archivo guardado correctamente");
		return "redirect:/horas_extras/upload";
	}

    @GetMapping("/calcular")
    public String calcularDescuentos(){
        horaExtraRepository.deleteAll();
        ArrayList<EmpleadoEntity>empleados=(ArrayList<EmpleadoEntity>) empleadoRepository.findAll();
        for(EmpleadoEntity empleado:empleados){
            String rut=empleado.getRut();
            ArrayList<MarcaEntity>marcasRut=marcaRepository.findByRut(rut);
            int n = marcasRut.size();
            HoraExtraEntity horaExtra = new HoraExtraEntity(null,rut,0,0,0);
            for(int i=1;i<n;i+=2){
                int marcaHora=Integer.parseInt((marcasRut.get(i)).getHora());
                int marcaMinuto=Integer.parseInt((marcasRut.get(i)).getMinuto());
                horaExtra=horaExtraService.cambiarHorasExtra(marcaHora, marcaMinuto,horaExtra);
                horaExtraRepository.save(horaExtra);
                }
            }
        return("redirect:/");
    }
}
