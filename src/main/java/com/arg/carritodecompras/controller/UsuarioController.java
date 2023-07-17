package com.arg.carritodecompras.controller;

import com.arg.carritodecompras.model.Orden;
import com.arg.carritodecompras.model.Usuario;
import com.arg.carritodecompras.service.IOrdenService;
import com.arg.carritodecompras.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger logger= LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IOrdenService ordenService;

    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }
    @PostMapping("/save")
    public String save(Usuario usuario){
        usuario.setTipo("USER");
        usuarioService.save(usuario);
        return "redirect:/";
    }
    @GetMapping("/login")
    public  String login(){
        return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) {
        logger.info("Accesos : {}", usuario);

        Optional<Usuario> user=usuarioService.findByEmail(usuario.getEmail());
        //logger.info("Usuario de db: {}", user.get());

        if (user.isPresent()) {
            session.setAttribute("idusuario", user.get().getId());

            if (user.get().getTipo().equals("ADMIN")) {
                return "redirect:/administrador";
            }else {
                return "redirect:/";
            }
        }else {
            logger.info("Usuario no existe");
        }

        return "redirect:/";
    }
    @GetMapping("/compras")
    public String obtenerCompras(HttpSession session, Model model){
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        Usuario usuario= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        List<Orden>ordenes = ordenService.findByUsuario(usuario);
        model.addAttribute("ordenes", ordenes);
        return "usuario/compras";
    }
    @GetMapping("/detalle/{id}")
    public  String detalleCompra(@PathVariable Long id, HttpSession session, Model model ){
        Optional<Orden> orden=ordenService.findById(id);

        model.addAttribute("detalles", orden.get().getDetalle());


        //session
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "usuario/detallecompra";
    }




}
