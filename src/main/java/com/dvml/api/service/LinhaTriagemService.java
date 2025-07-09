package com.dvml.api.service;


import com.dvml.api.dto.LinhaTriagemDTO;
import com.dvml.api.dto.TriagemDTO;
import com.dvml.api.entity.LinhaTriagem;
import com.dvml.api.entity.Triagem;
import com.dvml.api.repository.LinhaTriagemRepositoty;
import com.dvml.api.repository.TriagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinhaTriagemService {
    @Autowired
    private LinhaTriagemRepositoty repo;


    public LinhaTriagemDTO convertEntityToDto(LinhaTriagem entity){



        LinhaTriagemDTO linhaTriagemDTO = new LinhaTriagemDTO();
        linhaTriagemDTO.setCampo(entity.getCampo());
        linhaTriagemDTO.setValor(entity.getValor());
        linhaTriagemDTO.setUnidade(entity.getUnidade());
        linhaTriagemDTO.setTriagemId(entity.getTriagemId());
        return linhaTriagemDTO;
    }


    public List<LinhaTriagemDTO> listarTodos(){
        return repo.findAll().stream().map(
                this::convertEntityToDto
        ).collect(Collectors.toList());
    }

    public LinhaTriagem salvar(LinhaTriagem entity){
        return  repo.save(entity);
    }






}
