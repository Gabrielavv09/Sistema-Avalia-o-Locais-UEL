package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.models.LocalCampus;
import com.example.avaliacao_campus.repositories.LocalCampusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalCampusService {

    private final LocalCampusRepository localCampusRepository;

    public LocalCampusService(LocalCampusRepository localCampusRepository) {
        this.localCampusRepository = localCampusRepository;
    }

    public List<LocalCampus> buscarTodos() {
        // O repository JDBC já retorna todos ordenados se o SQL tiver ORDER BY
        return localCampusRepository.findAll();
    }

    public Optional<LocalCampus> buscarPorId(Long id) {
        return localCampusRepository.findById(id);
    }

    public LocalCampus salvar(LocalCampus local) {
        // Verifica se já existe um local com esse nome
        Optional<LocalCampus> existente = localCampusRepository.findByNome(local.getNome());

        if (existente.isPresent() && (local.getIdLocal() == null ||
                !existente.get().getIdLocal().equals(local.getIdLocal()))) {
            throw new IllegalArgumentException("Erro: o local \"" + local.getNome() + "\" já está cadastrado.");
        }

        if (local.getIdLocal() == null) {
            // Novo local
            localCampusRepository.save(local);
        } else {
            // Atualização
            localCampusRepository.update(local);
        }

        return local;
    }


    public void deletar(Long id) {
        localCampusRepository.delete(id);
    }
}
