package com.cds.dao;

import java.util.List;

import com.cds.entity.Major;

/**
 * רҵ���ݲ���
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
	 * ͨ��רҵ�������ҵ�רҵ
	 * @param majorName רҵ����
	 * @return ���ҵ���רҵ����
	 */
	Major findMajorByName_z(String majorName);
	/**
	 * ͨ��רҵ����ҵ���ѧԺ������רҵ��
	 * @param collegeId רҵ���
	 * @return רҵ����
	 */
	List<Major> findMajorsByCollegeId_z(int collegeId);
	
	/**
	 * ͨ��רҵ����ҵ���ѧԺ������רҵ��
	 * @param collegeId רҵ���
	 * @return רҵ����
	 */
	List findMajorsByCollegeIdJSON_z(int collegeId);
	
}
