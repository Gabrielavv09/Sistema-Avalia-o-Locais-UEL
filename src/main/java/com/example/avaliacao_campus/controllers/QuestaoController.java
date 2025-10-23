package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.models.Questao;
import com.example.avaliacao_campus.service.QuestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // Mudança para @Controller
@RequestMapping("/questoes") // Rota base: /questoes
public class QuestaoController {

    private final QuestaoService questaoService;

    @Autowired
    public QuestaoController(QuestaoService questaoService) {
        this.questaoService = questaoService;
    }

    // GET /questoes/cadastro
    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        model.addAttribute("questao", new Questao());
        return "questao/cadastro";
    }

    // POST /questoes/salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Questao questao, Model model, RedirectAttributes attributes) {
        try {
            questaoService.salvar(questao);
            attributes.addFlashAttribute("mensagem", "Questão cadastrada com sucesso!");
            return "redirect:/questoes/lista";
        } catch (IllegalArgumentException e) {
            model.addAttribute("questao", questao);
            model.addAttribute("erro", e.getMessage());
            return "questao/cadastro";
        }
    }

    // Listar todas as questões
    // GET /questoes/lista
    @GetMapping("/lista")
    public String listarTodos(Model model) {
        model.addAttribute("questoes", questaoService.buscarTodos());
        return "questao/lista";
    }
}
