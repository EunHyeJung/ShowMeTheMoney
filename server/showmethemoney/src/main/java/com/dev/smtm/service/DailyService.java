package com.dev.smtm.service;

import java.util.List;

import com.dev.smtm.domain.DailyVo;

public interface DailyService {
	
	/**
	 * Insert daily
	 * @param DailyVo
	 * @return int
	 * @throws Exception
	 */
	public int insertDaily(DailyVo vo) throws Exception;
	
	/**
	 * select daily
	 * @param store_id
	 * @return int
	 * @throws Exception
	 */
	public List<DailyVo> checkDailyTime(int staff_id, String start_time) throws Exception;
	
	/**
	 * delete daily
	 * @param daily_seq, staff_id
	 * @return int
	 * @throws Exception
	 */
	public int deleteDaily(int daily_seq, int staff_id) throws Exception;
}
