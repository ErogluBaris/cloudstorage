package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.controller.FileController;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {

    public final Logger logger = LoggerFactory.getLogger(FileService.class);

    private final UserService userService;
    private final FileMapper fileMapper;

    public FileService(UserService userService,
                       FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public String upload(MultipartFile multipartFile) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        if ( fileMapper.getFile(fileName) != null ) {
            return "File already exists with this name.";
        }

        File file = new File();
        file.setFileName(fileName);
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(String.valueOf(multipartFile.getSize()));

        User user = userService.findAuthenticatedUser();
        if ( user != null ) {
            file.setUserId(user.getUserId());
        }
        try {
            file.setFileData(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "File could not convert to byte array.";
        }

        fileMapper.insert(file);
        logger.info("File created. File name:  {}", fileName);

        return null;
    }

    public File find(String fileName) {

        return fileMapper.getFile(fileName);
    }

    public boolean delete(String fileName) {

        int affectedRowCount = fileMapper.delete(fileName);

        return affectedRowCount > 0;
    }

    public List<UploadedFile> getFilesByUser() {

        User user = userService.findAuthenticatedUser();
        List<File> userFiles = fileMapper.getFilesByUser(user.getUserId());

        List<UploadedFile> uploadedFiles = new ArrayList<>();
        for ( File file : userFiles ) {
            uploadedFiles.add(new UploadedFile(file.getFileId(), file.getFileName()));
        }

        return uploadedFiles;
    }
}
