package com.arg.carritodecompras.controller;


import com.arg.carritodecompras.model.Producto;
import com.arg.carritodecompras.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    @Autowired
    private ProductoService productoService;
    @GetMapping("")
    public String home(Model model){
        //Obtencion dep roductos al hacer la peticion
        List<Producto> productos= productoService.findAll();
        model.addAttribute("productos", productos);

        return "administrador/home";
    }
}
