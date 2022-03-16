package com.springbootapp.emlpoyee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springbootapp.emlpoyee.entity.Employee;
import com.springbootapp.emlpoyee.entity.EmployeeDetails;
import com.springbootapp.emlpoyee.exception.ResourceNotFound;
import com.springbootapp.emlpoyee.repository.EmployeeRepository;

@Service
public class EmployeeDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> {
			throw new ResourceNotFound("Employee", "email", email);
		});

		return new EmployeeDetails(employee);
	}

}
