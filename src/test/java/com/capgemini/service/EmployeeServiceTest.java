package com.capgemini.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.domain.Contact;
import com.capgemini.domain.DepartmentEntity;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.EmployeeProjectEntity;
import com.capgemini.service.exception.DeleteException;
import com.capgemini.service.exception.ValidationDataException;

@ActiveProfiles(profiles = { "jpql, mysql" })
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EntityManager em;

	// TODO Zaimplementowac rzucenie wyjatku keidy jeden z wyszukiwanych
	// parametrow bedzie nullem

	@Test
	public void shouldFindEmployeeByNameAndSurname() throws ValidationDataException {
		// given
		final String name = "Jina";
		final String surname = "Barański";
		final Long id = 1L;
		// when
		List<EmployeeEntity> foundEmployees = employeeService.findByNameAndSurname(name, surname);
		// then
		assertNotNull(foundEmployees);
		assertFalse(foundEmployees.isEmpty());
		assertEquals(name, foundEmployees.get(0).getName());
		assertEquals(surname, foundEmployees.get(0).getSurname());
		assertEquals(id, foundEmployees.get(0).getDepartment().getId());
		assertEquals("HR", foundEmployees.get(0).getDepartment().getDepartmentName());

	}

	@Test(expected = ValidationDataException.class)
	public void shouldThrowValidationExceptionWhenNameOrSurnameNull() throws ValidationDataException {
		final String name = "Jina";
		final String surname = null;
		// when
		employeeService.findByNameAndSurname(name, surname);
		// then

	}

	@Test
	public void shouldFindEmployeeByDepartmentId() {
		// given
		final Long id = 2L;
		// when
		List<EmployeeEntity> foundEmployees = employeeService.findByDepartment(id);
		// then
		assertNotNull(foundEmployees);
		assertFalse(foundEmployees.isEmpty());
		assertEquals(id, foundEmployees.get(0).getDepartment().getId());
		for (EmployeeEntity e : foundEmployees) {
			assertEquals("IT", e.getDepartment().getDepartmentName());
		}
	}

	@Test
	public void shouldUpdateEmployeeDepartmentId() {
		// given
		final Long employeeId = 1L;
		final Long departmentId = 4L;
		// when
		EmployeeEntity updatedEmployee = employeeService.assignToDepartment(employeeId, departmentId);
		System.out.println(updatedEmployee.getVersion());
		em.flush();
		// em.clear();
		em.refresh(updatedEmployee);
		// then
		assertNotNull(updatedEmployee);
		assertEquals(employeeId, updatedEmployee.getId());
		assertEquals(departmentId, updatedEmployee.getDepartment().getId());
		assertEquals("Jakość", updatedEmployee.getDepartment().getDepartmentName());
		assertNotNull(updatedEmployee.getModifyDate());
		assertNotEquals(updatedEmployee.getCreateDate(), updatedEmployee.getModifyDate());

	}

	@Test
	public void shouldSaveNewEmployee() {
		// given
		DepartmentEntity department = new DepartmentEntity("Test", new Contact("aaa@aaa.pl", "12121212", "12121212"));
		EmployeeEntity employee = new EmployeeEntity("Pawian", "Man", "1234567890", LocalDate.of(2004, 12, 12),
				new Contact("aaa@aaa.pl", "12121212", "12121212"), department);
		// when
		EmployeeEntity updateEmployee = employeeService.saveEmployee(employee);

		// then
		assertNotNull(updateEmployee);
		assertEquals("1234567890", updateEmployee.getPesel());
	}

	@Test
	public void shouldUpdateEmployee() {
		// given
		String name = "Mariusz";
		Long id = 10L;
		// when
		EmployeeEntity employeeToUpdate = employeeService.findById(id);
		employeeToUpdate.setName(name);
		EmployeeEntity updatedEmployee = employeeService.updateEmployee(employeeToUpdate);
		// then
		assertNotNull(updatedEmployee);
		assertEquals("Mariusz", updatedEmployee.getName());
		assertEquals(employeeToUpdate.getId(), updatedEmployee.getId());
	}

	@Test
	public void shouldDeleteEmployee() throws DeleteException {
		// given
		Long id = 30L;

		// when
		EmployeeEntity toRemovedEmployee = employeeService.removeEmployee(id);
		List<EmployeeProjectEntity> projects = toRemovedEmployee.getEmployeeProjectEntity();
		Long projectId = projects.get(0).getId();
		EmployeeEntity employeeAfterRemove = employeeService.findById(id);
		EmployeeProjectEntity employeeProject = em.find(EmployeeProjectEntity.class, projectId);
		// then
		assertFalse(em.contains(toRemovedEmployee));
		assertNotNull(toRemovedEmployee);
		assertEquals(id, toRemovedEmployee.getId());
		assertNull(employeeAfterRemove);
		assertNull(employeeProject);

	}

	@Test(expected = DeleteException.class)
	public void shouldThrowsExceptionWhenEmployeeIsManager() throws DeleteException {
		// given
		Long id = 1L;
		// when
		employeeService.removeEmployee(id);
		// then

	}

	@Test(expected = OptimisticLockException.class)
	public void shouldCannotUpdateBecauseOptimistickLocking() {
		// given
		String name1 = "Mariusz";
		String name2 = "Grzegorz";
		Long id = 10L;
		// when
		EmployeeEntity employeeToUpdate = employeeService.findById(id);
		em.detach(employeeToUpdate);
		EmployeeEntity secondEmployeeToUpdate = employeeService.findById(id);
		secondEmployeeToUpdate.setName(name1);
		employeeService.updateEmployee(secondEmployeeToUpdate);
		em.flush();
		employeeToUpdate.setName(name2);
		em.merge(employeeToUpdate);
		employeeService.updateEmployee(employeeToUpdate);
		// then
	}

}
