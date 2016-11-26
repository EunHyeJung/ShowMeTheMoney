package com.dev.smtm.service;

import com.dev.smtm.domain.GcmVO;
import com.dev.smtm.domain.UserVO;

public interface GcmService {
	public int registGcmInfo(GcmVO gcm) throws Exception;
	public GcmVO  readGcmInfo(int user_id) throws Exception;
	public int removeGcmInfo(int user_id) throws Exception;

}
