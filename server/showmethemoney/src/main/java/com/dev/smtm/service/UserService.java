package com.dev.smtm.service;

import java.util.Date;
import java.util.List;

import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.LoginDTO;
import com.dev.smtm.dto.UserDTO;

public interface UserService {
	public int registUser(UserVO user) throws Exception;
	public UserVO readUser(int user_id) throws Exception;
	public int removeUser(int user_id) throws Exception;
	public List<UserVO> getUserList() throws Exception;
	public UserDTO login(LoginDTO dto) throws Exception;
	
	public void keepLogin(String email, String session_id, Date next) throws Exception; 
}
