package com.dev.smtm.persistence;

import java.util.List;

import com.dev.smtm.domain.StaffVO;
import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.MessageDTO;
import com.dev.smtm.dto.StaffDTO;
import com.dev.smtm.dto.UserStaffDTO;

public interface StaffDAO {
	public int create(StaffVO vo) throws Exception;

	public List<StaffDTO> listAll(int store_id) throws Exception;
	
	public StaffVO read(int staff_id) throws Exception;
	
	public int update(int staff_id) throws Exception;

	public int delete(int staff_id) throws Exception;

	public UserVO searchEmail(String email) throws Exception;

	public UserVO searchPhone(String phone) throws Exception;
	public List<UserStaffDTO> readStaffByUserId(int user_id) throws Exception;
	public List<MessageDTO> readTempStaff(int user_id) throws Exception;

}
