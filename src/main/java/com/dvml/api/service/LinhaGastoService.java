package com.dvml.api.service;

import com.dvml.api.entity.LinhaGasto;
import com.dvml.api.repository.LinhaGastoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Objects;

import java.util.List;

@Service
@Transactional
public class LinhaGastoService {
    @Autowired
    private LinhaGastoRepository repo;

    @Autowired
    private LinhaGastoRepository liRepository;


    public List<LinhaGasto> listarTodasLinhas() {
        return liRepository.findAllOrderByNomeAsc();
    }

    public List<LinhaGasto> listarLinhasGastoByGastoId(long gastoId) {
        return liRepository.getAllLinhasGastoByIdGastos(gastoId);
    }
    public LinhaGasto getLineById(long id) {
        return repo.findById(id).get();
    }

    public ResponseEntity<String> criar(LinhaGasto linha) {
        if (Objects.nonNull(repo.save(linha))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o Linha.");
    }
    public ResponseEntity<String> update(LinhaGasto linha){
        LinhaGasto linhaGastoToUpdate = repo.findById(linha.getId()).get();
        linhaGastoToUpdate.setGastoId(linha.getGastoId());
        linhaGastoToUpdate.setIva(linha.getIva());
        linhaGastoToUpdate.setDesconto(linha.getDesconto());
        linhaGastoToUpdate.setQuantidade(linha.getQuantidade());
        linhaGastoToUpdate.setDataInsercao(linha.getDataInsercao());
        linhaGastoToUpdate.setServicoDescricao(linha.getServicoDescricao());
        linhaGastoToUpdate.setServicoId(linha.getServicoId());

        if(Objects.nonNull(repo.save(linhaGastoToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha gasto editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a Linha gasto.");
    }
    public  ResponseEntity<String> deleteLinhaGasto(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha gasto deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("Linha não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao deletar a Linha gasto.");
        }

    }

    @Transactional
    public ResponseEntity<String> deleteLinhaPeloGasto(long gastoId) {
        if (repo.existsByGastoId(gastoId)) {
            repo.deleteByGastoId(gastoId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Linha com gastoId " + gastoId + " deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Falha ao deletar: nenhuma linha encontrada com gastoId " + gastoId);
        }
    }



}