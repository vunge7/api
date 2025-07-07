package com.dvml.api.service;

import com.dvml.api.dto.RequisicaoExameDTO;
import com.dvml.api.entity.*;
import com.dvml.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import java.util.List;

@Service
public class RequisicaoExameService {
    @Autowired
    private RequisicaoExameRepository repo;

    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    private PessoaRepository pessoaRepo;

    @Autowired
    private FuncionarioRepository funcionarioRepo;


    @Autowired
    private InscricaoRepository inscricaoRepo;

    @Autowired
    private PacienteRepository pacienteRepo;

    public RequisicaoExame getRequisicaoById(long id) {
        return repo.findById(id).get();
    }

    public List<RequisicaoExame> listarTodasRequisicoes() {
        return repo.findAllOrderByNomeAsc();
    }


    public List<RequisicaoExameDTO> listarTodasRequisicoesComposto() {
        List<RequisicaoExame> listaRequisicao = repo.findAllOrderByNomeAsc();
        List<RequisicaoExameDTO> listaReuisicaoDTO = new ArrayList<>();

        for (RequisicaoExame l : listaRequisicao) {
            RequisicaoExameDTO linha = new RequisicaoExameDTO();
            linha.setId(l.getId());
            linha.setMedico(getPessoaBydUsuario( l.getUsuarioId()).getNome() + " " + getPessoaBydUsuario( l.getUsuarioId()).getApelido());
            linha.setPaciente(getPessoaBydInscricao( l.getInscricaoId()).getNome() + " " + getPessoaBydInscricao( l.getUsuarioId()).getApelido());
            linha.setData(l.getDataRequisicao());
            listaReuisicaoDTO.add(linha);
        }

        return listaReuisicaoDTO;
    }


    private Pessoa getPessoaBydInscricao(long idInscricao) {

        Inscricao inscricao = inscricaoRepo.findById(idInscricao).get();
        Paciente paciente = pacienteRepo.findById(inscricao.getPacienteId()).get();
        Pessoa pessoa = pessoaRepo.findById(paciente.getPessoaId()).get();

        return pessoa;
    }


    private Pessoa getPessoaBydUsuario(long idUser) {
        Usuario usuario = userRepo.findById(idUser).get();
        long idPessoa = funcionarioRepo.findById(usuario.getFuncionarioId()).get().getPessoaId();
        Pessoa pessoa = pessoaRepo.findById(idPessoa).get();
        return pessoa;
    }


    public RequisicaoExame criar(RequisicaoExame requisicaoExame) {

        requisicaoExame.setDataRequisicao(new Date());

        return repo.save(requisicaoExame);
       /*
        if(Objects.nonNull(repo.save(requisicaoExame))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Requisicao criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o Requisicao.");

        */
    }

    public ResponseEntity<String> update(RequisicaoExame requisicaoExame) {
        RequisicaoExame RequisicaoToUpdate = repo.findById(requisicaoExame.getId()).get();
        RequisicaoToUpdate.setDataRequisicao(requisicaoExame.getDataRequisicao());
        RequisicaoToUpdate.setStatus(requisicaoExame.getStatus());
        RequisicaoToUpdate.setUsuarioId(requisicaoExame.getUsuarioId());
        if (Objects.nonNull(repo.save(requisicaoExame))) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Requisicao editado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao editar o Requisicao.");
    }

    public ResponseEntity<String> deleteRequisicao(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body("Requisicao deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("Linha não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar a Requisicao.");
        }
    }
}
