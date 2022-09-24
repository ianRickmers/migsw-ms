package edu.migswms.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.migswms.entities.MarcaEntity;
import edu.migswms.repositories.MarcaRepository;

@Service
public class MarcaService {
    
    @Autowired
    MarcaRepository marcasRepository;

/*     public ArrayList<MarcasEntity> obtenerMarcasPorNombre(String nombre){
        return marcasRepository.findByRut(rut);
    } */
    public void subirMarca(String data) throws ParseException{
        String[] datoEntrada=data.split(";");
            String[] horaMinuto=datoEntrada[1].split(":");
            final String viejoFormato = "yyyy/mm/dd";
            final String nuevoFormato = "yyyy-mm-dd";
            SimpleDateFormat format = new SimpleDateFormat(viejoFormato);
            Date fecha = format.parse(datoEntrada[0]);
            format.applyPattern(nuevoFormato);
            String nuevaFecha = format.format(fecha);
            MarcaEntity marca=new MarcaEntity(null, nuevaFecha, horaMinuto[0], horaMinuto[1], datoEntrada[2]);
            marcasRepository.save(marca);
    }

    public String obtenerMarcas() throws FileNotFoundException, IOException, ParseException{
        FileReader fr=new FileReader("archives/data/data.txt");
        BufferedReader br=new BufferedReader(fr);
        String data = "";
        String i;    
        while((i=br.readLine())!=null){  
            subirMarca(i);
        }
        br.close();    
        fr.close(); 
        return data;
    }
}