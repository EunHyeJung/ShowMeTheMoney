package com.dev.smtm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.smtm.domain.StoreVO;
import com.dev.smtm.dto.StoreDTO;
import com.dev.smtm.persistence.StoreDao;

@Service
public class StoreServiceImpl implements StoreService{

	@Autowired
	StoreDao storeDao;
	
	@Override
	public List<StoreDTO> selectStoreName(int user_id) throws Exception {
		StoreVO vo = new StoreVO();
		vo.setUser_id(user_id);
		return storeDao.selectStoreName(vo);
	}
	
	@Override
	public int insertStore(StoreVO vo) throws Exception {
		int cnt = 0;
		try {
			cnt = storeDao.insertStore(vo);
		} catch (Exception e) {
			// TODO: handle exception
			return cnt;
		}
		return cnt;
	}
	
	@Override
	public int deleteStore(int store_id) throws Exception {
		// TODO Auto-generated method stub
		StoreVO vo = new StoreVO();
		vo.setStore_id(store_id);
		return storeDao.deleteStore(vo);
	}

	@Override
	public StoreVO readStore(int store_id) throws Exception {
		// TODO Auto-generated method stub
		return storeDao.read(store_id);
	}

}
