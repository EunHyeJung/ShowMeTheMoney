package com.dev.smtm.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dev.smtm.domain.StoreVO;
import com.dev.smtm.dto.StoreDTO;

@Repository
public class StoreDaoImpl implements StoreDao{
	

	@Inject
	SqlSession sqlSession;

	@Override
	public List<StoreDTO> selectStoreName(StoreVO vo) throws Exception {
		return sqlSession.selectList("store.getStoreName", vo);
	}
	
	@Override
	public int insertStore(StoreVO vo) throws Exception {
		return sqlSession.insert("store.insertStore",vo);
	}
	
	@Override
	public int deleteStore(StoreVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.delete("store.deleteStore", vo);
	}

	@Override
	public StoreVO read(int store_id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("store.read", store_id);
	}

	
}
