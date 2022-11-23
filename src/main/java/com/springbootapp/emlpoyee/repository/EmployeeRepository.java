package com.springbootapp.emlpoyee.repository;

import com.springbootapp.emlpoyee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    @Query(value = "SELECT * FROM Employees e WHERE e.first_name LIKE %?1%"
            + " OR e.last_name LIKE %?1%"
            + " OR e.email LIKE %?1%"
            + " OR e.department LIKE %?1%"
            + " OR e.job_title LIKE %?1%", nativeQuery = true)
    Page<Employee> search(String keyword, Pageable pageable);
}
