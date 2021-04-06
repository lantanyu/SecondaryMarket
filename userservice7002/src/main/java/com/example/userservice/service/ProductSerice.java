package com.example.userservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@FeignClient(value = "product-service7003")
public interface ProductSerice {

    @PostMapping(value = "/product/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String logUpload(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/product/uploads",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String logUploads(@RequestPart("files") List<MultipartFile> files);
}
