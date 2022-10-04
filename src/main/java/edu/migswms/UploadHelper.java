package edu.migswms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadHelper {
	String msg ="Archivo guardado";
	String error="No se pudo guardar el archivo";
	private final Logger logg = LoggerFactory.getLogger(UploadHelper.class);
	public String saveData(MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte [] bytes= file.getBytes();
				String folder= "/";
				Path path = Paths.get( folder+file.getOriginalFilename() );
				Files.write(path, bytes);				
				logg.info(msg);
				
			} catch (IOException e) {
				logg.error(error);
			}
		}
		return msg;
	}
}