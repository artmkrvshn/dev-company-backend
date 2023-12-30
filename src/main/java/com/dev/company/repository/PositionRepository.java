package com.dev.company.repository;

import com.dev.company.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> readById(UUID id);

    boolean existsById(UUID id);

    boolean existsByName(String name);

    void deleteById(UUID id);

    @Query("select check_employee_count_by_position_uuid(:id)")
    boolean hasEmployees(UUID id);
}
