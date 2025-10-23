package com.example.avaliacao_campus.controllers;
// Gerenciando o CRUD de Usuarios

import com.example.avaliacao_campus.models.Usuario;
import com.example.avaliacao_campus.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Exibir o Formulário de Cadastro
    // GET /usuarios/cadastro
    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        // Envia um objeto Usuario vazio para o formulário Thymeleaf
        model.addAttribute("usuario", new Usuario());
        // Retorna o nome do template
        return "usuario/cadastro";
    }

    // Cadastrar um novo usuário
    // POST /usuarios/salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.salvar(usuario);
            return "redirect:/usuarios/lista";
        } catch (IllegalArgumentException e) {
            model.addAttribute("usuario", usuario); // Mantém os dados preenchidos
            model.addAttribute("erro", e.getMessage());
            return "usuario/cadastro";
        }
    }

    // Listar todos os usuários
    // GET /usuarios/lista
    @GetMapping("/lista")
    public String listarTodos(Model model) {
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        return "usuario/lista";
    }
}