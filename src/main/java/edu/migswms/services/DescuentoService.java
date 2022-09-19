package edu.migswms.services;

import edu.migswms.entities.DescuentoEntity;
import edu.migswms.repositories.DescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DescuentoService {
    @Autowired
    DescuentoRepository descuentoRepository;
    public ArrayList<DescuentoEntity> obtenerDescuentos(){
        return (ArrayList<DescuentoEntity>) descuentoRepository.findAll();
    }

    public DescuentoEntity guardarDescuento(DescuentoEntity descuento){
        return descuentoRepository.save(descuento);
    }

    public Optional<DescuentoEntity> obtenerPorRut(String rut){
        return descuentoRepository.findByRut(rut);
    }

    public void reset(DescuentoEntity descuentoEntity){
        descuentoEntity.setDesc10(0);
        descuentoEntity.setDesc25(0);
        descuentoEntity.setDesc45(0);
    }
    
    public void cambiarDesc10(String rut){
        Optional<DescuentoEntity> descuento = descuentoRepository.findByRut(rut);
        if(descuento.isPresent()){
            DescuentoEntity descuentoEntity = descuento.get();
            descuentoEntity.setDesc10(descuentoEntity.getDesc10()+1);
            descuentoRepository.save(descuentoEntity);
        }
        else{
            DescuentoEntity descuentoEntity = new DescuentoEntity(null,rut,0,1,0);
            descuentoRepository.save(descuentoEntity);
        }
    }

    public void cambiarDesc25(String rut){
        Optional<DescuentoEntity> descuento = descuentoRepository.findByRut(rut);
        if(descuento.isPresent()){
            DescuentoEntity descuentoEntity = descuento.get();
            descuentoEntity.setDesc25(descuentoEntity.getDesc25()+1);
            descuentoRepository.save(descuentoEntity);
        }
        else{
            DescuentoEntity descuentoEntity = new DescuentoEntity(null,rut,0,0,1);
            descuentoRepository.save(descuentoEntity);
        }
    }

    public void cambiarDesc45(String rut){
        Optional<DescuentoEntity> descuento = descuentoRepository.findByRut(rut);
        if(descuento.isPresent()){
            DescuentoEntity descuentoEntity = descuento.get();
            descuentoEntity.setDesc45(descuentoEntity.getDesc45()+1);
            descuentoRepository.save(descuentoEntity);
        }
        else{
            DescuentoEntity descuentoEntity = new DescuentoEntity(null,rut,0,0,1);
            descuentoRepository.save(descuentoEntity);
        }
    }

    //Para hacer los test unitarios cambiar a retorno del descuento cambiado
    public void cambiarDescuentos(Integer marcaHora, Integer marcaMinuto, String rut){
        if(marcaHora==8 && marcaMinuto>10 && marcaMinuto<=25){
            cambiarDesc10(rut);
        }
        if(marcaHora==8 && marcaMinuto>25 && marcaMinuto<=45){
            cambiarDesc25(rut);
        }
        if(marcaHora==8 && marcaMinuto>45 && marcaMinuto<=59){
            cambiarDesc45(rut);
        }
        if(marcaHora==9 && marcaMinuto>=0 && marcaMinuto<=10){
            cambiarDesc45(rut);
        }
    }
}
