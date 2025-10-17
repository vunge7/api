package com.dvml.api.service;

import com.dvml.api.dto.FornecedorDTO;
import com.dvml.api.entity.Fornecedor;
import com.dvml.api.repository.FornecedorRepository;
import com.dvml.api.util.EstadoFornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    /**
     * Cadastra um novo fornecedor, incluindo empresaId
     */
    public FornecedorDTO cadastrarFornecedor(FornecedorDTO fornecedorDTO) {
        if (fornecedorRepository.existsByNif(fornecedorDTO.getNif())) {
            throw new IllegalArgumentException("NIF já registrado.");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorDTO.getNome());
        fornecedor.setContacto(fornecedorDTO.getContacto());
        fornecedor.setNif(fornecedorDTO.getNif());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setRegimeTributario(fornecedorDTO.getRegimeTributario());
        fornecedor.setEstadoFornecedor(EstadoFornecedor.ATIVO);
        fornecedor.setEmpresaId(fornecedorDTO.getEmpresaId());

        Fornecedor savedFornecedor = fornecedorRepository.save(fornecedor);
        return toDTO(savedFornecedor);
    }

    /**
     * Lista todos os fornecedores
     */
    public List<FornecedorDTO> listarTodosFornecedores() {
        return fornecedorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca fornecedor por ID
     */
    public Optional<Fornecedor> findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    /**
     * Edita um fornecedor existente, incluindo empresaId
     */
    public FornecedorDTO editarFornecedor(Long id, FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado"));

        if (!fornecedor.getNif().equals(fornecedorDTO.getNif()) &&
                fornecedorRepository.existsByNif(fornecedorDTO.getNif())) {
            throw new IllegalArgumentException("NIF já registrado por outro fornecedor.");
        }

        fornecedor.setNome(fornecedorDTO.getNome());
        fornecedor.setContacto(fornecedorDTO.getContacto());
        fornecedor.setNif(fornecedorDTO.getNif());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setRegimeTributario(fornecedorDTO.getRegimeTributario());
        fornecedor.setEstadoFornecedor(fornecedorDTO.getEstadoFornecedor() != null ? fornecedorDTO.getEstadoFornecedor() : EstadoFornecedor.ATIVO);
        fornecedor.setEmpresaId(fornecedorDTO.getEmpresaId());

        Fornecedor updatedFornecedor = fornecedorRepository.save(fornecedor);
        return toDTO(updatedFornecedor);
    }

    /**
     * Deleta um fornecedor
     */
    public void deleteFornecedor(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado"));
        fornecedorRepository.delete(fornecedor);
    }

    /**
     * Converte entidade em DTO
     */
    private FornecedorDTO toDTO(Fornecedor fornecedor) {
        FornecedorDTO dto = new FornecedorDTO();
        dto.setId(fornecedor.getId());
        dto.setNome(fornecedor.getNome());
        dto.setContacto(fornecedor.getContacto());
        dto.setNif(fornecedor.getNif());
        dto.setEndereco(fornecedor.getEndereco());
        dto.setRegimeTributario(fornecedor.getRegimeTributario());
        dto.setEstadoFornecedor(fornecedor.getEstadoFornecedor());
        dto.setEmpresaId(fornecedor.getEmpresaId());
        return dto;
    }
}