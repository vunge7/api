package com.dvml.api.repository;

import com.dvml.api.entity.Seguradora;
import com.dvml.api.entity.SourceDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceDocumentRepository extends JpaRepository<SourceDocument, Long> {
}
