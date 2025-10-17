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

    // ✅ Buscar requisição por ID
    public RequisicaoExame getRequisicaoById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));
    }

    // ✅ Listar todas as requisições
    public List<RequisicaoExame> listarTodasRequisicoes() {
        return repo.findAllOrderByNomeAsc();
    }

    // ✅ Listar todas as requisições com informações compostas (médico + paciente)
    public List<RequisicaoExameDTO> listarTodasRequisicoesComposto() {
        try {
            List<RequisicaoExame> listaRequisicao = repo.findAllOrderByNomeAsc();
            List<RequisicaoExameDTO> listaRequisicaoDTO = new ArrayList<>();

            for (RequisicaoExame l : listaRequisicao) {
                RequisicaoExameDTO linha = new RequisicaoExameDTO();
                linha.setId(l.getId());
                linha.setMedico(getPessoaByUsuario(l.getUsuarioId()).getNome() + " " + getPessoaByUsuario(l.getUsuarioId()).getApelido());
                linha.setPaciente(getPessoaByInscricao(l.getInscricaoId()).getNome() + " " + getPessoaByInscricao(l.getInscricaoId()).getApelido());
                linha.setData(l.getDataRequisicao());
                linha.setEmpresaId(l.getEmpresaId()); // ✅ Incluído o campo empresaId
                listaRequisicaoDTO.add(linha);
            }

            return listaRequisicaoDTO;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao listar todas as requisições compostas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ✅ Obter pessoa através da inscrição
    private Pessoa getPessoaByInscricao(long idInscricao) {
        Inscricao inscricao = inscricaoRepo.findById(idInscricao).orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
        Paciente paciente = pacienteRepo.findById(inscricao.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return pessoaRepo.findById(paciente.getPessoaId()).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    // ✅ Obter pessoa através do usuário
    private Pessoa getPessoaByUsuario(long idUser) {
        Usuario usuario = userRepo.findById(idUser).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Funcionario funcionario = funcionarioRepo.findById(usuario.getFuncionarioId()).orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return pessoaRepo.findById(funcionario.getPessoaId()).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    // ✅ Criar requisição
    public RequisicaoExame criar(RequisicaoExame requisicaoExame) {
        requisicaoExame.setDataRequisicao(new Date());
        // ✅ Considera empresaId que vem do objeto
        return repo.save(requisicaoExame);
    }

    // ✅ Atualizar requisição
    public ResponseEntity<String> update(RequisicaoExame requisicaoExame) {
        RequisicaoExame requisicaoToUpdate = repo.findById(requisicaoExame.getId())
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));

        requisicaoToUpdate.setDataRequisicao(requisicaoExame.getDataRequisicao());
        requisicaoToUpdate.setStatus(requisicaoExame.getStatus());
        requisicaoToUpdate.setUsuarioId(requisicaoExame.getUsuarioId());
        requisicaoToUpdate.setInscricaoId(requisicaoExame.getInscricaoId());
        requisicaoToUpdate.setEmpresaId(requisicaoExame.getEmpresaId()); // ✅ campo empresaId

        if (Objects.nonNull(repo.save(requisicaoToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Requisição editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a Requisição.");
    }

    // ✅ Deletar requisição
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
