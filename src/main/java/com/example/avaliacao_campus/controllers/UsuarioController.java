package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.models.Usuario;
import com.example.avaliacao_campus.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("")
    public String listarTodos(Model model) {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/index";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.salvar(usuario);
            redirectAttributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
            return "redirect:/usuarios/";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/usuarios/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Usuário deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar o usuário.");
        }
        return "redirect:/usuarios/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        model.addAttribute("usuario", usuario);
        return "usuarios/formulario";
    }
}
