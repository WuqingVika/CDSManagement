package com.cds.dao;

import java.util.List;

import com.cds.entity.Major;

/**
 * 专业数据操作
 * 
 * @author PengChan
 *
 */
public interface IMajorDao {
	void save(Major major);

	void update(Major major);

	void delete(Major major);

	Major find(Major major);

	List<Major> findAll();
	/**
	 * 通过专业名称来找到专业
	 * @param majorName 专业名称
	 * @return 查找到的专业对象
	 */
	Major findMajorByName_z(String majorName);
	/**
	 * 通过专业编号找到该学院的所有专业。
	 * @param collegeId 专业编号
	 * @return 专业集合
	 */
	List<Major> findMajorsByCollegeId_z(int collegeId);
	
	/**
	 * 通过专业编号找到该学院的所有专业。
	 * @param collegeId 专业编号
	 * @return 专业集合
	 */
	List findMajorsByCollegeIdJSON_z(int collegeId);
	
}
