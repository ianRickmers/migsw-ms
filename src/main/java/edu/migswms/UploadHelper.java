package edu.migswms;

//import java.io.File;
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
	private final Logger logg = LoggerFactory.getLogger(UploadHelper.class);
	/*public String createPath(){
		String directoryName = System.getProperty("user.dir")+"/data";
		File directory = new File(directoryName);
		if (! directory.exists()){
			directory.mkdir();
		}
		String newPath = directory.getAbsolutePath();
		return newPath;
	}*/
	public String saveData(MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte [] bytes= file.getBytes();
				String folder= "/";
				Path path = Paths.get( folder+file.getOriginalFilename() );
				Files.write(path, bytes);				
				logg.info("Archivo guardado");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "Archivo guardado correctamente";
	}

	public String saveJustificativo(MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte [] bytes= file.getBytes();
				String folder= "/";
				Path path = Paths.get( folder+file.getOriginalFilename() );
				Files.write(path, bytes);				
				logg.info("Archivo guardado");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "Archivo guardado correctamente";
	}
	
	public String saveAutorizacion(MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte [] bytes= file.getBytes();
				String folder= "/";
				Path path = Paths.get( folder+file.getOriginalFilename() );
				Files.write(path, bytes);				
				logg.info("Archivo guardado");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "Archivo guardado correctamente";
	}
}