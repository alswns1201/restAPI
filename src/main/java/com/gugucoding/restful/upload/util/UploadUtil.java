package com.gugucoding.restful.upload.util;


import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class UploadUtil {

    @Value("org.gugucoding.resful.uplaod.path")
    private String uploadPath;

    @PostConstruct
    public void init(){ // 자동으로 폴더 경로 만든다 .
        File tempFolder = new File(uploadPath);
        if(tempFolder.exists() == false){
            tempFolder.mkdir();
        }

        uploadPath = tempFolder.getAbsolutePath();
        log.info("-----------------------------");
        log.info(uploadPath);
    }

    public List<String> upload(MultipartFile[] files){

        List<String> result = new ArrayList<>();

        for(MultipartFile file : files){
            if(file.getContentType().startsWith("image")== false){
                log.error("file type not support"+file.getContentType());
                continue;
            }

            String uuid = UUID.randomUUID().toString();
            String saveFileName = uuid+"_"+file.getOriginalFilename();
            try(
                    InputStream in = file.getInputStream();
                    OutputStream out = new FileOutputStream(uploadPath+ File.separator+saveFileName)
            ){
                FileCopyUtils.copy(in,out);
                result.add(saveFileName);
            }catch (Exception e ){

            }
        }
        return  result;


    }


}
