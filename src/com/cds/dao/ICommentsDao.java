package com.cds.dao;

import java.util.List;

import com.cds.entity.Comments;

/**
 * 评语表数据操作
 * 
 * @author PengChan
 *
 */
public interface ICommentsDao {
	void save(Comments comments); // 保存评语

	void update(Comments comments); // 更新评语

	void delete(Comments comments); // 删除评语

	Comments find(Comments comments); // 查找评语

	List<Comments> findAll(); // 查找所有评语
}
