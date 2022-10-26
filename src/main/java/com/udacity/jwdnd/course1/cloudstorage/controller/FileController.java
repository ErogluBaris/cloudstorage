package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(Model model, @RequestParam("fileUpload") MultipartFile multipartFile) {

        String errorMessage = fileService.upload(multipartFile);
        if ( errorMessage != null ) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", errorMessage);
        } else {
            model.addAttribute("success", true);
        }

        return "result";
    }

    @GetMapping(value = "/find/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] findFile(@PathVariable("fileName") String fileName) {

        File file = fileService.find(fileName);

        return file.getFileData();
    }

    @GetMapping("/delete/{fileName}")
    public String deleteFile(Model model, @PathVariable("fileName") String fileName) {

        boolean isDeleted = fileService.delete(fileName);
        if ( isDeleted )
            model.addAttribute("success", true);
        else {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", fileName + " named file could not delete.");
        }

        return "result";
    }
}
