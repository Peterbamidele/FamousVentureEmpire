package com.example.famousventureempire.services.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface CloudStorageService {
 Map<?,?> uploadImage(File file, Map<?,?> imageProperties) throws IOException;
 Map<?,?> uploadImage(MultipartFile multipartFile,Map<?, ?> imageProperties) throws IOException;
}
