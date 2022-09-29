package edu.migswms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.migswms.UploadHelper;

@Controller
public class ArchivoController {
	
	@Autowired
	private UploadHelper upload;
	
	@GetMapping("/upload")
	public String home() {
		System.out.println(System.getProperty("user.dir"));return "upload";
	}
	
	@PostMapping("/cargar")
	public String carga( @RequestParam("archivos") MultipartFile file, RedirectAttributes ms) {
		upload.saveData(file);
		ms.addFlashAttribute("mensaje", "Archivo guardado correctamente");
		return "redirect:/upload";
	}

}