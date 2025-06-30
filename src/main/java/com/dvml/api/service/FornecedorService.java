package com.dvml.api.service;

import com.dvml.api.dto.FornecedorDTO;
import com.dvml.api.entity.Fornecedor;
import com.dvml.api.repository.FornecedorRepository;
import com.dvml.api.util.EstadoFornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public ResponseEntity<?> cadastrarFornecedor(FornecedorDTO fornecedorDTO) {
        if (fornecedorRepository.existsByNif(fornecedorDTO.getNif())) {
            return ResponseEntity.badRequest().body("NIF já registrado.");
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorDTO.getNome());
        fornecedor.setContacto(fornecedorDTO.getContacto());
        fornecedor.setNif(fornecedorDTO.getNif());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setRegimeTributario(fornecedorDTO.getRegimeTributario());
        fornecedor.setEstadoFornecedor(EstadoFornecedor.ATIVO);
        fornecedorRepository.save(fornecedor);
        return ResponseEntity.ok(fornecedor);
    }

    public List<Fornecedor> listarTodosFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public ResponseEntity<String> editarFornecedor(Long id, FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado"));
        if (!fornecedor.getNif().equals(fornecedorDTO.getNif()) && fornecedorRepository.existsByNif(fornecedorDTO.getNif())) {
            return ResponseEntity.badRequest().body("NIF já registrado por outro fornecedor.");
        }
        fornecedor.setNome(fornecedorDTO.getNome());
        fornecedor.setContacto(fornecedorDTO.getContacto());
        fornecedor.setNif(fornecedorDTO.getNif());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setRegimeTributario(fornecedorDTO.getRegimeTributario());
        fornecedorRepository.save(fornecedor);
        return ResponseEntity.ok("Fornecedor atualizado com sucesso!");
    }

    public ResponseEntity<String> deleteFornecedor(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado"));
        fornecedorRepository.delete(fornecedor);
        return ResponseEntity.ok("Fornecedor excluído com sucesso!");
    }
}