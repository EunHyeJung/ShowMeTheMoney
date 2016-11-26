package com.dev.smtm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.smtm.domain.DailyVo;
import com.dev.smtm.persistence.DailyDao;

@Service
public class DailyServiceImpl implements DailyService{
	@Autowired
	DailyDao dailyDao;
	
	@Override
	public int insertDaily(DailyVo vo) throws Exception {
		// TODO Auto-generated method stub
		int cnt = 0;
		try {
			cnt = dailyDao.insertDaily(vo);
		} catch (Exception e) {
			// TODO: handle exception
			return cnt;
		}
		return cnt;
	}
	
	@Override
	public List<DailyVo> checkDailyTime(int staff_id, String start_time)
			throws Exception {
		DailyVo vo = new DailyVo();
		vo.setStaff_id(staff_id);
		vo.setStart_time(start_time);
		// TODO Auto-generated method stub
		return dailyDao.checkDailyTime(vo);
	}
	
	@Override
	public int deleteDaily(int daily_seq, int staff_id) throws Exception {
		// TODO Auto-generated method stub
		DailyVo vo =  new DailyVo();
		vo.setDaily_seq(daily_seq);
		vo.setStaff_id(staff_id);
		return dailyDao.deleteDaily(vo);
	}
}
