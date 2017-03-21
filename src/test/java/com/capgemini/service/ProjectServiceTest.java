package com.capgemini.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StatusDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.EmployeeProjectEntity;
import com.capgemini.domain.ProjectEntity;
import com.capgemini.domain.RoleEntity;
import com.capgemini.domain.StatusEntity;
import com.capgemini.service.exception.EmptyResultException;
import com.capgemini.service.exception.ValidationDataException;

@ActiveProfiles(profiles = { "jpql, mysql" })
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private StatusDao statusDao;

	@Autowired
	private EntityManager em;

	@Test
	public void shouldAddProject() {
		// given
		Long id = 11L;
		EmployeeEntity employee = employeeDao.findOne(id);
		StatusEntity status = statusDao.findOne(1L);
		ProjectEntity projectToAdd = new ProjectEntity("Test", employee, status);
		// when
		ProjectEntity project = projectService.addProject(projectToAdd);
		// then
		assertNotNull(project);
		assertEquals("Test", project.getNameProject());
	}

	@Test
	public void shouldDeleteProject() {
		// given
		Long id = 4L;
		// when
		projectService.removeProject(id);
		// then
		assertFalse(em.contains(projectService.findProject(id)));
		assertNull(projectService.findProject(id));
	}

	@Test
	public void shouldUpdateProject() {
		// given
		Long id = 4L;
		ProjectEntity project = em.find(ProjectEntity.class, id);
		// when
		project.setNameProject("Test");
		projectService.updateProject(project);
		// then
		assertEquals("Test", project.getNameProject());
	}

	@Test
	public void shouldCreateAndSaveNewEmployeeProject() {
		// given
		Long id = 1L;
		Long employeeId = 2L;
		Long projectId = 3L;
		double salary = 200.0;
		RoleEntity role = em.find(RoleEntity.class, id);
		EmployeeEntity employee = em.find(EmployeeEntity.class, employeeId);
		ProjectEntity project = em.find(ProjectEntity.class, projectId);
		// when
		EmployeeProjectEntity employeeProject = projectService.addEmployeeToProject(employee, project, salary, role);
		// then
		assertNotNull(employeeProject);
		assertEquals(role, employeeProject.getRole());
		assertEquals(employee, employeeProject.getEmployee());
		assertEquals(project, employeeProject.getProject());
		assertNotNull(employeeProject.getStartDate());
	}

	@Test
	public void shouldSetEndDateInEmployeeProject() throws Exception {
		// given
		Long employeeId = 24L;
		Long projectId = 10L;
		// when
		EmployeeProjectEntity employeeProject = projectService.endEmployeesWorkInProject(employeeId, projectId);
		// then
		assertNotNull(employeeProject);
		assertEquals("DEV", employeeProject.getRole().getRole());
		assertNotNull(employeeProject.getEndDate());
	}

	@Test(expected = EmptyResultException.class)
	public void shouldThrowEmptyResultException() throws Exception {
		// given
		Long employeeId = 100L;
		Long projectId = 100L;
		// when
		projectService.endEmployeesWorkInProject(employeeId, projectId);
		// then

	}

	@Test(expected = ValidationDataException.class)
	public void shouldValidationDataException() throws Exception {
		// given
		Long employeeId = null;
		Long projectId = 100L;
		// when
		projectService.endEmployeesWorkInProject(employeeId, projectId);
		// then

	}

	@Test
	public void shouldFindAllActiveEmployeesInProjects() throws Exception {
		// given
		Long projectId = 1L;
		// when
		List<EmployeeProjectEntity> employeesProjects = projectService.findActiveEmployeeInProject(projectId);
		// then
		assertFalse(employeesProjects.isEmpty());
		for (EmployeeProjectEntity e : employeesProjects) {
			assertNull(e.getEndDate());
			assertEquals(projectId, e.getProject().getId());
		}
		assertEquals(1, employeesProjects.size());
	}

	@Test(expected = EmptyResultException.class)
	public void shouldThrowExceptionWhenResultEmpty() throws Exception {
		// given
		Long projectId = 50L;
		// when
		List<EmployeeProjectEntity> employeesProjects = projectService.findActiveEmployeeInProject(projectId);
		// then
		assertTrue(employeesProjects.isEmpty());
	}

	@Test
	public void shouldFindEmployeesWhoWorkedMoreThanNMonths() {
		// given
		int months = 6;
		// when
		List<EmployeeEntity> employees = projectService.findAllEmployeesWhoWorkedLongerThanN(months);
		// then
		assertNotNull(employees);
		assertEquals(21,employees.size());
	}


	@Test
	public void shouldNotFindEmployeesWhoWorkedMoreThanMilionMonths() {
		// given
		int months = 1000000;
		// when
		List<EmployeeEntity> employees = projectService.findAllEmployeesWhoWorkedLongerThanN(months);
		// then
		assertNotNull(employees);
		assertEquals(0,employees.size());
	}
}
