package com.capgemini.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.DepartmentDao;
import com.capgemini.domain.DepartmentEntity;

@Transactional
@Repository
public class DepartmentDaoImpl extends AbstractDao<DepartmentEntity, Long> implements DepartmentDao {

}
