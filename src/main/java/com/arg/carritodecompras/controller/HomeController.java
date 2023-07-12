package com.arg.carritodecompras.controller;
import com.arg.carritodecompras.model.DetalleOrden;
import com.arg.carritodecompras.model.Orden;
import com.arg.carritodecompras.model.Producto;
import com.arg.carritodecompras.model.Usuario;
import com.arg.carritodecompras.service.IDetalleOrdenService;
import com.arg.carritodecompras.service.IOrdenService;
import com.arg.carritodecompras.service.IUsuarioService;
import com.arg.carritodecompras.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger logg= LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;

    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    private IDetalleOrdenService detalleOrdenService;

    //Para almacenar los detalles de las odenes
    List<DetalleOrden> detalles= new ArrayList<DetalleOrden>();

    //Datos de la orden
    Orden orden= new Orden();
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
    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoService.get(Long.valueOf(id));
        logg.info("Producto añadido: {}", optionalProducto.get());
        logg.info("Cantidad: {}", cantidad);
        producto = optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);


        //validar que el producto no sea añadido dos veces
        Long idProducto=producto.getId();
        boolean ingresado=detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);

        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }
    //quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Long id, Model model) {

        // lista nueva de prodcutos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenesNueva.add(detalleOrden);
            }
        }

        // poner la nueva lista con los productos restantes
        detalles = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "/usuario/carrito";
    }
    @GetMapping("getCart")
    public String getCart(Model model){

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "/usuario/carrito";
    }
    @GetMapping("/order")
    public String order(Model model){
        Usuario usuario= usuarioService.findById(2).get();
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return "usuario/resumenorden";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(){
        Date fechaCreacion= new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        //Usuario
       // Usuario usuario= usuarioService.findById(Integer.parseInt(String.valueOf(2))).get();

        //orden.setUsuario(usuario);
        ordenService.save(orden);

        //Guardar detalles

        for(DetalleOrden dt: detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }

        //Limpiar la lista y orden
        orden = new Orden();
        detalles.clear();

        return "redirect:/";

    }
    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model){
        logg.info("Nombe del producto: ", nombre);
        List<Producto> productos= productoService.findAll().stream().filter(p-> p.getNombre().contains(nombre)).collect(Collectors.toList());

        model.addAttribute("productos", productos);
        return "usuario/home";
    }
}
