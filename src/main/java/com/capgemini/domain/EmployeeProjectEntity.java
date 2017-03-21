package com.capgemini.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE_PROJECT")
public class EmployeeProjectEntity extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate startDate;

	@Column(columnDefinition = "DATE")
	private LocalDate endDate;

	@Column(nullable = false)
	private Double salary;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private RoleEntity role;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ProjectEntity project;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private EmployeeEntity employee;

	public EmployeeProjectEntity() {
	}
	
	

	public EmployeeProjectEntity(Double salary, RoleEntity role, ProjectEntity project, EmployeeEntity employee) {
		this.salary = salary;
		this.role = role;
		this.project = project;
		this.employee = employee;
		this.startDate= LocalDate.now();
	}



	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public EmployeeEntity getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}

}
