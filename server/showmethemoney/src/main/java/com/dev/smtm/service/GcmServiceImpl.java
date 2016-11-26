package com.dev.smtm.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.dev.smtm.domain.GcmVO;
import com.dev.smtm.persistence.GcmDAO;

@Service
public class GcmServiceImpl implements GcmService {
	
	
	@Inject
	private GcmDAO dao;

	
	@Override
	public int registGcmInfo(GcmVO gcm) throws Exception {
		// TODO Auto-generated method stub
		 return dao.create(gcm);
	}

	
	@Override
	public GcmVO readGcmInfo(int user_id) throws Exception {
		// TODO Auto-generated method stub
		return dao.read(user_id);
	}

	@Override
	public int removeGcmInfo(int user_id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
