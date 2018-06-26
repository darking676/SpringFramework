package com.bit.myapp04.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.bit.myapp04.model.entity.GuestVo;

public class GuestDaoImpl01 implements GuestDao {
	
	JdbcTemplate jdbctemplate;
	
	public void setJdbctemplate(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}
	
	RowMapper<GuestVo> rowMapper = new RowMapper<GuestVo>() {
		
		@Override
		public GuestVo mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GuestVo(
					rs.getInt("sabun"),
					rs.getString("name"),
					rs.getDate("nalja"),
					rs.getInt("pay")
					);
		}
	};

	@Override
	public List<GuestVo> selectAll() throws SQLException {
		String sql = "SELECT * FROM GUEST ORDER BY SABUN";
		return jdbctemplate.query(sql, rowMapper);
	}

	@Override
	public GuestVo selectOne(int sabun) throws SQLException {
		String sql = "SELECT * FROM GUEST WHERE SABUN=?";
		return (GuestVo) jdbctemplate.query(sql, rowMapper);
	}

	@Override
	public int insertOne(final GuestVo bean) throws SQLException {
		final String sql = "INSERT INTO GUEST VALUES (?, ?, SYSDATE, ?)";
		return jdbctemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bean.getSabun());
				pstmt.setString(2, bean.getName());
				pstmt.setInt(3, bean.getPay());
				return pstmt;
			}
		});
	}

	@Override
	public int updateOne(final GuestVo bean) throws SQLException {
		final String sql = "UPDATE GUEST SET NAME=?, PAY=? WHERE SABUN=?";
		return jdbctemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bean.getName());
				pstmt.setInt(2, bean.getPay());
				pstmt.setInt(3, bean.getSabun());
				return pstmt;
			}
		});
	}

	@Override
	public int deleteOne(final int sabun) throws SQLException {
		final String sql = "DELETE FROM GUEST WHERE SABUN=?";
		return jdbctemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, sabun);
				return pstmt;
			}
		});
	}

}
