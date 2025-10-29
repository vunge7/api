package com.dvml.api.repository;

import com.dvml.api.entity.Filial;
import com.dvml.api.entity.PainelPermissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PainelPermissaoRepository extends JpaRepository<PainelPermissao, Long> {
    List<PainelPermissao> findByUsuarioId(Long usuarioId);
    List<PainelPermissao> findByUsuarioIdAndFilialId(Long usuarioId, Long filialId);


    @Query(value ="SELECT filial_id FROM painel_permissao WHERE usuario_id = :usuario_id GROUP BY filial_id" , nativeQuery = true)
    List<Long> findIdsFiliaisByUsuarioIdAndEmpresaId(@Param("usuario_id") Long usuarioId);

    // 👇 Adicione esse método:
    boolean existsByUsuarioIdAndPainelIdAndFilialId(Long usuarioId, Long painelId, Long filialId);
}
