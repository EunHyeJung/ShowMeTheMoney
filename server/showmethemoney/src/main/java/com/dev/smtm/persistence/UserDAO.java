package com.dev.smtm.persistence;

import java.util.List;

import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.LoginDTO;
import com.dev.smtm.dto.UserDTO;

public interface UserDAO {
	public int create(UserVO vo) throws Exception;
	
	public UserVO read(int user_id) throws Exception;
	
	public int update(UserVO vo) throws Exception;
	
	public int delete(int user_id) throws Exception;
	
	public List<UserVO> listAll() throws Exception;
	
	public UserDTO login(LoginDTO dto) throws Exception;
}
