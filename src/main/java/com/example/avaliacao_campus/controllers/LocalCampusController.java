package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.models.LocalCampus;
import com.example.avaliacao_campus.service.LocalCampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // Mudança de @RestController para @Controller
@RequestMapping("/locais") // Rota base: /locais
public class LocalCampusController {

    private final LocalCampusService localCampusService;

    @Autowired
    public LocalCampusController(LocalCampusService localCampusService) {
        this.localCampusService = localCampusService;
    }

    // GET /locais/cadastro
    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        model.addAttribute("local", new LocalCampus());
        return "local/cadastro";
    }

    // Cadastrar um novo local
    // POST /locais/salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute LocalCampus local, Model model) {
        try {
            localCampusService.salvar(local);
            return "redirect:/locais/lista";
        } catch (IllegalArgumentException e) {
            model.addAttribute("local", local);
            model.addAttribute("erro", e.getMessage());
            return "local/cadastro";
        }
    }

    // Listar todos os locais
    // GET /locais/lista
    @GetMapping("/lista")
    public String listarTodos(Model model) {
        model.addAttribute("locais", localCampusService.buscarTodos());
        return "local/lista";
    }
}