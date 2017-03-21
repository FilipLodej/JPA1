package com.capgemini.service;

import java.util.List;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.EmployeeProjectEntity;
import com.capgemini.domain.ProjectEntity;
import com.capgemini.domain.RoleEntity;


public interface ProjectService {

	ProjectEntity addProject(ProjectEntity project);
	void removeProject(Long id);
	ProjectEntity updateProject(ProjectEntity project);
	ProjectEntity findProject(Long id);
	
	EmployeeProjectEntity addEmployeeToProject(EmployeeEntity employee, ProjectEntity project, double salary, RoleEntity role);
	EmployeeProjectEntity endEmployeesWorkInProject(Long employeeId, Long projectId) throws Exception ;
	List<EmployeeProjectEntity> findActiveEmployeeInProject(Long projectId) throws Exception;
	List<EmployeeEntity> findAllEmployeesWhoWorkedLongerThanN(int months);
}
