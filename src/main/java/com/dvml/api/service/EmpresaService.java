package com.dvml.api.service;

import com.dvml.api.dto.EmpresaDTO;
import com.dvml.api.entity.Empresa;
import com.dvml.api.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    public Empresa save(Empresa empresa) {
        if (empresa.getTipo() != null && empresa.getTipo().name().equalsIgnoreCase("MATRIZ")) {
            empresa.setEmpresaMatrizId(null);
        }

        List<Empresa> existentes = repository.findByNif(empresa.getNif());
        if (!existentes.isEmpty() && (empresa.getId() == null || !existentes.get(0).getId().equals(empresa.getId()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma empresa com este NIF.");
        }

        return repository.save(empresa);
    }

    public Empresa update(Long id, Empresa dadosAtualizados) {
        Empresa empresa = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada com o ID: " + id));

        empresa.setNome(dadosAtualizados.getNome());
        empresa.setNif(dadosAtualizados.getNif());
        empresa.setEmail(dadosAtualizados.getEmail());
        empresa.setTelefone(dadosAtualizados.getTelefone());
        empresa.setEndereco(dadosAtualizados.getEndereco());
        empresa.setTipo(dadosAtualizados.getTipo());
        empresa.setEmpresaMatrizId(dadosAtualizados.getEmpresaMatrizId());
        empresa.setSeguradoraId(dadosAtualizados.getSeguradoraId());

        return repository.save(empresa);
    }

    public List<Empresa> findAll() {
        return repository.findAllByStatusTrueOrderByNomeAsc();
    }

    public Optional<EmpresaDTO> findById(Long id) {
        return repository.findById(id).map(this::mapToDTOWithFiliais);
    }

    public Optional<EmpresaDTO> findFilialById(Long id) {
        return repository.findById(id)
                .filter(e -> e.getTipo() != null && e.getTipo().name().equalsIgnoreCase("FILIAL"))
                .map(this::mapToDTOWithFiliais);
    }

    public List<EmpresaDTO> listarArvoreCompleta() {
        List<Empresa> matrizes = repository.findAll().stream()
                .filter(e -> e.getEmpresaMatrizId() == null)
                .collect(Collectors.toList());

        return matrizes.stream()
                .map(this::mapToDTOWithFiliais)
                .collect(Collectors.toList());
    }

    public List<EmpresaDTO> getFiliaisByEmpresaId(Long empresaId) {
        List<Empresa> filiais = repository.findByEmpresaMatrizId(empresaId);
        return filiais.stream().map(this::mapToDTOWithFiliais).collect(Collectors.toList());
    }

    private EmpresaDTO mapToDTOWithFiliais(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setNome(empresa.getNome());
        dto.setTipo(empresa.getTipo());
        dto.setEmpresaMatrizId(empresa.getEmpresaMatrizId());
        dto.setNif(empresa.getNif());
        dto.setEmail(empresa.getEmail());
        dto.setTelefone(empresa.getTelefone());
        dto.setEndereco(empresa.getEndereco());
        dto.setSeguradoraId(empresa.getSeguradoraId());

        List<Empresa> filiais = repository.findByEmpresaMatrizId(empresa.getId());
        dto.setEmpresas(filiais.stream().map(this::mapToDTOWithFiliais).collect(Collectors.toList()));

        return dto;
    }

    public void delete(Long id) {
        Empresa empresa = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada com o ID: " + id));

        if (empresa.getEmpresaMatrizId() == null) {
            List<Empresa> filiais = repository.findByEmpresaMatrizId(empresa.getId());
            if (!filiais.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Não é possível deletar a matriz enquanto houver filiais associadas.");
            }
        }

        repository.deleteById(id);
    }

    public void deleteCascade(Long id) {
        Empresa empresa = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada com o ID: " + id));

        List<Empresa> filiais = repository.findByEmpresaMatrizId(empresa.getId());
        for (Empresa filial : filiais) {
            deleteCascade(filial.getId());
        }

        repository.deleteById(id);
    }

    public List<EmpresaDTO> listarTodasFiliais() {
        List<Empresa> filiais = repository.findAll().stream()
                .filter(e -> e.getTipo() != null && e.getTipo().name().equalsIgnoreCase("FILIAL"))
                .collect(Collectors.toList());

        return filiais.stream().map(this::mapToDTOWithFiliais).collect(Collectors.toList());
    }
}