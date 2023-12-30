package com.dev.company.repository;

import com.dev.company.entity.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from Project p left join fetch p.employees left join fetch p.status left join fetch p.chief c left join fetch c.position left join fetch c.department")
    List<Project> readAllWithStatusAndChief();

    @Query("select p from Project p left join fetch p.employees left join fetch p.status left join fetch p.chief c left join fetch c.position left join fetch c.department")
    List<Project> readAllWithStatusAndChief(Sort sort);

    @Query("select p from Project p left join fetch p.chief left join fetch p.employees left join fetch p.status where p.id = :id")
    Optional<Project> readWithChiefAndEmployeesAndStatuses(UUID id);

    boolean existsByName(String name);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
