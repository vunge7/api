package com.dvml.api.repository;

import com.dvml.api.entity.LinhaRequisicaoExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinhaRequisicaoExameRepository extends JpaRepository<LinhaRequisicaoExame, Long> {

    @Query(value = "SELECT * FROM linha_requisicao_exame ORDER BY nome ASC", nativeQuery = true)
    List<LinhaRequisicaoExame> findAllOrderByNomeAsc();

    @Query(value = "SELECT * FROM linha_requisicao_exame WHERE requisicao_exame_id = :requisicaoExameId", nativeQuery = true)
    List<LinhaRequisicaoExame> findAllByRequisicaoId(@Param("requisicaoExameId") long requisicaoExameId);
}
