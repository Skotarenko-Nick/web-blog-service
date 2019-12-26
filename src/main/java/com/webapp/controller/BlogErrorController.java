package com.webapp.controller;

import com.webapp.exception.CommonException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public void error() throws CommonException {
        throw new CommonException("Something goes wrong, Error appears");
    }

    @GetMapping("/403")
    public void error403() throws CommonException {
        throw new CommonException("User is not authorized to perform the operation or the resource is unavailable for some reason");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
