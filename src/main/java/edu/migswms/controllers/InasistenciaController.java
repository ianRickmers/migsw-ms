package edu.migswms.controllers;

import edu.migswms.UploadHelper;
import edu.migswms.entities.InasistenciaEntity;
import edu.migswms.repositories.InasistenciaRepository;

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
import java.util.Optional;

@Controller
@RequestMapping("/inasistencias")
public class InasistenciaController {
    
    @Autowired
    InasistenciaRepository inasistenciaRepository;

    @Autowired
	private UploadHelper upload;
    
    @GetMapping("/listar")
    public String listar(Model model){
        ArrayList<InasistenciaEntity>inasistencias=(ArrayList<InasistenciaEntity>) inasistenciaRepository.findAll();
        model.addAttribute("inasistencias",inasistencias);
        return "inasistencia/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("inasistencia",new InasistenciaEntity());
        return "inasistencia/form";
    }

    @PostMapping("/guardar")
    public String crear(InasistenciaEntity inasistencia){
        inasistenciaRepository.save(inasistencia);
        return "redirect:/inasistencias/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable long id, Model model){
        Optional<InasistenciaEntity> inasistencia=inasistenciaRepository.findById(id);
        model.addAttribute("inasistencia",inasistencia);
        return "inasistencia/form";

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable long id){
        inasistenciaRepository.deleteById(id);
        return "redirect:/inasistencias/listar";
    }
	
	@GetMapping("/upload")
	public String home() {
        return "inasistencia/upload";
	}
	
	@PostMapping("/cargar")
	public String carga( @RequestParam("archivos") MultipartFile file, RedirectAttributes ms) {
		upload.saveData(file);
		ms.addFlashAttribute("mensaje", "Archivo guardado correctamente");
		return "redirect:/inasistencias/upload";
	}
}