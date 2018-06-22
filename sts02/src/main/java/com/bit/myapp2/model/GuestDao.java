package com.bit.myapp2.model;

import java.sql.SQLException;
import java.util.List;

public interface GuestDao<GuestVo> {

	List<GuestVo> selectAll() throws SQLException;
	
	GuestVo selectOne(int pk) throws SQLException;
	
	int insertOne(GuestVo t) throws SQLException;
	
	int updateOne(GuestVo t) throws SQLException;
	
	int deleteOne(int pk) throws SQLException;
	
}
