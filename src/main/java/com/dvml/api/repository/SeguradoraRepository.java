package com.dvml.api.repository;

import com.dvml.api.entity.Seguradora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeguradoraRepository extends JpaRepository<Seguradora, Long> {

}
