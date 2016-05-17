package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IStudentscoreDao;
import com.cds.entity.Student;
import com.cds.entity.Studentscore;

/**
 * 学生成绩数据操作
 * 
 * @author PengChan
 *
 */
public class StudentscoreDao implements IStudentscoreDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Studentscore studentscore) {
		sessionFactory.getCurrentSession().save(studentscore);
	}

	@Override
	public void update(Studentscore studentscore) {
		sessionFactory.getCurrentSession().update(studentscore);
	}

	@Override
	public void delete(Studentscore studentscore) {
		sessionFactory.getCurrentSession().delete(studentscore);
	}

	@Override
	public Studentscore find(Studentscore studentscore) {
		Studentscore results = new Studentscore();
		results = (Studentscore) sessionFactory.getCurrentSession().get(Studentscore.class, studentscore.getId());
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Studentscore> findAll() {
		List<Studentscore> results = sessionFactory.getCurrentSession().createCriteria(Studentscore.class).list();
		return results;
	}

	@Override
	public List findStudentScore_c(Student student) {	 
		String hql = "select cddesigntopics.cDDesignTopId,cdplan.cDPlanName,cddesigntopics.topics,teacher.teacherName,"
				+ " studentscore.attendanceScore,studentscore.replyScore,studentscore.examScore,studentscore.selEvScore,"
				+ " studentscore.totalScore,studentgroup.stuGroupId"
				+ " from student,stugroupmembers,studentgroup,cddesigntopics,cdteachergroup,cdplan,teacher,studentscore"
				+ " where student.stuId = stugroupmembers.stuId"
				+ " and stugroupmembers.stuGroupId = studentgroup.stuGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and cddesigntopics.cDTeacherGroupId = cdteachergroup.cDTeacherGroupId"
				+ " and cdteachergroup.cDPlanId = cdplan.cDPlanId"
				+ " and cddesigntopics.teacherId = teacher.teacherId"
				+ " and studentscore.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and studentscore.stuId = student.stuId"
				+ " and cdplan.isCurrent = 1"
				+ " and student.stuId = "+student.getStuId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}
}
