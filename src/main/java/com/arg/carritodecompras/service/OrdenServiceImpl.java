package com.arg.carritodecompras.service;

import com.arg.carritodecompras.model.Orden;
import com.arg.carritodecompras.model.Usuario;
import com.arg.carritodecompras.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenServiceImpl implements IOrdenService{
    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }
    public String generarNumeroOrden(){
        int numero=0;
        String numeroConcatenado="";

        List<Orden> ordenes= findAll();
        List<Integer> numeros= new ArrayList<Integer>();



        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));

        if(ordenes.isEmpty()){
            numero=1;
        }else{
            numero=numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if(numero<10){
            numeroConcatenado="000000000"+String.valueOf(numero);
        }
        else if(numero<100){
            numeroConcatenado="00000000"+String.valueOf(numero);
        }
        else if(numero<1000){
            numeroConcatenado="0000000"+String.valueOf(numero);
        }
        else if(numero<10000){
            numeroConcatenado="000000"+String.valueOf(numero);
        }


        return numeroConcatenado;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Orden> findById(Long id) {
        return ordenRepository.findById(id);
    }


}
