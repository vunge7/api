package com.dvml.api.service;


import com.dvml.api.entity.Pessoa;
import com.dvml.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repo;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> listarTodasPessoa() {

        return pessoaRepository.findAllOrderByNomeAsc();
    }

    public Pessoa getPessoaById(long id) {
        return repo.findById(id).get();
    }


    public Pessoa getPessoaByNif(String nif) {
        return repo.findByNif(nif);
    }

    public Pessoa criar(Pessoa pessoa) {
        return repo.save(pessoa);
    }

    public Pessoa update(Pessoa pessoa) {
        Pessoa pessoaToUpdate = repo.findById(pessoa.getId()).get();
        pessoaToUpdate.setId(pessoa.getId());
        pessoaToUpdate.setEmail(pessoa.getEmail());
        pessoaToUpdate.setNome(pessoa.getNome());
        pessoaToUpdate.setGenero(pessoa.getGenero());
        pessoaToUpdate.setTelefone(pessoa.getTelefone());
        pessoaToUpdate.setEndereco(pessoa.getEndereco());
        pessoaToUpdate.setDataNascimento(pessoa.getDataNascimento());
        pessoaToUpdate.setApelido(pessoa.getApelido());
        pessoaToUpdate.setBairro(pessoa.getBairro());
        pessoaToUpdate.setNif(pessoa.getNif());
        pessoaToUpdate.setMae(pessoa.getMae());
        pessoaToUpdate.setPai(pessoa.getPai());
        pessoaToUpdate.setNacionalidade(pessoa.getNacionalidade());
        pessoaToUpdate.setLocalNascimento(pessoa.getLocalNascimento());
        pessoaToUpdate.setMunicipioEndereco(pessoa.getMunicipioEndereco());
        pessoaToUpdate.setMunicipioNascimento(pessoa.getMunicipioNascimento());
        pessoaToUpdate.setPaisEndereco(pessoa.getPaisEndereco());
        pessoaToUpdate.setPaisNascimento(pessoa.getPaisNascimento());
        pessoaToUpdate.setProvinciaEndereco(pessoa.getProvinciaEndereco());
        pessoaToUpdate.setProvinciaNascimento(pessoa.getProvinciaNascimento());
        pessoaToUpdate.setRaca(pessoa.getRaca());
        pessoaToUpdate.setHabilitacao(pessoa.getHabilitacao());
        pessoaToUpdate.setEstadoCivil(pessoa.getEstadoCivil());
        pessoaToUpdate.setProfissao(pessoa.getProfissao());





        return repo.save(pessoaToUpdate);
    }


    public Pessoa updatePhoto( String nomePhoto, long pessoId) {
        Pessoa pessoaToUpdate = repo.findById(pessoId).get();
        pessoaToUpdate.setNomePhoto(nomePhoto);
        return repo.save(pessoaToUpdate);
    }

    public ResponseEntity<String> deletePessoa(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Pessoa  deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("Pessoa não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao deletar Pessoa.");
        }
    }
}

