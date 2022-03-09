package com.springbootapp.emlpoyee.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springbootapp.emlpoyee.entity.Employee;
import com.springbootapp.emlpoyee.exception.ResourceNotFound;
import com.springbootapp.emlpoyee.repository.EmployeeRepository;

import lombok.NonNull;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee save(@NonNull Employee employee) {
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

	public List<Employee> getEmplyeesByFirstNameAndLastNameLike(String firstName, String lastName) {
		return employeeRepository.findByFirstNameAndLastNameContains(firstName, lastName);
	}

	public List<Employee> getEmployeesOrderByFirstName() {
		return employeeRepository.findAllOrderByFirstName();
	}

	public Employee updateEmployee(Employee employee, Long id) {
		Employee existingEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Employee", "Id", id));

		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		existingEmployee.setGender(employee.getGender());
		existingEmployee.setJobTitle(employee.getJobTitle());
		existingEmployee.setSalary(employee.getSalary());
		existingEmployee.setDepartment(employee.getDepartment());

		return employeeRepository.save(existingEmployee);
	}

	public void deleteEmployee(Long id) {
		employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Employee", "Id", id));
		employeeRepository.deleteById(id);
	}

	public Page<Employee> findPaginated(int pageNo, int pageSize) {
		Pageable emPageable = PageRequest.of(pageNo, pageSize);
		return employeeRepository.findAll(emPageable);
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
}
