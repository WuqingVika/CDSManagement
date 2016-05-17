package com.cds.service;

import java.util.List;

import com.cds.entity.Comments;

public interface ICommentsService {
	void save(Comments comments);	//±£´æÆÀÓï
	void update(Comments comments); //¸üĞÂÆÀÓï
	void delete(Comments comments);	//É¾³ıÆÀÓï
	Comments find(Comments comments); //²éÕÒÆÀÓï
	List<Comments> findAll();		//²éÕÒËùÓĞÆÀÓï
}
