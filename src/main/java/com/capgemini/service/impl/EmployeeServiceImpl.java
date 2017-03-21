package com.capgemini.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.DepartmentDao;
import com.capgemini.dao.EmployeeDao;
import com.capgemini.domain.DepartmentEntity;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.service.EmployeeService;
import com.capgemini.service.exception.DeleteException;
import com.capgemini.service.exception.ValidationDataException;

@Profile(value = "jpql")
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	DepartmentDao departmentDao;


	@Override
	public EmployeeEntity saveEmployee(EmployeeEntity employeeToAdd) {
		return employeeDao.save(employeeToAdd);
	}

	@Override
	public EmployeeEntity removeEmployee(Long employeeId) throws DeleteException {
		List<EmployeeEntity> manager = employeeDao.findManagers(employeeId);
		if(!manager.isEmpty()){
			throw new DeleteException("Cannot remove a manager");
		}
		EmployeeEntity employee = employeeDao.findOne(employeeId);
		employeeDao.delete(employee);
		return employee;
	}

	@Override
	public EmployeeEntity updateEmployee(EmployeeEntity employeeToUpdate)  {
		return employeeDao.update(employeeToUpdate);
	}

	@Override
	public List<EmployeeEntity> findByNameAndSurname(String name, String surname) throws ValidationDataException  {
		if(name==null||surname==null){
			throw new ValidationDataException("Name or surname cannot be empty!");
		}
		return employeeDao.findByNameAndSurname(name, surname);
	}

	@Override
	public List<EmployeeEntity> findByDepartment(Long departmentId) {
		return employeeDao.findByDepartment(departmentId);
	}

	@Override
	public EmployeeEntity assignToDepartment(Long employeeId, Long departmentId) {
		EmployeeEntity employee = employeeDao.getOne(employeeId);
		DepartmentEntity department = departmentDao.findOne(departmentId);
		employee.setDepartment(department);
		return employeeDao.update(employee);
	}

	@Override
	public EmployeeEntity findById(Long id) {
		return employeeDao.findOne(id);
	}

}
