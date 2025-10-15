package com.dvml.api.service;

import com.dvml.api.entity.Gasto;
import com.dvml.api.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GastoService {

    @Autowired
    private GastoRepository repo;

    @Autowired
    private GastoRepository GastRepository;

    public List<Gasto> listarTodosGasto() {
        return GastRepository.findAllOrderByNomeAsc();
    }

    public Gasto getGastoById(long id) {
        return repo.findById(id).get();
    }

    public Gasto criar(Gasto gasto) {
        // ⚡ empresaId já é considerado automaticamente no repo.save
        return repo.save(gasto);
    }

    public ResponseEntity<String> update(Gasto gasto){
        Gasto gastoToUpdate = repo.findById(gasto.getId()).get();
        gastoToUpdate.setStatus(gasto.getStatus());
        gastoToUpdate.setDoc(gasto.getDoc());
        gastoToUpdate.setDataCriacao(gasto.getDataCriacao());
        gastoToUpdate.setDocRef(gasto.getDocRef());
        gastoToUpdate.setInscricaoId(gasto.getInscricaoId());
        gastoToUpdate.setConvertido(gasto.getConvertido());

        // ⚡ Adicionado empresaId
        gastoToUpdate.setEmpresaId(gasto.getEmpresaId());

        if(Objects.nonNull(repo.save(gastoToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Gasto editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a gasto.");
    }

    public ResponseEntity<String> deleteGasto(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Gasto deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gasto ao deletar a Linha.");
        }
    }
}
