package com.dev.smtm.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.dev.smtm.domain.StaffVO;
import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.MessageDTO;
import com.dev.smtm.dto.StaffDTO;
import com.dev.smtm.dto.UserStaffDTO;
import com.dev.smtm.persistence.StaffDAO;


@Service
public class StaffServiceImpl implements StaffService {
	@Inject
	private StaffDAO dao;

	@Override
	public int addStaff(StaffVO vo) throws Exception {
		return dao.create(vo);
		
	}

	@Override
	public List<StaffDTO> getStaffList(int store_id) throws Exception {
		// TODO Auto-generated method stub
		return dao.listAll(store_id);
	}

	@Override
	public int deleteStaff(int staff_id) throws Exception {
		return dao.delete(staff_id);
	}

	@Override
	public UserVO searchEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchEmail(email);
	}

	@Override
	public UserVO searchPhone(String phone) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchPhone(phone);
	}

	@Override
	public StaffVO readStaff(int staff_id) throws Exception {
		// TODO Auto-generated method stub
		return dao.read(staff_id);
	}

	@Override
	public List<MessageDTO> readTempStaff(int user_id) throws Exception {
		// TODO Auto-generated method stub
		return dao.readTempStaff(user_id);
	}

	@Override
	public int updateStaff(int staff_id) throws Exception {
		// TODO Auto-generated method stub
		return dao.update(staff_id);
	}

	@Override
	public List<UserStaffDTO> readStaffByUserId(int user_id) throws Exception {
		// TODO Auto-generated method stub
		return dao.readStaffByUserId(user_id);
	}

	

}
