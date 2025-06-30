package com.dvml.api.service;

import com.dvml.api.dto.TriagemDTO;
import com.dvml.api.entity.Inscricao;
import com.dvml.api.entity.Paciente;
import com.dvml.api.entity.Pessoa;
import com.dvml.api.entity.Triagem;
import com.dvml.api.repository.InscricaoRepository;
import com.dvml.api.repository.PacienteRepository;
import com.dvml.api.repository.PessoaRepository;
import com.dvml.api.repository.TriagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TriagemService {

    @Autowired
    private TriagemRepository repo;
    @Autowired
    private InscricaoRepository repoInscricao;

    @Autowired
    private PacienteRepository repoPaciente;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LinhaTriagemService linhaTriagemService;

    public TriagemDTO  convertEntityToDto(Triagem triagem){
        TriagemDTO triagemDTO = new TriagemDTO();
        triagemDTO.setId(triagem.getId());
        triagemDTO.setDataCriacao(triagem.getDataCriacao());
        triagemDTO.setIncricaoId(triagem.getFkInscricao());
        triagemDTO.setPaciente(  getNomePacienteByIdInscricao(triagem.getFkInscricao()) );
        triagemDTO.setUserId( triagem.getFkUser());

        if (Objects.nonNull(triagem.getTriagens())){
            triagemDTO.setTriagens(
                    triagem.getTriagens().stream().map(
                            linhaTriagemService::convertEntityToDto
                    ).collect(Collectors.toList()) );
        }

        return triagemDTO;

    }

    private String getNomePacienteByIdInscricao(long id){
           Inscricao inscricao =  repoInscricao.findById(id).get();
           Paciente paciente =  repoPaciente.getReferenceById(inscricao.getPacienteId());
           Pessoa pessoa =  pessoaRepository.findById(paciente.getPessoaId()).get();
           return pessoa.getNome() + " " +pessoa.getApelido();
    }


    public List<TriagemDTO> listarTodos(){
        return repo.findAll().stream().map(
                this::convertEntityToDto
        ).collect(Collectors.toList());
    }

    public Triagem  salvar(Triagem entity){
          return  repo.save(entity);
    }



}
