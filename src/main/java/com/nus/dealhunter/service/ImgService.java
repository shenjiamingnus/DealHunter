package com.nus.dealhunter.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImgService {

  public static final String ACCESS_KEY = "tNlOb8NL6fAVb90yjgbf";

  public static final String SECRET_KEY = "ieCXgFn3xAmMwCGff4hZQK1QjZW2DDsma6T4Spsu";

  public static final String BUCKET = "dealhunter";

  public static final String END_POINT = "http://68.183.176.202:9000";

  public String uploadImage(MultipartFile file) {
    String objectName = UUID.randomUUID().toString();
    try {
      InputStream fileInputStream = file.getInputStream();
      //1.创建minio链接客户端
      MinioClient minioClient = MinioClient.builder().credentials(ACCESS_KEY, SECRET_KEY).endpoint(END_POINT).build();
      //2.上传
      PutObjectArgs putObjectArgs = PutObjectArgs.builder()
          .object(objectName+".png")
          .contentType("image/jpg")
          .bucket(BUCKET)
          .stream(fileInputStream, fileInputStream.available(), -1)
          .build();
      minioClient.putObject(putObjectArgs);

    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return END_POINT + "/" + BUCKET + "/" + objectName + ".png";
  }

}
