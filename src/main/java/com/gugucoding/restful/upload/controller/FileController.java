package com.gugucoding.restful.upload.controller;


import com.gugucoding.restful.upload.exception.UploadNotSupportException;
import com.gugucoding.restful.upload.util.UploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final UploadUtil uploadUtil;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFile(@RequestParam("files")MultipartFile[] files){
        log.info("upload file ----");
            //json 이 아닌 multipart/form-data 형식으로 들어온다. 그렇기에 @RequestParam 이 적합.

        if(files==null || files.length ==0){
            throw new UploadNotSupportException("No files to uplaod");

        }

        for(MultipartFile file : files){
            checkFileType(file.getOriginalFilename());
        }

        List<String> result = uploadUtil.upload(files);


        return ResponseEntity.ok(result);
    }

    private void checkFileType(String fileName)throws UploadNotSupportException{
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        String regExp = "^(jpg|jpeg|JPG|JPEG|png|PNG|gif|GIF)";
        if(!suffix.matches(regExp)){
            throw new UploadNotSupportException("File type not supported : "+suffix);
        }
    }

}
