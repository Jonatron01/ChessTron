package com.Jonatron01.ChessTron.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @GetMapping("/game-page")
    public String gamePage(Model model, @RequestParam(name = "wpPlayer", required = true) boolean w, @RequestParam(name = "bpPlayer", required = true) boolean b) {
        model.addAttribute("wpPlayer", w);
        model.addAttribute("bpPlayer", b);
        System.out.println("uhoh");
        return "game"; // Corresponds to src/main/resources/templates/new-page.html
    }
}