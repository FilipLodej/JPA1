package com.capgemini.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.EmployeeProjectDao;
import com.capgemini.dao.ProjectDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.EmployeeProjectEntity;
import com.capgemini.domain.ProjectEntity;
import com.capgemini.domain.RoleEntity;
import com.capgemini.service.ProjectService;
import com.capgemini.service.exception.EmptyResultException;
import com.capgemini.service.exception.ValidationDataException;

@Service

@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectDao projectDao;

	@Autowired
	EmployeeProjectDao employeeProjectDao;

	@Override
	public ProjectEntity addProject(ProjectEntity project) {
		return projectDao.save(project);
	}

	@Override
	public void removeProject(Long id) {
		projectDao.delete(id);
	}

	@Override
	public ProjectEntity updateProject(ProjectEntity project) {
		return projectDao.update(project);
	}

	@Override
	public ProjectEntity findProject(Long id) {
		return projectDao.findOne(id);
	}

	@Override
	public EmployeeProjectEntity addEmployeeToProject(EmployeeEntity employee, ProjectEntity project, double salary,
			RoleEntity role) {
		EmployeeProjectEntity employeeProject = new EmployeeProjectEntity(salary, role, project, employee);
		employeeProjectDao.save(employeeProject);
		return employeeProject;
	}

	@Override
	public EmployeeProjectEntity endEmployeesWorkInProject(Long employeeId, Long projectId) throws Exception {
		if (employeeId == null || projectId == null) {
			throw new ValidationDataException("Employee id or project id cannot be empty!");
		}
		try {
			EmployeeProjectEntity employeeProject = employeeProjectDao
					.findEmployeeProjectByEmployeeIdAndProjectId(employeeId, projectId);
			employeeProject.setEndDate(LocalDate.now());
			return employeeProject;
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultException("Employee and project doesnt exists!");
		}
	}

	@Override
	public List<EmployeeEntity> findAllEmployeesWhoWorkedLongerThanN(int months) {
		return employeeProjectDao.findAllEmployeesWhoWorkedLongerThanN(months);
	}

	@Override
	public List<EmployeeProjectEntity> findActiveEmployeeInProject(Long projectId) throws Exception {
			List<EmployeeProjectEntity> employeeProject=employeeProjectDao.findActiveEmployeeInProject(projectId);
			if(employeeProject.isEmpty()){
				throw new EmptyResultException("Empty result!");
			}
		return employeeProject; 
		}
	
}
