package com.dev.smtm.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.LoginDTO;
import com.dev.smtm.dto.UserDTO;

@Repository
public class UserDAOImpl implements UserDAO {

	@Inject
	private SqlSession session;

	private static String namespace = "com.dev.smtm.mapper.UserMapper";

	@Override
	public int create(UserVO vo) throws Exception {
		return session.insert(namespace + ".insert", vo);
	}

	@Override
	public UserVO read(int user_id) throws Exception {
		return session.selectOne(namespace + ".read", user_id);
	}

	@Override
	public int update(UserVO vo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int user_id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserVO> listAll() throws Exception {
		return session.selectList(namespace + ".listAll");
	}

	@Override
	public UserDTO login(LoginDTO dto) throws Exception {
		// TODO Auto-generated method stub
		
		UserDTO userDTO = session.selectOne(namespace+".login", dto);

		return userDTO;
	}

}
