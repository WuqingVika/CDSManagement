package com.cds.service;

import java.util.List;

import com.cds.entity.Evalstandards;

public interface IEvalstandardsService {
	void save(Evalstandards evalstandards);
	void update(Evalstandards evalstandards);
	void delete(Evalstandards evalstandards);
	Evalstandards find(Evalstandards evalstandards);
	List<Evalstandards> findAll();
}
