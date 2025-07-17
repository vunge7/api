package com.dvml.api.service;

import com.dvml.api.entity.LinhaResultado;
import com.dvml.api.repository.LinhaResultadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinhaResultadoService {

    @Autowired
    private LinhaResultadoRepository repository;

    public LinhaResultado salvar(LinhaResultado linhaResultado) {
        return repository.save(linhaResultado);
    }

    public List<LinhaResultado> listarTodos() {
        return repository.findAll();
    }

    public Optional<LinhaResultado> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public LinhaResultado atualizar(Long id, LinhaResultado novo) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setExameId(novo.getExameId());
                    existing.setValorReferencia(novo.getValorReferencia());
                    existing.setUnidadeId(novo.getUnidadeId());
                    existing.setResultadoId(novo.getResultadoId());
                    existing.setObservacao(novo.getObservacao());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("LinhaResultado não encontrada com ID: " + id));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}