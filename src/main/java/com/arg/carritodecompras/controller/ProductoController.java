package com.arg.carritodecompras.controller;

import com.arg.carritodecompras.model.Producto;
import com.arg.carritodecompras.model.Usuario;
import com.arg.carritodecompras.service.IUsuarioService;
import com.arg.carritodecompras.service.ProductoService;
import com.arg.carritodecompras.service.UploadFileService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final Logger LOGGER= LoggerFactory.getLogger(ProductoController.class);
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UploadFileService upload;
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("")
    //Model lleva informacion desde el backend hacia la vista
    public String show(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "productos/show";
    }
    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }
    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Este es el objeto producto {}", producto);

        Usuario u= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        producto.setUsuario(u);

        //Imagen
        if (producto.getId()==null){ //Cuando se crea un producto
            String nombreImagen= upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        else{

        }

        productoService.save(producto);
        return "redirect:/productos";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Producto producto= new Producto();
        Optional<Producto> optionalProducto= productoService.get(id);
        producto=optionalProducto.get();
        LOGGER.info("Producto buscado: ",producto);
        model.addAttribute("producto", producto);
        return "productos/edit";
    }
    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        if(file.isEmpty()){ //Cuando editamos un producto pero no cambiamos la imagen
            Producto p = new Producto();
            p= productoService.get(producto.getId()).get();
            producto.setImagen(p.getImagen());
        }
        else {//CUnado se edita tambien la imagen

            Producto p= new Producto();
            p= productoService.get(producto.getId()).get();

            //Eliminar cuando la imagen no sea por defecto
            if(!p.getImagen().equals("default.jpg")){
                upload.deleteImage(p.getImagen());
            }

            String nombreImagen= upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        productoService.update(producto);
        return "redirect:/productos";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        Producto p= new Producto();
        p= productoService.get(id).get();

        //Eliminar cuando la imagen no sea por defecto
        if(!p.getImagen().equals("default.jpg")){
            upload.deleteImage(p.getImagen());
        }

        productoService.delete(id);
        return "redirect:/productos";
    }


}
