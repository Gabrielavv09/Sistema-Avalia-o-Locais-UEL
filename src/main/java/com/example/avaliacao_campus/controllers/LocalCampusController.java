package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.models.LocalCampus;
import com.example.avaliacao_campus.service.LocalCampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/locais")
public class LocalCampusController {

    private final LocalCampusService localCampusService;

    @Autowired
    public LocalCampusController(LocalCampusService localCampusService) {
        this.localCampusService = localCampusService;
    }

    @GetMapping("")
    public String listarTodos(Model model) {
        List<LocalCampus> locais = localCampusService.buscarTodos();
        model.addAttribute("locais", locais);
        return "locais/index";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("local", new LocalCampus());
        return "locais/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            LocalCampus local = localCampusService.buscarPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Local não encontrado"));
            model.addAttribute("local", local);
            return "locais/formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/locais";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute LocalCampus local, RedirectAttributes redirectAttributes) {
        try {
            localCampusService.salvar(local);
            redirectAttributes.addFlashAttribute("mensagem", "Local cadastrado com sucesso!");
            return "redirect:/locais";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar local: " + e.getMessage());
            return "redirect:/locais/novo";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute LocalCampus local, RedirectAttributes redirectAttributes) {
        try {
            local.setIdLocal(id);
            localCampusService.salvar(local);
            redirectAttributes.addFlashAttribute("mensagem", "Local atualizado com sucesso!");
            return "redirect:/locais";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar local: " + e.getMessage());
            return "redirect:/locais/editar/" + id;
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            localCampusService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Local deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar o local.");
        }
        return "redirect:/locais";
    }
}
