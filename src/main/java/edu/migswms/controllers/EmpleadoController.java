package edu.migswms.controllers;


import edu.migswms.entities.EmpleadoEntity;
import edu.migswms.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Optional;

        @Controller
        @RequestMapping("/empleados")
        public class EmpleadoController {

            @Autowired
            EmpleadoService empleadoService;

            @GetMapping("/listar")
            public String listar(Model model){
                ArrayList<EmpleadoEntity>empleados=empleadoService.obtenerEmpleados();
                System.out.println(empleados);
                model.addAttribute("empleados",empleados);
                return "empleado/listar";
            }

            @GetMapping("/nuevo")
            public String nuevo(Model model){
                model.addAttribute("empleado",new EmpleadoEntity());
                return "empleado/form";
            }

            @PostMapping("/guardar")
            public String crear(EmpleadoEntity empleado){
                empleadoService.guardarEmpleado(empleado);
                return "redirect:/empleados/listar";
            }

            @GetMapping("/editar/{id}")
            public String editar(@PathVariable long id, Model model){
                Optional<EmpleadoEntity> empleado=empleadoService.obtenerPorId(id);
                model.addAttribute("empleado",empleado);
                return "empleado/form";

            }

            @GetMapping("/eliminar/{id}")
            public String eliminar(@PathVariable long id){
                empleadoService.eliminarEmpleado(id);
                return "redirect:/empleados/listar";
            }
        }