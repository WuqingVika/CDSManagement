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
	//ע��DaoFactory
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
		// ����һ�����
		int flag = 0;
		// 1.���ѡ��
		try {
			// �趨Ϊ����ѡ��
			cddesigntopics.setIsSelfChoosed(1);
			daoFactory.getCddesigntopicsDao().save(cddesigntopics);
			// ��¼��һ�������ɹ�
			flag++;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2.���ѡ���¼
		// ʵ����һ��ѡ���¼
		Cdchoosingrecord cdchoosingrecord = new Cdchoosingrecord();
		try {
			cdchoosingrecord.setSubmitTime(new Date());
			cdchoosingrecord.setPassTime(null);// ����ͨ����ʱ��
			cdchoosingrecord.setIsPassed(0);// ����û��ͨ��
			cdchoosingrecord.setTutorOpinion(null);// ���õ�ʦ������
			cdchoosingrecord.setCddesigntopics(cddesigntopics);
			daoFactory.getCdchoosingrecordDao().save(cdchoosingrecord);
			// ��¼�ڶ��������ɹ�
			flag++;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3.���ѧ��С��
		try {
			// �������һ����ʦ�γ����С��
			studentgroup.setCddesigntopics(cddesigntopics);
			// ���ѧ��С����Ϣ
			daoFactory.getStudentGroupDao().save(studentgroup);
			// ���ѧ����Ա��Ϣ
			daoFactory.getStudentGroupDao().addStudentMembers_c(studentgroup);
			// ��¼�������ɹ�
			flag++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (flag == 3) ? true : false;
	}

	@Override
	public boolean selectCDTopic_c(Studentgroup studentgroup) {
		try {
			// 1.���ѧ��С��
			daoFactory.getStudentGroupDao().save(studentgroup);
			// 2.���С���Ա
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
