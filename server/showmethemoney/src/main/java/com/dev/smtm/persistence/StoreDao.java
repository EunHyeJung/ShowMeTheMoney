package com.dev.smtm.persistence;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dev.smtm.domain.StoreVO;
import com.dev.smtm.dto.StoreDTO;

@Repository
public interface StoreDao {
	
	public List<StoreDTO> selectStoreName(StoreVO vo) throws Exception;
	
	public int insertStore(StoreVO vo) throws Exception;
	
	public StoreVO read(int store_id) throws Exception;
	
	public int deleteStore(StoreVO vo) throws Exception;
}
