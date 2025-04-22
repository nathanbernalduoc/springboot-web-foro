package com.nathanbernal.springweb_foro.controller;
import com.nathanbernal.springweb_foro.service.ForoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nathanbernal.springweb_foro.SpringwebForoApplication;
import com.nathanbernal.springweb_foro.dto.ForoDto;

import org.springframework.ui.Model;

@Controller
public class HomeController {

    private final ForoService foroService;

    private final SpringwebForoApplication springwebForoApplication;

    HomeController(SpringwebForoApplication springwebForoApplication, ForoService foroService) {
        this.springwebForoApplication = springwebForoApplication;
        this.foroService = foroService;
    }

    @GetMapping("/home")
    public String home(@RequestParam(name="name", required=false, defaultValue = "Seguridad y Calidad / Foros") String name, Model model) {
        model.addAttribute("name", name);
        ResponseEntity<Flux<ForoDto>> foros = new ResponseEntity<>(foroService.getForos(), HttpStatus.OK);
        List<String> foroList = new ArrayList<String>();
        int foroItem = 0;
        while(foroItem < 4) {
            Mono<ForoDto> f = foros.getBody().next();
            System.out.print(f.toString());
            foroList.add(f.toString());
            foroItem++;
       }

        return "Home";
    }

    @GetMapping("/")
    public String root(@RequestParam(name="name", required=false, defaultValue = "Seguridad y Calidad / Foro") String name, Model model) {
        model.addAttribute("name", name);
        return "Home";
    }
    

}
