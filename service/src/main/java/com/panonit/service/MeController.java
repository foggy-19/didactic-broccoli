package com.panonit.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@ResponseBody
public class MeController {

    @GetMapping(path = "/me")
    String me(Principal principal) {
        return principal.getName();
    }
}
