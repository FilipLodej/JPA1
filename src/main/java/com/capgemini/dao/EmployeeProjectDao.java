package com.capgemini.dao;


import java.util.List;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.EmployeeProjectEntity;



public interface EmployeeProjectDao extends Dao<EmployeeProjectEntity, Long> {

	List<EmployeeProjectEntity> findActiveEmployeeInProject(Long projectId);
	List<EmployeeEntity> findAllEmployeesWhoWorkedLongerThanN(int months);
	EmployeeProjectEntity findEmployeeProjectByEmployeeIdAndProjectId(Long employeeId, Long projectId);
}
