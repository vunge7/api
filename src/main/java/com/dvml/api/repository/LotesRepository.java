package com.dvml.api.repository;

import com.dvml.api.entity.Lotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotesRepository extends JpaRepository<Lotes, Long> {
}