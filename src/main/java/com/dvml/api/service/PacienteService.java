package com.dvml.api.service;


import com.dvml.api.dto.PacienteDTO;
import com.dvml.api.entity.Paciente;
import com.dvml.api.entity.Pessoa;
import com.dvml.api.repository.PacienteRepository;
import com.dvml.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repo;

    @Autowired
    private PessoaRepository repoPessoa;


    public PacienteDTO convertEntityToDto(Paciente paciente){

        Pessoa pessoa = repoPessoa.findById(paciente.getPessoaId()).get();


        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(paciente.getId());
        pacienteDTO.setNome(pessoa.getNome());
        pacienteDTO.setApelido(pessoa.getApelido());
        pacienteDTO.setNif(pessoa.getNif());
        pacienteDTO.setBairro(pessoa.getBairro());
        pacienteDTO.setProfissao(pessoa.getProfissao());
        pacienteDTO.setEstadoCivil(pessoa.getEstadoCivil());
        pacienteDTO.setHabilitacao(pessoa.getHabilitacao());
        pacienteDTO.setNacionalidade(pessoa.getNacionalidade());
        pacienteDTO.setRaca(pessoa.getRaca());
        pacienteDTO.setDataNascimento(pessoa.getDataNascimento());
        pacienteDTO.setPai(pessoa.getPai());
        pacienteDTO.setMae(pessoa.getMae());
        pacienteDTO.setGenero(  pessoa.getGenero().toString());
        pacienteDTO.setPaisEndereco(pessoa.getPaisEndereco());
        pacienteDTO.setProvinciaEndereco(pessoa.getProvinciaEndereco());
        pacienteDTO.setMunicipioEndereco(pessoa.getMunicipioEndereco());
        pacienteDTO.setPaisNascimento(pessoa.getPaisNascimento());
        pacienteDTO.setProvinciaNascimento(pessoa.getProvinciaNascimento());
        pacienteDTO.setMunicipioNascimento(pessoa.getMunicipioNascimento());
        pacienteDTO.setLocalNascimento(pessoa.getLocalNascimento());
        pacienteDTO.setPessoaId(pessoa.getId());
        pacienteDTO.setEndereco(pessoa.getEndereco());
        pacienteDTO.setNomePhoto(pessoa.getNomePhoto());

        return pacienteDTO;
    }


    public Paciente adicionar(Paciente paciente){
        paciente.setDataCadastro(new Date());
        paciente.setDataActualizacao(new Date());
        return  repo.save(paciente);
    }

    public Paciente update(Paciente paciente){
        Paciente pacienteToUpdate = repo.findById(paciente.getId()).get();
        pacienteToUpdate.setDataActualizacao(new Date());
        return  repo.save(pacienteToUpdate);
    }

    public List<PacienteDTO> listarTodos(){
        return repo.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public PacienteDTO getPacienteById(long id){
          Paciente paciente =   repo.findById(id).get();
          return  convertEntityToDto(paciente);
    }



}

