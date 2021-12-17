package orted.firebasejwt.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class MainController {

    @GetMapping("/user/home")
    public String home(){
        return "Welcome user home you are authorized";
    }

    @GetMapping("/admin/home")
    public String home2(){
        return "Welcome admin home you are authorized";
    }

}
