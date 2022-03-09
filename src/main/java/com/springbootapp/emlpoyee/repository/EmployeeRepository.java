package com.springbootapp.emlpoyee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springbootapp.emlpoyee.entity.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Page<Employee> findAll(Pageable pageable);
	
	Optional<Employee> findById(Long Id);
	
	@Query(value="SELECT e FROM Employee e WHERE e.firstName LIKE %:fn% AND e.lastName LIKE %:ln%")
	List<Employee> findByFirstNameAndLastNameContains(@Param("fn") String firstName,@Param("ln") String lastName);
	
	@Query(value="SELECT * FROM employees Order By first_name,last_name",nativeQuery = true)
	List<Employee> findAllOrderByFirstName();
}
