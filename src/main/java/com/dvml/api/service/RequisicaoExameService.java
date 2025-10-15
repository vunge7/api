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
import java.util.List;
import java.util.Objects;

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
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Requisição não encontrada"));
    }

    public List<RequisicaoExame> listarTodasRequisicoes() {
        return repo.findAllOrderByNomeAsc();
    }

    public List<RequisicaoExameDTO> listarTodasRequisicoesComposto() {
        List<RequisicaoExame> listaRequisicao = repo.findAllOrderByNomeAsc();
        List<RequisicaoExameDTO> listaRequisicaoDTO = new ArrayList<>();

        for (RequisicaoExame l : listaRequisicao) {
            RequisicaoExameDTO linha = new RequisicaoExameDTO();
            linha.setId(l.getId());
            linha.setMedico(getPessoaBydUsuario(l.getUsuarioId()).getNome() + " " + getPessoaBydUsuario(l.getUsuarioId()).getApelido());
            linha.setPaciente(getPessoaBydInscricao(l.getInscricaoId()).getNome() + " " + getPessoaBydInscricao(l.getInscricaoId()).getApelido());
            linha.setData(l.getDataRequisicao());
            listaRequisicaoDTO.add(linha);
        }

        return listaRequisicaoDTO;
    }

    private Pessoa getPessoaBydInscricao(long idInscricao) {
        Inscricao inscricao = inscricaoRepo.findById(idInscricao).get();
        Paciente paciente = pacienteRepo.findById(inscricao.getPacienteId()).get();
        return pessoaRepo.findById(paciente.getPessoaId()).get();
    }

    private Pessoa getPessoaBydUsuario(long idUser) {
        Usuario usuario = userRepo.findById(idUser).get();
        long idPessoa = funcionarioRepo.findById(usuario.getFuncionarioId()).get().getPessoaId();
        return pessoaRepo.findById(idPessoa).get();
    }

    public RequisicaoExame criar(RequisicaoExame requisicaoExame) {
        requisicaoExame.setDataRequisicao(new Date());
        // ✅ Considera empresaId que vem do objeto
        return repo.save(requisicaoExame);
    }

    public ResponseEntity<String> update(RequisicaoExame requisicaoExame) {
        RequisicaoExame requisicaoToUpdate = repo.findById(requisicaoExame.getId())
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));

        requisicaoToUpdate.setDataRequisicao(requisicaoExame.getDataRequisicao());
        requisicaoToUpdate.setStatus(requisicaoExame.getStatus());
        requisicaoToUpdate.setUsuarioId(requisicaoExame.getUsuarioId());
        requisicaoToUpdate.setInscricaoId(requisicaoExame.getInscricaoId());

        // ✅ Atualizando empresaId
        requisicaoToUpdate.setEmpresaId(requisicaoExame.getEmpresaId());

        if (Objects.nonNull(repo.save(requisicaoToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Requisição editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a Requisição.");
    }

    public ResponseEntity<String> deleteRequisicao(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Requisição deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a Requisição.");
        }
    }
}
