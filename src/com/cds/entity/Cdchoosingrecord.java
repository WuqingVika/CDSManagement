package com.cds.entity;
// Generated 2016-4-20 18:11:49 by Hibernate Tools 4.3.1.Final

import java.util.Date;

/**
 * Cdchoosingrecord generated by hbm2java
 */
public class Cdchoosingrecord implements java.io.Serializable { 
	private static final long serialVersionUID = 1L;
	private Integer cdchoosingRecId;
	private Cddesigntopics cddesigntopics;
	private Date submitTime;
	private Date passTime;
	private String tutorOpinion;
	private Integer isPassed;

	public Cdchoosingrecord() {
	}

	public Cdchoosingrecord(Cddesigntopics cddesigntopics, Date submitTime, Date passTime, String tutorOpinion,
			Integer isPassed) {
		this.cddesigntopics = cddesigntopics;
		this.submitTime = submitTime;
		this.passTime = passTime;
		this.tutorOpinion = tutorOpinion;
		this.isPassed = isPassed;
	}

	public Integer getCdchoosingRecId() {
		return this.cdchoosingRecId;
	}

	public void setCdchoosingRecId(Integer cdchoosingRecId) {
		this.cdchoosingRecId = cdchoosingRecId;
	}

	public Cddesigntopics getCddesigntopics() {
		return this.cddesigntopics;
	}

	public void setCddesigntopics(Cddesigntopics cddesigntopics) {
		this.cddesigntopics = cddesigntopics;
	}

	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getPassTime() {
		return this.passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public String getTutorOpinion() {
		return this.tutorOpinion;
	}

	public void setTutorOpinion(String tutorOpinion) {
		this.tutorOpinion = tutorOpinion;
	}

	public Integer getIsPassed() {
		return this.isPassed;
	}

	public void setIsPassed(Integer isPassed) {
		this.isPassed = isPassed;
	}

}
