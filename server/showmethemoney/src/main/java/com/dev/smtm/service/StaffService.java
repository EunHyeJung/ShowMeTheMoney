package com.dev.smtm.service;

import java.util.List;

import com.dev.smtm.domain.StaffVO;
import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.MessageDTO;
import com.dev.smtm.dto.StaffDTO;
import com.dev.smtm.dto.UserStaffDTO;

public interface StaffService {
	public int addStaff(StaffVO vo) throws Exception;
	public List<StaffDTO> getStaffList(int store_id) throws Exception;
	
	public StaffVO readStaff(int staff_id) throws Exception;
	public int updateStaff(int staff_id) throws Exception;
	public int deleteStaff(int staff_id) throws Exception;
	public UserVO searchEmail(String email) throws Exception;
	public UserVO searchPhone(String phone) throws Exception;
	public List<UserStaffDTO> readStaffByUserId(int user_id) throws Exception;
	public List<MessageDTO> readTempStaff(int user_id) throws Exception;
}
