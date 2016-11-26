package com.dev.smtm.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.LoginDTO;
import com.dev.smtm.dto.UserDTO;
import com.dev.smtm.persistence.UserDAO;

@Service
public class UserServiceImpl implements UserService {

	@Inject
	private UserDAO dao;

	@Override
	public int registUser(UserVO user) throws Exception {
		return dao.create(user);
	}

	@Override
	public UserVO readUser(int user_id) throws Exception {
		return dao.read(user_id);
	}

	@Override
	public int removeUser(int user_id) throws Exception {
		return dao.delete(user_id);
	}

	@Override
	public List<UserVO> getUserList() throws Exception {
		return dao.listAll();
	}

	@Override
	public UserDTO login(LoginDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return dao.login(dto);
	}

	@Override
	public void keepLogin(String email, String session_id, Date next) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
