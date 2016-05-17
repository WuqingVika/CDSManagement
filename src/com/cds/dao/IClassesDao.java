package com.cds.dao;

import java.util.List;

import com.cds.entity.Classes;

public interface IClassesDao {
	void save(Classes classes);// ���

	void update(Classes classes);// �޸�

	void delete(Classes classes);// ɾ��

	Classes find(Classes classes);// ��ѯ

	List<Classes> findAll();// ��ѯ���е�����
	/**
	 * ͨ���༶�����Ʒ��ظð༶�Ķ���ֻ�ܷ���һ���༶����Ϊ������༶������ͬ
	 * @param className �༶����
	 * @return �༶��ʵ�����
	 */
	Classes findClassByClassName_z(String className);
	/**
	 * ͨ��ѧԺ��ѯ�����еİ༶
	 * @param collegeId ѧԺ�ı��
	 * @return ���еİ༶
	 */
	List<Classes> findClassByCollegeId_z(int collegeId);
}
