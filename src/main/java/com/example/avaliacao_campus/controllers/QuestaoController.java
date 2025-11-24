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
    public String listarTodas(@RequestParam(value = "termo", required = false) String termo, Model model) {
        List<Questao> questoes = questaoService.buscarTodos(termo);
        model.addAttribute("questoes", questoes);
        model.addAttribute("termo", termo); // Envia de volta para o input
        return "questoes/index";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("questao", new Questao());
        return "questoes/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Questao questao, RedirectAttributes redirectAttributes) {
        try {
            questaoService.salvar(questao);
            redirectAttributes.addFlashAttribute("mensagem", "Questão cadastrada com sucesso!");
            return "redirect:/questoes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar questão: " + e.getMessage());
            return "redirect:/questoes/novo";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Questao questao = questaoService.buscarPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Questão não encontrada"));
            model.addAttribute("questao", questao);
            return "questoes/formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/questoes";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute Questao questao,
                            RedirectAttributes redirectAttributes) {
        try {
            questao.setIdQuestao(id);
            questaoService.salvar(questao);
            redirectAttributes.addFlashAttribute("mensagem", "Questão atualizada com sucesso!");
            return "redirect:/questoes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar questão: " + e.getMessage());
            return "redirect:/questoes/editar/" + id;
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
        return "redirect:/questoes";
    }
}