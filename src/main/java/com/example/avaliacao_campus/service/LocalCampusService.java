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

    public List<LocalCampus> buscarTodos(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return localCampusRepository.findAll();
        } else {
            return localCampusRepository.findByNomeLike(termo);
        }
    }

    public List<LocalCampus> buscarTodos() {
        return buscarTodos(null);
    }

    public Optional<LocalCampus> buscarPorId(Long id) {
        return localCampusRepository.findById(id);
    }

    public LocalCampus salvar(LocalCampus local) {
        Optional<LocalCampus> existente = localCampusRepository.findByNome(local.getNome());

        if (existente.isPresent() && (local.getIdLocal() == null ||
                !existente.get().getIdLocal().equals(local.getIdLocal()))) {
            throw new IllegalArgumentException("Erro: o local \"" + local.getNome() + "\" já está cadastrado.");
        }

        if (local.getIdLocal() == null) {
            localCampusRepository.save(local);
        } else {
            localCampusRepository.update(local);
        }
        return local;
    }

    public void deletar(Long id) {
        localCampusRepository.delete(id);
    }
}