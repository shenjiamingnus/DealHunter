package com.nus.dealhunter.controller;

import com.nus.dealhunter.annotation.CurrentUser;
import com.nus.dealhunter.model.CustomUserDetails;
import com.nus.dealhunter.payload.response.GeneralApiResponse;
import com.nus.dealhunter.service.ImgService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api("Image")
@RestController
@RequestMapping("/api/image")
public class ImgController {

  @Autowired
  public ImgService imgService;

  @PostMapping("/upload")
  public ResponseEntity<?> uploadAvatar(@RequestParam MultipartFile file, @CurrentUser CustomUserDetails userDetails) throws IOException {
    String address = imgService.uploadImage(file);
    if (address != null) {
      return ResponseEntity.ok(new GeneralApiResponse(true, "Upload Successfully!", address));
    }
    return ResponseEntity.ok(new GeneralApiResponse(false, "Upload Failed."));
  }

}
