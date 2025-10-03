package com.dvml.api.service;

import com.dvml.api.dto.LinhasLotesDTO;
import com.dvml.api.entity.LinhasLotes;
import com.dvml.api.repository.LinhasLotesRepository;
import com.dvml.api.repository.LotesRepository;
import com.dvml.api.repository.ProdutoRepository;
import com.dvml.api.repository.ArmazemRepository;  // ← ADICIONAR ESTE IMPORT
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinhasLotesService {

    @Autowired
    private LinhasLotesRepository linhasLotesRepository;

    @Autowired
    private LotesRepository lotesRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ArmazemRepository armazemRepository;  // ← ADICIONAR ESTE REPOSITORY

    public LinhasLotes criarLinhasLotes(LinhasLotesDTO dto) {
        if (!lotesRepository.existsById(dto.getLotes_id())) {
            throw new EntityNotFoundException("Lote com ID " + dto.getLotes_id() + " não encontrado");
        }
        if (!produtoRepository.existsById(dto.getProduto_id())) {
            throw new EntityNotFoundException("Produto com ID " + dto.getProduto_id() + " não encontrado");
        }
        if (dto.getArmazem_id() != null && !armazemRepository.existsById(dto.getArmazem_id())) {
            throw new EntityNotFoundException("Armazém com ID " + dto.getArmazem_id() + " não encontrado");
        }

        LinhasLotes linhasLotes = new LinhasLotes();
        linhasLotes.setLotes_id(dto.getLotes_id());
        linhasLotes.setProduto_id(dto.getProduto_id());
        linhasLotes.setArmazem_id(dto.getArmazem_id());  // ← ADICIONAR ESTE CAMPO
        linhasLotes.setQuantidade(dto.getQuantidade());
        return linhasLotesRepository.save(linhasLotes);
    }

    public LinhasLotes atualizarLinhasLotes(Long id, LinhasLotesDTO dto) {
        LinhasLotes linhasLotes = linhasLotesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LinhasLotes com ID " + id + " não encontrado"));
        if (!lotesRepository.existsById(dto.getLotes_id())) {
            throw new EntityNotFoundException("Lote com ID " + dto.getLotes_id() + " não encontrado");
        }
        if (!produtoRepository.existsById(dto.getProduto_id())) {
            throw new EntityNotFoundException("Produto com ID " + dto.getProduto_id() + " não encontrado");
        }
        if (dto.getArmazem_id() != null && !armazemRepository.existsById(dto.getArmazem_id())) {
            throw new EntityNotFoundException("Armazém com ID " + dto.getArmazem_id() + " não encontrado");
        }

        linhasLotes.setLotes_id(dto.getLotes_id());
        linhasLotes.setProduto_id(dto.getProduto_id());
        linhasLotes.setArmazem_id(dto.getArmazem_id());  // ← ADICIONAR ESTE CAMPO
        linhasLotes.setQuantidade(dto.getQuantidade());
        return linhasLotesRepository.save(linhasLotes);
    }

    public void deletarLinhasLotes(Long id) {
        if (!linhasLotesRepository.existsById(id)) {
            throw new EntityNotFoundException("LinhasLotes com ID " + id + " não encontrado");
        }
        linhasLotesRepository.deleteById(id);
    }

    public List<LinhasLotesDTO> listarTodos() {
        return linhasLotesRepository.findAll().stream().map(linhas -> {
            LinhasLotesDTO dto = new LinhasLotesDTO();
            dto.setId(linhas.getId());
            dto.setLotes_id(linhas.getLotes_id());
            dto.setProduto_id(linhas.getProduto_id());
            dto.setArmazem_id(linhas.getArmazem_id());  // ← ADICIONAR ESTE CAMPO
            dto.setQuantidade(linhas.getQuantidade());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<LinhasLotesDTO> listarPorLote(Long loteId) {
        return linhasLotesRepository.findByLotesId(loteId).stream().map(linhas -> {
            LinhasLotesDTO dto = new LinhasLotesDTO();
            dto.setId(linhas.getId());
            dto.setLotes_id(linhas.getLotes_id());
            dto.setProduto_id(linhas.getProduto_id());
            dto.setArmazem_id(linhas.getArmazem_id());  // ← ADICIONAR ESTE CAMPO
            dto.setQuantidade(linhas.getQuantidade());
            return dto;
        }).collect(Collectors.toList());
    }

    public LinhasLotesDTO buscarPorId(Long id) {
        LinhasLotes linhasLotes = linhasLotesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LinhasLotes com ID " + id + " não encontrado"));
        LinhasLotesDTO dto = new LinhasLotesDTO();
        dto.setId(linhasLotes.getId());
        dto.setLotes_id(linhasLotes.getLotes_id());
        dto.setProduto_id(linhasLotes.getProduto_id());
        dto.setArmazem_id(linhasLotes.getArmazem_id());  // ← ADICIONAR ESTE CAMPO
        dto.setQuantidade(linhasLotes.getQuantidade());
        return dto;
    }
}