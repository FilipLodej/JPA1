package com.capgemini.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "DEPARTMENT")
public class DepartmentEntity extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(nullable = false, length = 50)
	private String departmentName;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "email", column = @Column(name = "EMAIL", nullable = false) ),
			@AttributeOverride(name = "telefonHome", column = @Column(name = "TELEFON_HOME", nullable = false) ),
			@AttributeOverride(name = "telefonMobile", column = @Column(name = "TELEFON_MOBILE", nullable = false) ), })
	private Contact contact;

	protected DepartmentEntity() {

	}
	
	public DepartmentEntity(String departmentName, Contact contact) {
		this.departmentName = departmentName;
		this.contact = contact;
	}



	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
