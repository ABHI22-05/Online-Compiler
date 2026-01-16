package com.compiler.repository;

import com.compiler.model.CodeSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeSubmissionRepository extends JpaRepository<CodeSubmission, Long> {
    Optional<CodeSubmission> findByShareId(String shareId);

    List<CodeSubmission> findByUserIdOrderByCreatedAtDesc(String userId);

    List<CodeSubmission> findTop10ByOrderByCreatedAtDesc();
}
