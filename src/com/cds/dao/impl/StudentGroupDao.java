package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IStudentgroupDao;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * 学生小组数据访问
 * @author PengChan
 *
 */
public class StudentGroupDao implements IStudentgroupDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Studentgroup studentgroup) {
		 sessionFactory.getCurrentSession().save(studentgroup);
	}

	@Override
	public void update(Studentgroup studentgroup) {
		 sessionFactory.getCurrentSession().update(studentgroup);
	}

	@Override
	public void delete(Studentgroup studentGroup) {
		 sessionFactory.getCurrentSession().delete(studentGroup);		 
	}

	@Override
	public Studentgroup find(Studentgroup studentgroup) {
		Studentgroup results = new Studentgroup();
		results = (Studentgroup) sessionFactory.getCurrentSession().get(Studentgroup.class, studentgroup.getStuGroupId());
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Studentgroup> findAll() {
		List<Studentgroup> results = sessionFactory.getCurrentSession().createCriteria(Studentgroup.class).list();
		return results;
	}
	
	
	@Override
	public void persistStudentGroup_c(Studentgroup studentgroup) {
		sessionFactory.getCurrentSession().persist(studentgroup);
	}

	@Override
	public void addStudentMembers_c(Studentgroup studentgroup) {
		int stuGroupId = studentgroup.getStuGroupId();
		String hql = "";
		for(Student student:studentgroup.getStudents()){
			hql ="insert into stugroupmembers(stuId,stuGroupId) values("+student.getStuId()+","+stuGroupId+");";
			sessionFactory.getCurrentSession().createSQLQuery(hql).executeUpdate();
		}	 				
	}

	@Override
	public List findAllMembers_c(Studentgroup studentgroup) {
		String hql = "select student.studentId,student.stuName,"
				+ " classes.className,college.collegeName,major.majorName"
				+ " from studentgroup,stugroupmembers,student,classes,major,college"
				+ " where studentgroup.stuGroupId = stugroupmembers.stuGroupId"
				+ " and stugroupmembers.stuId = student.stuId"
				+ " and student.classId = classes.classId"
				+ " and classes.majorId = major.majorId"
				+ " and major.collegeId = college.collegeId"
				+ " and studentgroup.stuGroupId ="+studentgroup.getStuGroupId();
		List result = sessionFactory.getCurrentSession().createSQLQuery(hql).list();
		return result;
	}

	@Override
	public void addStuMembers_c(Studentgroup studentgroup, Student student) {
		String hql = "insert into stugroupmembers values("+student.getStuId()+","+studentgroup.getStuGroupId()+")";
		sessionFactory.getCurrentSession().createSQLQuery(hql).executeUpdate();
	}

	@Override
	public void removeStuGroupInfo_c(Studentgroup studentgroup, Student student) {
		String hql = "delete from stugroupmembers where stugroupmembers.stuId ="
				+student.getStuId()+" and stugroupmembers.stuGroupId="+studentgroup.getStuGroupId();
		sessionFactory.getCurrentSession().createSQLQuery(hql).executeUpdate();
	}

	@Override
	public void removeAllStuGroup_c(Studentgroup studentgroup) {
		try {
			//1.删除小组成员表中的记录
			String hql = "delete from stugroupmembers where stugroupmembers.stuGroupId ="+studentgroup.getStuGroupId();
			sessionFactory.getCurrentSession().createSQLQuery(hql).executeUpdate();
			//2.删除学生小组
			delete(studentgroup);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
