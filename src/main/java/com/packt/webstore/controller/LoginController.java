package com.packt.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dman on 8/11/16.
 */
@Controller
public class LoginController {
    private static final String LOGIN = "login";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return LOGIN;
    }

    @RequestMapping(value = "/loginfailed", method =
            RequestMethod.GET)
    public String loginerror(Model model) {
        model.addAttribute("error", "true");
        return LOGIN;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        return LOGIN;
    }
}
