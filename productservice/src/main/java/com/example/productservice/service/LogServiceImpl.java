package com.example.productservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class LogServiceImpl implements LogService {
    public String logUpload(MultipartFile file)  {
        if(file==null){
            return "";
        }
        String filePath = "E:\\shiyantupian";
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }
        String filename = UUID.randomUUID()+".jpg";
        fileUpload = new File(filePath, filename);
        try {
            file.transferTo(fileUpload);
            return filename;

        }catch (IOException e) {
            return "error";
        }
    }
    public String Uploads(List<MultipartFile> files) {
        String iconname =null;
        if(!files.isEmpty()&&files.size()>0) {
            for (MultipartFile file :files) {
                String filePath = "E:\\shiyantupian";
                File fileUpload = new File(filePath);
                if (!fileUpload.exists()) {
                    fileUpload.mkdirs();
                }
                String filename = UUID.randomUUID()+".jpg";
                fileUpload = new File(filePath, filename);
                try {
                    file.transferTo(fileUpload);
                    if (iconname==null) {
                        iconname =";"+ filename;
                    }else {
                        iconname = iconname+";"+filename;
                    }
                } catch (IOException e) {
                    return "error";
                }
            }
            return iconname;
        }else {
            return "";
        }

    }
}
