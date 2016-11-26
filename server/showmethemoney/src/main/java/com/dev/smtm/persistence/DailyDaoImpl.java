package com.dev.smtm.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dev.smtm.domain.DailyVo;

@Repository
public class DailyDaoImpl implements DailyDao{
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public int insertDaily(DailyVo vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("daily.insertDaily",vo);
	}
	
	@Override
	public List<DailyVo> checkDailyTime(DailyVo vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("daily.checkDailyTime",vo);
	}

	@Override
	public int deleteDaily(DailyVo vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.delete("daily.deleteDailyInfo", vo);
	}
}
