package edu.migswms.controllers;

import edu.migswms.entities.MarcaEntity;
import edu.migswms.repositories.MarcaRepository;
import edu.migswms.services.MarcaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    MarcaService marcaService;

    @GetMapping("/upload")
    public String upload(RedirectAttributes ms) throws IOException {
        try {
            marcaRepository.deleteAll();
            marcaService.obtenerMarcas();
            ms.addFlashAttribute("mensaje", "Archivo guardado correctamente");
            return "redirect:/";
        } catch (Exception e) {
            ms.addFlashAttribute("mensaje", "Error leyendo archivo");
        }
        return "redirect:/";
    }
    @GetMapping("/listar")
    public String listar(Model model){
        List<MarcaEntity>marcas=marcaRepository.findAll();
        model.addAttribute("marcas",marcas);
        return "marca/listar";
    }
}