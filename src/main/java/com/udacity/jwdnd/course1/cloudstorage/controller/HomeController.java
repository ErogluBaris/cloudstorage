package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final HomeService homeService;
    private final EncryptionService encryptionService;

    public HomeController(HomeService homeService,
                          EncryptionService encryptionService) {
        this.homeService = homeService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("uploadedFiles", homeService.getFilesByUser());
        model.addAttribute("savedNotes", homeService.getNotesByUser());
        model.addAttribute("savedCredentials", homeService.getCredentialsByUser());
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @GetMapping("/result")
    public String result() {
        return "result";
    }

}
