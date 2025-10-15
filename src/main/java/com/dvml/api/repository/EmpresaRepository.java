package com.dvml.api.repository;

import com.dvml.api.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    // ✅ Lista todas as empresas ativas, ordenadas por nome
    List<Empresa> findAllByStatusTrueOrderByNomeAsc();

    // ✅ Busca empresas pelo NIF (para validação de duplicidade)
    List<Empresa> findByNif(String nif);

    // ✅ Busca todas as filiais diretas de uma empresa matriz
    List<Empresa> findByEmpresaMatrizId(Long empresaMatrizId);
}
