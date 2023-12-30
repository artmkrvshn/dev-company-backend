package com.dev.company.repository;

import com.dev.company.entity.Status;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Profile("jpa")
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    boolean existsByName(String name);

    void deleteById(UUID id);

    boolean existsById(UUID id);

    Optional<Status> findById(UUID id);

    @Query("select check_project_count_by_status_uuid(:id)")
    boolean hasProjects(UUID id);

    boolean existsByIdAndNameNot(UUID id, String name);
}
