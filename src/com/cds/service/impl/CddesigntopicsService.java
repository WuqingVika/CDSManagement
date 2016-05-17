package com.cds.service.impl;

import java.util.Date;
import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Cdchoosingrecord;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdplan;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Classes;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;
import com.cds.service.ICddesigntopicsService;

public class CddesigntopicsService implements ICddesigntopicsService {
	//注入DaoFactory
		private DaoFactory daoFactory;
		public void setDaoFactory(DaoFactory daoFactory) {
			this.daoFactory = daoFactory;
		}
	@Override
	public void save(Cddesigntopics cddesigntopics) {
		daoFactory.getCddesigntopicsDao().save(cddesigntopics);

	}

	@Override
	public void update(Cddesigntopics cddesigntopics) {
		daoFactory.getCddesigntopicsDao().update(cddesigntopics);

	}

	@Override
	public void delete(Cddesigntopics cddesigntopics) {
		daoFactory.getCddesigntopicsDao().delete(cddesigntopics);

	}

	@Override
	public Cddesigntopics find(Cddesigntopics cddesigntopics) {
		return daoFactory.getCddesigntopicsDao().find(cddesigntopics);
	}

	@Override
	public List<Cddesigntopics> findAll() {
		return daoFactory.getCddesigntopicsDao().findAll();
	}
	
	@Override
	public List findAllTopicByTGpId_c(Cdteachergroup cdteachergroup) {
		return daoFactory.getCddesigntopicsDao().findTopicByCdGroupId_c(cdteachergroup);
	}

	@Override
	public boolean selfSelectCDTopic_c(Cddesigntopics cddesigntopics, Studentgroup studentgroup) {
		// 定义一个标记
		int flag = 0;
		// 1.添加选题
		try {
			// 设定为自主选题
			cddesigntopics.setIsSelfChoosed(1);
			daoFactory.getCddesigntopicsDao().save(cddesigntopics);
			// 记录第一步操作成功
			flag++;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2.添加选题记录
		// 实例化一个选题记录
		Cdchoosingrecord cdchoosingrecord = new Cdchoosingrecord();
		try {
			cdchoosingrecord.setSubmitTime(new Date());
			cdchoosingrecord.setPassTime(null);// 设置通过的时间
			cdchoosingrecord.setIsPassed(0);// 设置没有通过
			cdchoosingrecord.setTutorOpinion(null);// 设置导师的评语
			cdchoosingrecord.setCddesigntopics(cddesigntopics);
			daoFactory.getCdchoosingrecordDao().save(cdchoosingrecord);
			// 记录第二步操作成功
			flag++;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3.添加学生小组
		try {
			// 添加是哪一个教师课程设计小组
			studentgroup.setCddesigntopics(cddesigntopics);
			// 添加学生小组信息
			daoFactory.getStudentGroupDao().save(studentgroup);
			// 添加学生组员信息
			daoFactory.getStudentGroupDao().addStudentMembers_c(studentgroup);
			// 记录第三步成功
			flag++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (flag == 3) ? true : false;
	}

	@Override
	public boolean selectCDTopic_c(Studentgroup studentgroup) {
		try {
			// 1.添加学生小组
			daoFactory.getStudentGroupDao().save(studentgroup);
			// 2.添加小组成员
			daoFactory.getStudentGroupDao().addStudentMembers_c(studentgroup);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List findSelfChoosedCD_c(Student student) {
		return daoFactory.getCddesigntopicsDao().findAllSelfChoosedCD_c(student);
	}

	@Override
	public List findChoosedThCD_c(Student student) {
		return daoFactory.getCddesigntopicsDao().findAllChoosedThCD_c(student);
	}

	@Override
	public List findAllChoosedCD_c(Student student) {
		return daoFactory.getCddesigntopicsDao().findAllChoosedCD_c(student);
	}

	@Override
	public List findCDByClAndCdPlan_c(Classes classes, Cdplan cdplan) {		 
		return daoFactory.getCddesigntopicsDao().findCDByClassAndCDPlan_c(classes, cdplan);
	}

	@Override
	public List findDesignTopics_c(Student student) {		 
		return daoFactory.getCddesigntopicsDao().findCdDesignTopic_c(student);
	}	 
	

}
