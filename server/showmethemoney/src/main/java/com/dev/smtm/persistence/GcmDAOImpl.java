package com.dev.smtm.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dev.smtm.domain.GcmVO;

@Repository
public class GcmDAOImpl implements GcmDAO {
	
	
	@Inject
	private SqlSession session;

	private static String namespace = "com.dev.smtm.mapper.GcmMapper";

	@Override
	public int create(GcmVO vo) throws Exception {
		return session.insert(namespace + ".insert", vo);
	}

	@Override
	public GcmVO read(int user_id) throws Exception {
		return session.selectOne(namespace + ".read", user_id);
	}

	@Override
	public int update(GcmVO vo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int delete(int user_id, int device_id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



}
