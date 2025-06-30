package com.dvml.api.repository;

import com.dvml.api.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query(value = "SELECT * FROM Pessoa", nativeQuery = true)
    List<Pessoa> findAllOrderByNomeAsc();

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM pessoa WHERE nif = :nif", nativeQuery = true)
    long existePessoaByNif(@Param("nif") String nif);

    Pessoa findByNif(String nif);

}

