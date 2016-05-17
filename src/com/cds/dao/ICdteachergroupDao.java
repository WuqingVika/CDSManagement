package com.cds.dao;

import java.util.List;

import com.cds.entity.Cdteachergroup;
import com.cds.entity.Teacher;

public interface ICdteachergroupDao {
	void save(Cdteachergroup cdteachergroup);// 添加

	void update(Cdteachergroup cdteachergroup);// 修改

	void delete(Cdteachergroup cdteachergroup);// 删除

	Cdteachergroup find(Cdteachergroup cdteachergroup);// 查询

	List<Cdteachergroup> findAll();// 查询所有的数据
	/**
	 * 通过教师编号和教师课程计划编号添加教师组的组员
	 * @param teacherId 教师编号
	 * @param teacherGroupId 教师课程计划编号
	 */
	void addTeacherMember_z(int teacherId, int cdTeacherGroupId);
	/**
	 * 通过teacherGroupId来删除所有的教师成员信息
	 * @param teacherGroupId
	 */
	void deleteMemberInfoByCdTeacherGroupId(int teacherGroupId);
	
	/**
	 * 通过组员查询所有的教师信息
	 * @param cdteachergroup
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findTeacherByTG_c(Cdteachergroup cdteachergroup);
	
	/**
	 * 向教师小组中添加组员
	 * @param teacher 教师
	 * @param cdteachergroup 课程设计小组
	 */
	void addThGroupMember_c(Teacher teacher,Cdteachergroup cdteachergroup);
	
	
	/**
	 * 查询所有的除了组长的成员信息
	 * @param cdteachergroup
	 * @return
	 */
	List findTeacherByTeachGroup_c(Cdteachergroup cdteachergroup);
	
	
	/**
	 * 删除小组成员
	 * @param cdteachergroup 教师小组
	 * @param teacher 教师
	 */
	void deleteTeachGroupMem_c(Cdteachergroup cdteachergroup,Teacher teacher);
}
