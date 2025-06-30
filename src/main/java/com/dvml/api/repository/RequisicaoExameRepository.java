package com.dvml.api.repository;

import com.dvml.api.entity.RequisicaoExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequisicaoExameRepository extends JpaRepository<RequisicaoExame, Long> {
    @Query(value = "SELECT * FROM requisicao_exame", nativeQuery = true)
    List<RequisicaoExame> findAllOrderByNomeAsc();


}
