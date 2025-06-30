package com.dvml.api.repository;
import com.dvml.api.entity.LinhaGasto;
import com.dvml.api.entity.LinhaRequisicaoExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LinhaRequisicaoExameRepository extends JpaRepository<LinhaRequisicaoExame, Long> {
    @Query(value = "SELECT * FROM linha_requisicao_exame", nativeQuery = true)
    List<LinhaRequisicaoExame> findAllOrderByNomeAsc();

    @Query("SELECT l FROM LinhaRequisicaoExame l WHERE l.requisicaoExameId = :requisicaoExameId")
    List<LinhaRequisicaoExame> findAllByRequisicaoId(@Param("requisicaoExameId") long requisicaoExameId);

    @Query("SELECT l FROM LinhaRequisicaoExame l WHERE l.inscricaoId = :inscricaoId")
    List<LinhaRequisicaoExame> findAllByInscricaoId(@Param("inscricaoId") long inscricaoId);

    Optional<LinhaRequisicaoExame> findById(long id);
}

