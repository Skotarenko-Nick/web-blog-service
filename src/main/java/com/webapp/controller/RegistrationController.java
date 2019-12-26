package com.webapp.controller;

import com.webapp.exception.RegistrationException;
import com.webapp.exception.UserException;
import com.webapp.model.User;
import com.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity createNewUser(@Valid User user,
                                BindingResult bindingResult) throws UserException, RegistrationException {

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            throw new UserException("There is already a user registered with the email provided");
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            throw new UserException("There is already a user registered with the username provided");
        }

        if (!bindingResult.hasErrors()) {
            // Registration successful, save user
            // Set user role to USER and set it as active
            userService.save(user);

            return ResponseEntity.ok().build();
        }

        throw new RegistrationException("Somthig goes wrong");
    }
}
