package com.webapp.controller;

import com.webapp.exception.LoginException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

    @GetMapping("/login")
    @ResponseBody
    public void login(Principal principal) throws LoginException {

        if (principal == null) {
            throw new LoginException("No data to proceed");
        }else {
            principal.getName();
        }
    }

}
