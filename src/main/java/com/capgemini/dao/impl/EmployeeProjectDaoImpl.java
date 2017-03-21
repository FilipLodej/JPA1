package com.capgemini.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.dao.EmployeeProjectDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.EmployeeProjectEntity;

@Repository
public class EmployeeProjectDaoImpl extends AbstractDao<EmployeeProjectEntity, Long> implements EmployeeProjectDao {

	@Autowired
	EntityManager em;

	@Override
	public EmployeeProjectEntity findEmployeeProjectByEmployeeIdAndProjectId(Long employeeId, Long projectId) {
		return em
				.createQuery("select e from EmployeeProjectEntity e where e.employee.id=:empId and e.project.id=:projId",
						EmployeeProjectEntity.class)
				.setParameter("empId", employeeId).setParameter("projId", projectId).getSingleResult();
	}

	@Override
	public List<EmployeeProjectEntity> findActiveEmployeeInProject(Long projectId) {
		return em.createQuery("select ep from EmployeeProjectEntity ep inner join ep.employee e inner join ep.project p where e.id=ep.employee and ep.endDate is null and p.id=:projectId", EmployeeProjectEntity.class)
				.setParameter("projectId", projectId).getResultList();
	}

	@Override
	public List<EmployeeEntity> findAllEmployeesWhoWorkedLongerThanN(int months) {
		return em.createQuery("select e from EmployeeEntity e where e.id in (select ep.employee from EmployeeProjectEntity ep where TIMESTAMPDIFF(MONTH, ep.startDate, ep.endDate) >:months)", EmployeeEntity.class)
				.setParameter("months",months).getResultList();
		
//		return em.createQuery("select e from EmployeeEntity e where e.id in (select ep.employee from EmployeeProjectEntity ep where case when ep.endDate is null then TIMESTAMPDIFF(MONTH, ep.startDate, CURRENT_TIMESTAMP) else TIMESTAMPDIFF(MONTH, ep.startDate, ep.endDate) end >:months)", EmployeeEntity.class)
//				.setParameter("months",months).getResultList();
	}

}
