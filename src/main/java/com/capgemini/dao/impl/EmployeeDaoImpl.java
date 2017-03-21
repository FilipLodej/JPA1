package com.capgemini.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.capgemini.dao.EmployeeDao;

import com.capgemini.domain.EmployeeEntity;
@Profile(value = "jpql")
@Repository
public class EmployeeDaoImpl extends AbstractDao<EmployeeEntity, Long> implements EmployeeDao {

	@Autowired
	EntityManager em;
	

	@Override
	public List<EmployeeEntity> findByNameAndSurname(String name, String surname) {
		return em
				.createQuery("select e from EmployeeEntity e where e.name like :name and e.surname like :surname",
						EmployeeEntity.class)
				.setParameter("name", name).setParameter("surname", surname).getResultList();
	}

	@Override
	public List<EmployeeEntity> findByDepartment(Long departmentId) {
		return em.createQuery("select e from EmployeeEntity e where e.department.id like :departmentId",
				EmployeeEntity.class).setParameter("departmentId", departmentId).getResultList();
	}

	@Override
	public List<EmployeeEntity> findManagers(Long employeeId) {
		return em
				.createQuery("select e from EmployeeEntity e inner join e.managerRole p where p.manager=e.id and e.id=:employeeId",
						EmployeeEntity.class).setParameter("employeeId", employeeId)
				.getResultList();
	}

}
