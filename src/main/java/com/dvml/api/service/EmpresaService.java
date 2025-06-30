package com.dvml.api.service;


import com.dvml.api.entity.Empresa;
import com.dvml.api.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repo;


    public List<Empresa> listarTodasEmpresa(){

        return repo.findAll();
    }


    public Empresa getEmpresaById(long id){
        return repo.findById(id).get();
    }
    public Empresa criar(Empresa empresa){
        return repo.save(empresa);
    }

    public Empresa update(Empresa empresa) {
        Empresa EmpresaToUpdate = repo.findById(empresa.getId()).get();
        EmpresaToUpdate.setTelefone(empresa.getTelefone());
        EmpresaToUpdate.setId(empresa.getId());
        EmpresaToUpdate.setNome(empresa.getNome());
        EmpresaToUpdate.setNif(empresa.getNif());
        EmpresaToUpdate.setSeguradoraId(empresa.getSeguradoraId());
        return repo.save(EmpresaToUpdate);
    }

    public void deletEmpresa(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Empresa não encontrado com o ID: " + id);
        }
    }
    }
