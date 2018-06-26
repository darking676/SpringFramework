package com.bit.myapp04.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.bit.myapp04.model.entity.GuestVo;

public class GuestDaoImpl01 implements GuestDao {
	
	JdbcTemplate jdbctemplate;
	TransactionTemplate transactionTemplate;
	PlatformTransactionManager transactionManager;

	public JdbcTemplate getJdbctemplate() {
		return jdbctemplate;
	}
	
	public void setJdbctemplate(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
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
		return getJdbctemplate().query(sql, rowMapper);
	}

	@Override
	public GuestVo selectOne(int sabun) throws SQLException {
		String sql = "SELECT * FROM GUEST WHERE SABUN=?";
		return (GuestVo) jdbctemplate.query(sql, rowMapper);
	}

	// 1000 입력 -> 1000+1111 값 출력 (insert 작업 2번 일어나게 하려는 것)
	@Override
	public int insertOne(final GuestVo bean) throws SQLException {
		final String sql = "INSERT INTO GUEST VALUES (?, ?, SYSDATE, ?)";
//		return jdbctemplate.update(new PreparedStatementCreator() {
		
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(definition);
//		try {
//			jdbctemplate.update(new PreparedStatementCreator() {
//			bean.setSabun(bean.getSabun()+333);
//			jdbctemplate.update(new PreparedStatementCreator() {
//		} catch (Exception e) {
//		}
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				jdbctemplate.update(new PreparedStatementCreator() {
					
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, bean.getSabun());
						pstmt.setString(2, bean.getName());
						pstmt.setInt(3, bean.getPay());
						return pstmt;
					}
				});
				bean.setSabun(bean.getSabun()+333);
				jdbctemplate.update(new PreparedStatementCreator() {
					
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
		});
		return 0;
		
		
		
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

		// 생성자를 이용해 dataSource 주입
//		transactionManager = new DataSourceTransactionManager();
		
//		TransactionDefinition definition = new DefaultTransactionDefinition();
//		TransactionStatus status = transactionManager.getTransaction(definition);
//		transactionManager.commit(status);
//		transactionManager.rollback(status);
		
		final TransactionTemplate transactionTemplate = new TransactionTemplate();
		transactionTemplate.setTransactionManager(transactionManager);
		
		TransactionCallback<Object> action = new TransactionCallback<Object>() {
			
			@Override
			public Object doInTransaction(TransactionStatus status) {
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						jdbctemplate.update(new PreparedStatementCreator() {
							
							@Override
							public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
								PreparedStatement pstmt = conn.prepareStatement(sql); 
								pstmt.setInt(1, sabun);
								return pstmt;
							}
						});
						
					}
				});
				return 0;
			}
		};
		
//		return jdbctemplate.update(sql, new Object[] {sabun});
		int result = jdbctemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, sabun);
				return pstmt;
			}
		});
//		transactionManager.commit(status);
		return result;
	}

}
