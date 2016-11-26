package com.dev.smtm.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dev.smtm.domain.DailyVo;

@Repository
public interface DailyDao {

	public int insertDaily(DailyVo vo) throws Exception;

	public List<DailyVo> checkDailyTime(DailyVo vo) throws Exception;
	
	public int deleteDaily(DailyVo vo) throws Exception;
}
