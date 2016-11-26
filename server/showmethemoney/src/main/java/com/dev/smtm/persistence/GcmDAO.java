package com.dev.smtm.persistence;

import com.dev.smtm.domain.GcmVO;

public interface GcmDAO {
	public int create(GcmVO vo) throws Exception;
	
	public GcmVO read(int user_id) throws Exception;
	
	public int update(GcmVO vo) throws Exception;

	public int delete(int user_id, int device_id) throws Exception;
}
