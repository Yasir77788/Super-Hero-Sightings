package com.sg.superheroSightings.service;

import com.sg.superheroSightings.dao.SuperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class SuperService {
    
    @Autowired
    private SuperDao superDao;


    private final String IMAGE_DIRECTORY = "src/main/resources/static/images";
    private final String IMAGE_EXTENSION = ".png";




    public void uploadFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(IMAGE_DIRECTORY);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName+IMAGE_EXTENSION);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public boolean isImageSet(String fileName){
        try{
            File f = new File(IMAGE_DIRECTORY+"/"+fileName+IMAGE_EXTENSION);
            if(f.exists() && !f.isDirectory()) {
                return true;
            } else {
                return false;
            }
        } catch(Exception e){
            return false;
        }

    }
}
