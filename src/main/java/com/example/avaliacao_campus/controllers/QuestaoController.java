package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.models.Questao;
import com.example.avaliacao_campus.service.QuestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/questoes")
public class QuestaoController {

    private final QuestaoService questaoService;

    @Autowired
    public QuestaoController(QuestaoService questaoService) {
        this.questaoService = questaoService;
    }

    @GetMapping("")
    public String listarTodos(Model model) {
        List<Questao> questoes = questaoService.buscarTodos();
        model.addAttribute("questoes", questoes);
        return "questoes/index";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("questao", new Questao());
        return "questoes/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Questao questao, RedirectAttributes redirectAttributes) {
        try {
            questaoService.salvar(questao);
            redirectAttributes.addFlashAttribute("mensagem", "Questão cadastrada com sucesso!");
            return "redirect:/questoes/";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/questoes/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            questaoService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Questão deletada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar a questão.");
        }
        return "redirect:/questoes/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Questao questao = questaoService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Questão não encontrada"));
        model.addAttribute("questao", questao);
        return "questoes/formulario";
    }
}
