package com.dev.company.repository;

import com.dev.company.entity.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e left join fetch e.department left join fetch e.position where e.id = :id")
    Optional<Employee> readWithDepartmentAndPositionById(@Param("id") UUID id);

    @Query("select e from Employee e left join fetch e.department left join fetch e.position")
    List<Employee> readAllWithDepartmentAndPosition(Sort sort);

    boolean existsById(UUID employeeId);

    void deleteById(UUID id);

    boolean existsByDepartmentId(UUID id);
}
