package com.dvml.api.repository;

import com.dvml.api.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LineRepository extends JpaRepository<Line, Long> {
    @Query(value = "SELECT * FROM line", nativeQuery = true)
    List<Line> findAllOrderByNomeAsc();
}
