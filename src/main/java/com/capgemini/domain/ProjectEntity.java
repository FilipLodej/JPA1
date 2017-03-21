package com.capgemini.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT")
public class ProjectEntity extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 20)
	private String nameProject;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	private EmployeeEntity manager;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private StatusEntity status;

	public ProjectEntity() {

	}

	public ProjectEntity(String nameProject, EmployeeEntity manager, StatusEntity status) {
		this.nameProject = nameProject;
		this.manager = manager;
		this.status = status;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

}
