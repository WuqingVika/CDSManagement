package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IStudentDao;
import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.Student;

/**
 * 学生信息表操作
 * 
 * @author PengChan
 *
 */
public class StudentDao implements IStudentDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Student student) {
		sessionFactory.getCurrentSession().save(student);
	}

	@Override
	public void update(Student student) {
		 sessionFactory.getCurrentSession().update(student);
	}

	@Override
	public void delete(Student student) {
		 sessionFactory.getCurrentSession().delete(student);
	}

	@Override
	public Student find(Student student) {
		return (Student) sessionFactory.getCurrentSession().get(Student.class, student.getStuId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> findAll() {
		List<Student> results = sessionFactory.getCurrentSession().createCriteria(Student.class).list();  
		return results;
	}

	@Override
	public Student findStudentByStudentId_z(String studentId) {
		return (Student) sessionFactory.getCurrentSession().createQuery("from Student where studentId=:studentId")
				.setParameter("studentId", studentId)
				.uniqueResult();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List findAllStuByClass_c(Classes classes) {
		String hql = "select s.stuId,s.studentId,s.stuName "
				+ "from classes c natural join student s "
				+ "where c.classId="+classes.getClassId();
		List result = sessionFactory.getCurrentSession().createSQLQuery(hql).list();
		return result;
	}

	@Override
	public List findNeedChStuByClass_c(Student student, Cdplan cdplan) {	
		String hql = "select s.stuId,s.studentId,s.stuName"
				+ " from classes c"
				+ " natural join student s"
				+ " where c.classId="+student.getClasses().getClassId()
				+ " and (s.stuId) not in("
				+ " select distinct s.stuId"
				+ " from student s,classes c,stugroupmembers sgm,"
				+ "studentgroup sg,cddesigntopics ct,cdteachergroup ctg,cdplan cp"
				+ " where s.classId = c.classId"
				+ " and s.stuId = sgm.stuId"
				+ " and sgm.stuGroupId = sg.stuGroupId"
				+ " and sg.cDDesignTopId = ct.cDDesignTopId"
				+ " and ct.cDTeacherGroupId = ctg.cDTeacherGroupId"
				+ " and ctg.cDPlanId = cp.cDPlanId"
				+ " and c.classId ="+student.getClasses().getClassId()
				+ " and cp.cDPlanId = "+cdplan.getCdplanId()+")"
				+ " and s.stuId <> "+student.getStuId();
		List result = sessionFactory.getCurrentSession().createSQLQuery(hql).list();
		return result;
	}

	@Override
	public Student findSudentByStudentId_c(Student student) {
		String hql = "from Student s where s.studentId="+student.getStudentId();
		Student stu = (Student) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
		return stu;
	}
}
