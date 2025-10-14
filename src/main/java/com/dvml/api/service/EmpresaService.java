package com.dvml.api.service;

import com.dvml.api.dto.EmpresaDTO;
import com.dvml.api.entity.Empresa;
import com.dvml.api.repository.EmpresaRepository;
import com.dvml.api.util.TipoEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repo;

    // ===================================
    // ➕ CRIAR EMPRESA
    // ===================================
    public EmpresaDTO criar(EmpresaDTO dto) {
        // Verificar se já existe empresa com o mesmo NIF
        List<Empresa> existentes = repo.findByNif(dto.getNif());
        if (!existentes.isEmpty()) {
            throw new IllegalArgumentException("Já existe uma empresa com este NIF: " + dto.getNif());
        }

        Empresa nova = new Empresa();
        nova.setNome(dto.getNome());
        nova.setTipo(dto.getTipo() != null ? dto.getTipo() : TipoEmpresa.MATRIZ);
        nova.setNif(dto.getNif());
        nova.setEmail(dto.getEmail());
        nova.setTelefone(dto.getTelefone());
        nova.setEndereco(dto.getEndereco());
        nova.setSeguradoraId(dto.getSeguradoraId());

        // Se não for informado empresaMatrizId, define como 0 (é matriz)
        nova.setEmpresaMatrizId(dto.getEmpresaMatrizId() == null ? 0L : dto.getEmpresaMatrizId());

        Empresa salvo = repo.save(nova);
        return mapToDTO(salvo);
    }

    // ===================================
    // 🔁 ATUALIZAR EMPRESA
    // ===================================
    public EmpresaDTO update(EmpresaDTO dto) {
        Optional<Empresa> opt = repo.findById(dto.getId());
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Empresa não encontrada com o ID: " + dto.getId());
        }

        Empresa empresa = opt.get();
        empresa.setNome(dto.getNome());
        empresa.setTipo(dto.getTipo());
        empresa.setNif(dto.getNif());
        empresa.setEmail(dto.getEmail());
        empresa.setTelefone(dto.getTelefone());
        empresa.setEndereco(dto.getEndereco());
        empresa.setSeguradoraId(dto.getSeguradoraId());
        empresa.setEmpresaMatrizId(dto.getEmpresaMatrizId() == null ? 0L : dto.getEmpresaMatrizId());

        Empresa atualizada = repo.save(empresa);
        return mapToDTO(atualizada);
    }

    // ===================================
    // 📋 LISTAR TODAS AS EMPRESAS
    // ===================================
    public List<EmpresaDTO> listarTodas() {
        return repo.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ===================================
    // 🔍 BUSCAR POR ID
    // ===================================
    public EmpresaDTO buscarPorId(Long id) {
        Optional<Empresa> opt = repo.findById(id);
        return opt.map(this::mapToDTO).orElse(null);
    }

    // ===================================
    // ❌ DELETAR EMPRESA
    // ===================================
    public boolean deletarPorId(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // ===================================
    // 🔁 MAPEAMENTO ENTITY → DTO
    // ===================================
    private EmpresaDTO mapToDTO(Empresa e) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(e.getId());
        dto.setNome(e.getNome());
        dto.setTipo(e.getTipo());
        dto.setEmpresaMatrizId(e.getEmpresaMatrizId());
        dto.setNif(e.getNif());
        dto.setEmail(e.getEmail());
        dto.setTelefone(e.getTelefone());
        dto.setEndereco(e.getEndereco());
        dto.setSeguradoraId(e.getSeguradoraId());
        return dto;
    }
}
