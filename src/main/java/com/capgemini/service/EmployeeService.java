package com.capgemini.service;

import java.util.List;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.service.exception.DeleteException;
import com.capgemini.service.exception.ValidationDataException;

public interface EmployeeService {

	EmployeeEntity saveEmployee(EmployeeEntity employeeToAdd);

	EmployeeEntity removeEmployee(Long employeeId) throws DeleteException;

	EmployeeEntity updateEmployee(EmployeeEntity employeeToUpdate);

	EmployeeEntity assignToDepartment(Long employeeId, Long departmentId);

	List<EmployeeEntity> findByNameAndSurname(String name, String surname)throws ValidationDataException;

	List<EmployeeEntity> findByDepartment(Long departmentId);
	
	EmployeeEntity findById(Long id);
}
