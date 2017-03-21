package com.capgemini.dao;

import java.util.List;

import com.capgemini.domain.EmployeeEntity;

public interface EmployeeDao extends Dao<EmployeeEntity, Long> {

	List<EmployeeEntity> findByNameAndSurname(String name, String surname);

	List<EmployeeEntity> findByDepartment(Long departmentId);
	
	List<EmployeeEntity> findManagers(Long employeeId);

}
