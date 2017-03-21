package com.capgemini.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "ROLE")
public class RoleEntity extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length = 20)
	private String role;

	public RoleEntity() {
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
