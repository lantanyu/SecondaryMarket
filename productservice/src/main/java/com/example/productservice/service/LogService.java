package com.example.productservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LogService {
    public String logUpload(MultipartFile file);
    public String Uploads(List<MultipartFile> files);
}
