package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.enums.ErrorMessages;
import com.udacity.jwdnd.course1.cloudstorage.model.SignupFormModel;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    public final Logger logger = LoggerFactory.getLogger(SignupController.class);

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signup() {
        return "signup";
    }

    @PostMapping()
    public String signup(@ModelAttribute("signupFormModel") SignupFormModel signupFormModel, Model model) {

        String signupErrorMessage = null;
        boolean isSignupSuccessful = userService.isUsernameAvailable(signupFormModel.getUsername());
        if ( !isSignupSuccessful )
            signupErrorMessage = ErrorMessages.USER_EXISTS.getErrorMessage();
        else {
           int addedRowCount = userService.createUser(signupFormModel);
           if ( addedRowCount <= 0 ) {
               isSignupSuccessful = false;
               signupErrorMessage = ErrorMessages.USER_NOT_CREATED.getErrorMessage();
           } else {
               logger.info("User created.");
           }
        }

        if ( signupErrorMessage != null ) {
            model.addAttribute("signupErrorMessage", signupErrorMessage);
        }
        model.addAttribute("isSignupSuccessful", isSignupSuccessful);

        return "signup";
    }
}
