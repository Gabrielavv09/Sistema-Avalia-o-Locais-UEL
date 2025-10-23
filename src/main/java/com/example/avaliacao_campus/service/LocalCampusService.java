package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.models.LocalCampus;
import com.example.avaliacao_campus.repositories.LocalCampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LocalCampusService {

    private final LocalCampusRepository localCampusRepository;

    @Autowired
    public LocalCampusService(LocalCampusRepository localCampusRepository) {
        this.localCampusRepository = localCampusRepository;
    }

    public List<LocalCampus> buscarTodos() {
        return localCampusRepository.findAllByOrderByNomeAsc(); // Usando o método customizado
    }

    public Optional<LocalCampus> buscarPorId(Long id) {
        return localCampusRepository.findById(id);
    }

    /**
     * Lógica de Negócio: Salva um novo local após validar a unicidade do nome.
     */
    public LocalCampus salvar(LocalCampus local) {
        // Validação de Nome Único
        if (local.getNome() != null && localCampusRepository.findByNome(local.getNome()).isPresent()) {
            throw new IllegalArgumentException("Erro: O local " + local.getNome() + " já está cadastrado.");
        }

        return localCampusRepository.save(local);
    }

    public void deletar(Long id) {
        localCampusRepository.deleteById(id);
    }
}