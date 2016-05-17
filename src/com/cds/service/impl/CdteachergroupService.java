package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Teacher;
import com.cds.service.ICdteachergroupService;

public class CdteachergroupService implements ICdteachergroupService {
	//×¢ÈëDaoFactory
		private DaoFactory daoFactory;
		public void setDaoFactory(DaoFactory daoFactory) {
			this.daoFactory = daoFactory;
		}
		
	@Override
	public void save(Cdteachergroup cdteachergroup) {
		daoFactory.getCdteachergroupDao().save(cdteachergroup);

	}

	@Override
	public void update(Cdteachergroup cdteachergroup) {
		daoFactory.getCdteachergroupDao().update(cdteachergroup);

	}

	@Override
	public void delete(Cdteachergroup cdteachergroup) {
		daoFactory.getCdteachergroupDao().delete(cdteachergroup);
	}

	@Override
	public Cdteachergroup find(Cdteachergroup cdteachergroup) {
		
		return daoFactory.getCdteachergroupDao().find(cdteachergroup);
	}

	@Override
	public List<Cdteachergroup> findAll() {
		return daoFactory.getCdteachergroupDao().findAll();
	}

	@Override
	public void addTeacherMember_z(int teacherId, int cdTeacherGroupId) {
		daoFactory.getCdteachergroupDao().addTeacherMember_z(teacherId, cdTeacherGroupId);
	}

	@Override
	public void deleteMemberInfoByCdTeacherGroupId(int teacherGroupId) {
		daoFactory.getCdteachergroupDao().deleteMemberInfoByCdTeacherGroupId(teacherGroupId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List findTeacherByGP_c(Cdteachergroup cdteachergroup) {
		return daoFactory.getCdteachergroupDao().findTeacherByTG_c(cdteachergroup);
	}

	@Override
	public void addThGroupMember_c(List<Teacher> teachers, Cdteachergroup cdteachergroup) {
		try {
			for (Teacher teacher : teachers) {
				daoFactory.getCdteachergroupDao().addThGroupMember_c(teacher, cdteachergroup);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List findTeacherByTeachGroup_c(Cdteachergroup cdteachergroup) {
		return daoFactory.getCdteachergroupDao().findTeacherByTeachGroup_c(cdteachergroup);
	}

	@Override
	public void deleteTeachGroupMem_c(Cdteachergroup cdteachergroup, Teacher teacher) {
		daoFactory.getCdteachergroupDao().deleteTeachGroupMem_c(cdteachergroup, teacher);
	}

	@Override
	public void addTeacher_c(Cdteachergroup cdteachergroup, Teacher teacher) {
		daoFactory.getCdteachergroupDao().addThGroupMember_c(teacher, cdteachergroup);
	}
}
