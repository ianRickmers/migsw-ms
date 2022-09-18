package edu.migswms.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.migswms.entities.MarcaEntity;
import edu.migswms.repositories.MarcaRepository;

@Service
public class MarcaService {
    
    @Autowired
    MarcaRepository marcasRepository;

    public ArrayList<MarcaEntity> obtenerMarca(){
        return (ArrayList<MarcaEntity>) marcasRepository.findAll();
    }

    public MarcaEntity guardarMarca(MarcaEntity marcas){
        return marcasRepository.save(marcas);
    }

    public Optional<MarcaEntity> obtenerPorId(Long id){
        return marcasRepository.findById(id);
    }

    public boolean eliminarMarca(Long id) {
        try{
            marcasRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }

    public ArrayList<MarcaEntity> obtenerMarcaPorRut(String rut){
        return marcasRepository.findByRutCustomQuery(rut);
    }
/*     public ArrayList<MarcasEntity> obtenerMarcasPorNombre(String nombre){
        return marcasRepository.findByRut(rut);
    } */
    public void subirMarca(String data){
        String[] datoEntrada=data.split(";");
            String[] horaMinuto=datoEntrada[1].split(":");
            MarcaEntity marca=new MarcaEntity(null, datoEntrada[0], horaMinuto[0], horaMinuto[1], datoEntrada[2]);
            guardarMarca(marca);
    }

    public String obtenerMarcas() throws FileNotFoundException, IOException{
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