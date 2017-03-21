package com.capgemini.dao.impl;

import org.springframework.stereotype.Repository;

import com.capgemini.dao.StatusDao;
import com.capgemini.domain.StatusEntity;

@Repository
public class StatusDaoImpl extends AbstractDao<StatusEntity, Long> implements StatusDao {

}
