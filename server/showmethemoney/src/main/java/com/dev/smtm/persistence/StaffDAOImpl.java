package com.dev.smtm.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dev.smtm.domain.StaffVO;
import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.MessageDTO;
import com.dev.smtm.dto.StaffDTO;
import com.dev.smtm.dto.UserStaffDTO;

@Repository
public class StaffDAOImpl implements StaffDAO {
	private static String namespace = "com.dev.smtm.mapper.StaffMapper";

	@Inject
	SqlSession session;

	@Override
	public int create(StaffVO vo) throws Exception {
		return session.insert(namespace + ".insert", vo);

	}

	@Override
	public List<StaffDTO> listAll(int store_id) throws Exception {
		return session.selectList(namespace + ".listAll", store_id);
	}

	@Override
	public int delete(int staff_id) throws Exception {
		return session.delete(namespace + ".delete", staff_id);
	}

	@Override
	public UserVO searchEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".searchEmail", email);
	}

	@Override
	public UserVO searchPhone(String phone) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".searchPhone", phone);
	}

	@Override
	public StaffVO read(int staff_id) throws Exception {
		return session.selectOne(namespace+".read", staff_id);
	}

	@Override
	public List<MessageDTO> readTempStaff(int user_id) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".readMessageList", user_id);
	}

	@Override
	public int update(int staff_id) throws Exception {
		return session.update(namespace+".update", staff_id);
	}

	@Override
	public List<UserStaffDTO> readStaffByUserId(int user_id) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".readStaffByUserId", user_id);
	}

	/*
	 * @Override public List<StaffTemp> listAll(int store_id) throws Exception {
	 * // TODO Auto-generated method stub return
	 * session.selectList(namespace+"getStaffNameList", store_id); }
	 */

}
