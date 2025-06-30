package com.dvml.api.service;


import com.dvml.api.entity.Seguradora;
import com.dvml.api.repository.SeguradoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SeguradoraService {

    @Autowired
    private SeguradoraRepository repo;


    public List<Seguradora> listarTodasSeguradoras(){
        return repo.findAll();
    }


    public Seguradora getSeguradoraById(long id){
        return repo.findById(id).get();
    }
    public Seguradora criar(Seguradora seguradora){
        return repo.save(seguradora);
    }
}
