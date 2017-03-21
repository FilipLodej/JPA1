package com.capgemini.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 50)
	private String surname;

	@Column(length = 11)
	private String pesel;

	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate birthdayDate;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "email", column = @Column(name = "EMAIL", nullable = false) ),
			@AttributeOverride(name = "telefonHome", column = @Column(name = "TELEFON_HOME", nullable = false) ),
			@AttributeOverride(name = "telefonMobile", column = @Column(name = "TELEFON_MOBILE", nullable = false) ), })
	private Contact contact;

	@ManyToOne(cascade=CascadeType.PERSIST, optional = false)
	private DepartmentEntity department;

	@OneToOne(cascade = CascadeType.ALL, optional = true, mappedBy="manager")
	private ProjectEntity managerRole;
	
	@OneToMany(mappedBy="employee",cascade=CascadeType.REMOVE, orphanRemoval=true)
	private List<EmployeeProjectEntity> employeeProjectEntity;

	public EmployeeEntity() {

	}

	public EmployeeEntity(String name, String surname, String pesel, LocalDate birthdayDate, Contact contact,
			DepartmentEntity department) {
		this.name = name;
		this.surname = surname;
		this.pesel = pesel;
		this.birthdayDate = birthdayDate;
		this.contact = contact;
		this.department = department;
	}

	public ProjectEntity getManagerRole() {
		return managerRole;
	}

	public void setManagerRole(ProjectEntity managerRole) {
		this.managerRole = managerRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public LocalDate getBirthdayDate() {
		return birthdayDate;
	}

	public void setBirthdayDate(LocalDate birthdayDate) {
		this.birthdayDate = birthdayDate;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	public List<EmployeeProjectEntity> getEmployeeProjectEntity() {
		return employeeProjectEntity;
	}

	public void setEmployeeProjectEntity(List<EmployeeProjectEntity> employeeProjectEntity) {
		this.employeeProjectEntity = employeeProjectEntity;
	}



}
