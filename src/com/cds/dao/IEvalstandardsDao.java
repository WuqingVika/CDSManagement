package com.cds.dao;

import java.util.List;

import com.cds.entity.Evalstandards;

/**
 * ���������ݲ���
 * 
 * @author PengChan
 *
 */
public interface IEvalstandardsDao {
	void save(Evalstandards evalstandards);

	void update(Evalstandards evalstandards);

	void delete(Evalstandards evalstandards);

	Evalstandards find(Evalstandards evalstandards);

	List<Evalstandards> findAll();
}
