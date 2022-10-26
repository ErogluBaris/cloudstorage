package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/create")
    public String create(Model model, @ModelAttribute("credentialModel") CredentialModel credentialModel) {

        boolean isCreated = credentialService.saveCredential(credentialModel);
        if ( isCreated )
            model.addAttribute("success", true);
        else {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", String.format("Credential could not save. Credential Url: %s, Credential Username: %s", credentialModel.getUrl(), credentialModel.getUsername()));
        }

        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String delete(Model model, @PathVariable("credentialId") Integer credentialId) {

        boolean isDeleted = credentialService.delete(credentialId);
        if ( isDeleted )
            model.addAttribute("success", true);
        else {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Credential could not delete.");
        }

        return "result";
    }
}
