package com.dvml.api.repository;

import com.dvml.api.entity.ResultadoExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoExameRepository extends JpaRepository<ResultadoExame, Long> {

}
