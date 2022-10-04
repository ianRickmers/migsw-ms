package edu.migswms.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.migswms.entities.MarcaEntity;
import edu.migswms.repositories.MarcaRepository;

@Service
public class MarcaService {

    @Autowired
    MarcaRepository marcasRepository;

    public MarcaEntity subirMarca(String data) throws ParseException {
        String[] datoEntrada = data.split(";");
        String[] horaMinuto = datoEntrada[1].split(":");
        final String viejoFormato = "yyyy/mm/dd";
        final String nuevoFormato = "yyyy-mm-dd";
        SimpleDateFormat format = new SimpleDateFormat(viejoFormato);
        Date fecha = format.parse(datoEntrada[0]);
        format.applyPattern(nuevoFormato);
        String nuevaFecha = format.format(fecha);
        return new MarcaEntity(null, nuevaFecha, horaMinuto[0], horaMinuto[1], datoEntrada[2]);
    }

    public void obtenerMarcas() throws IOException, ParseException {
        Logger logger = Logger.getAnonymousLogger();
        String error = "No se pudo guardar el archivo";
        FileReader fr = new FileReader("/data.txt");
        BufferedReader br = new BufferedReader(fr);
        try {
            String i;
            while ((i = br.readLine()) != null) {
                marcasRepository.save(subirMarca(i));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, error, e);
        } finally {
            br.close();
            fr.close();
        }
    }
}