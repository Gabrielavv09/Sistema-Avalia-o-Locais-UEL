package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.dtos.AvaliacaoRequestDTO;
import com.example.avaliacao_campus.models.Avaliacao;
import com.example.avaliacao_campus.models.LocalCampus;
import com.example.avaliacao_campus.models.Questao;
import com.example.avaliacao_campus.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final QuestaoService questaoService;
    private final LocalCampusService localCampusService;
    private final AvaliacaoQuestaoService avaliacaoQuestaoService;
    private final UsuarioService usuarioService;

    @Autowired
    public AvaliacaoController(
            AvaliacaoService avaliacaoService,
            QuestaoService questaoService,
            LocalCampusService localCampusService,
            AvaliacaoQuestaoService avaliacaoQuestaoService,
            UsuarioService usuarioService) {
        this.avaliacaoService = avaliacaoService;
        this.questaoService = questaoService;
        this.localCampusService = localCampusService;
        this.avaliacaoQuestaoService = avaliacaoQuestaoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/lista")
    public String listarTodas(@RequestParam(value = "termo", required = false) String termo, Model model) {
        List<Avaliacao> avaliacoes = avaliacaoService.buscarTodas(termo);
        model.addAttribute("avaliacoes", avaliacoes);
        model.addAttribute("termo", termo);
        return "avaliacao/lista";
    }

    @GetMapping("/form")
    public String exibirFormulario(Model model) {
        List<Questao> questoesPadrao = questaoService.buscarPorTipo("padrao");
        List<Questao> questoesPersonalizadas = questaoService.buscarPorTipo("multipla");
        List<LocalCampus> locais = localCampusService.buscarTodos();

        model.addAttribute("questoesPadrao", questoesPadrao);
        model.addAttribute("questoesPersonalizadas", questoesPersonalizadas);
        model.addAttribute("locais", locais);
        model.addAttribute("avaliacaoRequest", new AvaliacaoRequestDTO());
        return "avaliacao/formulario";
    }

    @PostMapping("/registrar")
    public String registrar(@ModelAttribute AvaliacaoRequestDTO request, RedirectAttributes attributes) {
        try {
            avaliacaoService.registrarAvaliacao(request);
            attributes.addFlashAttribute("mensagem", "Avaliação registrada com sucesso!");
            return "redirect:/avaliacoes/sucesso";
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/avaliacoes/form";
        }
    }

    @GetMapping("/sucesso")
    public String sucesso() {
        return "avaliacao/sucesso";
    }

    @GetMapping("/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        var avaliacao = avaliacaoService.findById(id).orElseThrow();
        model.addAttribute("avaliacao", avaliacao);
        return "avaliacao/detalhes";
    }
}