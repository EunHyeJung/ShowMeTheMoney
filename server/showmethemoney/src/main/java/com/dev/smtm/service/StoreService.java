package com.dev.smtm.service;

import java.util.List;

import com.dev.smtm.domain.StoreVO;
import com.dev.smtm.dto.StoreDTO;

public interface StoreService {
	
	/**
	 * Select Store Name
	 * @param user_id
	 * @return List<StoreDTO>
	 * @throws Exception
	 */
	public List<StoreDTO> selectStoreName(int user_id) throws Exception;

	/**
	 * Insert Store Vo
	 * @param StoreVO
	 * @return int
	 * @throws Exception
	 */
	public int insertStore(StoreVO vo) throws Exception;
	
	/**
	 * Delete Store Vo
	 * @param store_id
	 * @return int
	 * @throws Exception
	 */
	public int deleteStore(int store_id) throws Exception;
	
	public StoreVO readStore(int store_id) throws Exception;
	
}
