package com.springbootapp.emlpoyee.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springbootapp.emlpoyee.entity.Employee;
import com.springbootapp.emlpoyee.exception.ResourceNotFound;
import com.springbootapp.emlpoyee.repository.EmployeeRepository;

import lombok.NonNull;

@Service
public class EmployeeService {


	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Employee save(@NonNull Employee employee) {
		employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
		return employeeRepository.save(employee);
	}

	public List<Employee> saveAll(@NonNull List<Employee> employees) {
		return employeeRepository.saveAll(employees);
	}

	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Employee", "Id", id));
	}

	public void deleteEmployee(Long id) {
		employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Employee", "Id", id));
		employeeRepository.deleteById(id);
	}

	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Pageable emPageable = null;
		if (sortDirection.equalsIgnoreCase("asc")) {
			emPageable = PageRequest.of(pageNo, pageSize).withSort(Sort.by(sortField).ascending());
		} else {
			emPageable = PageRequest.of(pageNo, pageSize).withSort(Sort.by(sortField).descending());
		}
		return employeeRepository.findAll(emPageable);
	}

	public Page<Employee> searchEmployee(String keyword, int pageNo, int pageSize) {
		Pageable emPageable = PageRequest.of(pageNo, pageSize);
		return employeeRepository.search(keyword, emPageable);
	}
}
