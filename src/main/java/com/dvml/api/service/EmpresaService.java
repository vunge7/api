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

    // ✅ Criar nova empresa (matriz ou filial)
    public Empresa save(Empresa empresa) {
        // Se for MATRIZ, força empresaMatrizId = 0
        if (empresa.getTipo() != null && empresa.getTipo().name().equalsIgnoreCase("MATRIZ")) {
            empresa.setEmpresaMatrizId(0L);
        }

        // Valida NIF duplicado
        List<Empresa> existentes = repository.findByNif(empresa.getNif());
        if (!existentes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma empresa com este NIF.");
        }

        return repository.save(empresa);
    }

    // ✅ Atualizar empresa
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

    // ✅ Buscar todas as empresas ativas
    public List<Empresa> findAll() {
        return repository.findAllByStatusTrueOrderByNomeAsc();
    }

    // ✅ Buscar uma empresa com filiais
    public Optional<EmpresaDTO> findById(Long id) {
        return repository.findById(id)
                .map(this::mapToDTOWithFiliais);
    }

    // ✅ Montar árvore completa (todas as matrizes → filiais → subfiliais)
    public List<EmpresaDTO> listarArvoreCompleta() {
        List<Empresa> matrizes = repository.findAll().stream()
                .filter(e -> e.getEmpresaMatrizId() == null || e.getEmpresaMatrizId() == 0)
                .collect(Collectors.toList());

        return matrizes.stream()
                .map(this::mapToDTOWithFiliais)
                .collect(Collectors.toList());
    }

    // ✅ Buscar filiais diretas de uma empresa
    public List<EmpresaDTO> getFiliaisByEmpresaId(Long empresaId) {
        List<Empresa> filiais = repository.findByEmpresaMatrizId(empresaId);
        return filiais.stream()
                .map(this::mapToDTOWithFiliais)
                .collect(Collectors.toList());
    }

    // ✅ Converter recursivamente Empresa → EmpresaDTO (com filiais e subfiliais)
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
        List<EmpresaDTO> filiaisDTO = filiais.stream()
                .map(this::mapToDTOWithFiliais)
                .collect(Collectors.toList());
        dto.setFiliais(filiaisDTO);

        return dto;
    }

    // ✅ Deletar empresa (bloqueia exclusão de matriz com filiais)
    public void delete(Long id) {
        Empresa empresa = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada com o ID: " + id));

        if (empresa.getEmpresaMatrizId() == null || empresa.getEmpresaMatrizId() == 0) {
            List<Empresa> filiais = repository.findByEmpresaMatrizId(empresa.getId());
            if (!filiais.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Não é possível deletar a matriz enquanto houver filiais associadas.");
            }
        }

        repository.deleteById(id);
    }

    // ✅ Deletar matriz e todas as filiais (recursivo)
    public void deleteCascade(Long id) {
        Empresa empresa = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada com o ID: " + id));

        List<Empresa> filiais = repository.findByEmpresaMatrizId(empresa.getId());
        for (Empresa filial : filiais) {
            deleteCascade(filial.getId());
        }

        repository.deleteById(id);
    }
}
