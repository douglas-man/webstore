package com.packt.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by douglas on 6/14/2016.
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("greeting", "Welocme to Web Store!");
        model.addAttribute("tagline", "The one and only amazing web store");

        return "welcome";
    }
}
