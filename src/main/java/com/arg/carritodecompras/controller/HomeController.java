package com.arg.carritodecompras.controller;

import com.arg.carritodecompras.model.Producto;
import com.arg.carritodecompras.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Period;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger logg= LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", productoService.findAll());

        return "usuario/home";
    }
    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Long id, Model model){
        logg.info("ID producto enviado como parametro {}", id);
        Producto producto= new Producto();
        Optional <Producto> productoOptional= productoService.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }
}
