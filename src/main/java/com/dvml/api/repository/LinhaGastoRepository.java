package com.dvml.api.repository;

import com.dvml.api.entity.Inscricao;
import com.dvml.api.entity.LinhaGasto;
import com.dvml.api.util.EstadoInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LinhaGastoRepository extends JpaRepository<LinhaGasto, Long> {
    @Query(value = "SELECT * FROM linha_gasto", nativeQuery = true)
    List<LinhaGasto> findAllOrderByNomeAsc();
    void deleteByGastoId(long gastoId);
    boolean existsByGastoId(long gastoId);

    @Query(value = "SELECT * FROM linha_gasto i WHERE i.gasto_id = :gastoId", nativeQuery = true)
    List<LinhaGasto> getAllLinhasGastoByIdGastos(long gastoId );
}
