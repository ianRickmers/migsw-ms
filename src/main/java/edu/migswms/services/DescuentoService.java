package edu.migswms.services;

import edu.migswms.entities.DescuentoEntity;
import org.springframework.stereotype.Service;

@Service
public class DescuentoService {
    
    public DescuentoEntity cambiarDesc10(DescuentoEntity descuento){
        descuento.setDesc10(descuento.getDesc10()+1);
        return descuento;
    }

    public DescuentoEntity cambiarDesc25(DescuentoEntity descuento){
        descuento.setDesc25(descuento.getDesc25()+1);
        return descuento;
    }

    public DescuentoEntity cambiarDesc45(DescuentoEntity descuento){
        descuento.setDesc45(descuento.getDesc45()+1);
        return descuento;
    }

    //Para hacer los test unitarios cambiar a retorno del descuento cambiado
    public DescuentoEntity cambiarDescuentos(Integer marcaHora, Integer marcaMinuto, DescuentoEntity descuento){
        if(marcaHora==8 && marcaMinuto>10 && marcaMinuto<=25){
            cambiarDesc10(descuento);
            return descuento;
        }
        if(marcaHora==8 && marcaMinuto>25 && marcaMinuto<=45){
            cambiarDesc25(descuento);
            return descuento;
        }
        if(marcaHora==8 && marcaMinuto>45 && marcaMinuto<=59){
            cambiarDesc45(descuento);
            return descuento;
        }
        if(marcaHora==9 && marcaMinuto>=0 && marcaMinuto<=10){
            cambiarDesc45(descuento);
            return descuento;
        }
        return descuento;
    }
}
