package com.cds.dao;

import java.util.List;

import com.cds.entity.Classes;

public interface IClassesDao {
	void save(Classes classes);// 添加

	void update(Classes classes);// 修改

	void delete(Classes classes);// 删除

	Classes find(Classes classes);// 查询

	List<Classes> findAll();// 查询所有的数据
	/**
	 * 通过班级的名称返回该班级的对象。只能返回一个班级，因为不允许班级名称相同
	 * @param className 班级名称
	 * @return 班级的实体对象
	 */
	Classes findClassByClassName_z(String className);
	/**
	 * 通过学院查询到所有的班级
	 * @param collegeId 学院的编号
	 * @return 所有的班级
	 */
	List<Classes> findClassByCollegeId_z(int collegeId);
}
