package com.dvml.api.repository;

import com.dvml.api.dto.PainelDTO;
import com.dvml.api.entity.Painel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PainelRepository extends JpaRepository<Painel, Long> {


}
