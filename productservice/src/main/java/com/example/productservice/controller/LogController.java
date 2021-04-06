package com.example.productservice.controller;

import com.example.commons.po.cuser;
import com.example.productservice.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@Scope("prototype")
public class LogController {
    @Autowired
    private LogService logService;

    @PostMapping(value = "/upload")
    public String logUpload(@RequestParam("file") MultipartFile file, @RequestParam("username") String hh)  {
        System.out.println(hh);
        return logService.logUpload(file);
    }
    @PostMapping(value = "/uploads")
    public String logUploads(@RequestParam("files") List<MultipartFile> files, @RequestParam("username") String hh)  {
        System.out.println(hh);
        return logService.Uploads(files);
    }
}
