package com.dev.smtm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.smtm.domain.GcmVO;
import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.LoginDTO;
import com.dev.smtm.dto.UserDTO;
import com.dev.smtm.dto.UserStaffDTO;
import com.dev.smtm.service.GcmService;
import com.dev.smtm.service.PushService;
import com.dev.smtm.service.StaffService;
import com.dev.smtm.service.UserService;

@Controller
@RequestMapping("/users/*")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Inject
	private UserService service;
	@Inject
	private GcmService gcmService;
	@Inject
	private PushService pushService;
	@Inject
	private StaffService staffService;
	

	@RequestMapping(value = "regist", method = RequestMethod.GET)
	public String registUserGET() throws Exception {
		logger.info("registUser Get Called");
		return "/signup";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> registUserPOST(@RequestBody UserVO user) throws Exception {
		logger.info("create Post Called");
		logger.info("userMode : " + user.getMode());
		Map<String, String> result = new HashMap<String, String>();
		if (service.registUser(user) != 1) {
			result.put("RESULT", "FAIL");
			return result;
		}
		result.put("RESULT", "SUCCESS");
		return result;

	}

	@RequestMapping(value = "read", method = RequestMethod.GET)
	public @ResponseBody List<UserVO> listAll() throws Exception {
		logger.info("UserList Get Called");
		List<UserVO> list = service.getUserList();// model.addAttribute("list",
		return list;
	}

	@RequestMapping(value = "authentication", method = RequestMethod.POST)
	public @ResponseBody Map<String, UserDTO> login(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody LoginDTO loginDTO, HttpSession session) throws Exception {
		logger.info("Login called");
		logger.info("userAgent " + userAgent);
		logger.info("Login Id : "+loginDTO.getEmail());

		UserDTO dto = service.login(loginDTO);

		// 모바일 모드일 경우 GCM을 위한 처리
/*		if (userAgent.equals("android") && dto != null) {
			logger.info("deviceId : " + dto.getDevice_id());
			String device_id = dto.getDevice_id();
			if (gcmService.readGcmInfo(vo.getUser_id()) == null) {
				GcmVO gcmInfo = new GcmVO(device_id, vo.getUser_id());
				gcmService.registGcmInfo(gcmInfo);
			}
		}
*/
		// 결과 값 반환
		Map<String, UserDTO> result = new HashMap<String, UserDTO>();
		if (dto == null) {
			result.put("result", null);
			logger.info("Login fail");
			return result;
		}

		switch (dto.getMode()) {
		case 0: // 사장님 모드
			session.setAttribute("user_id", dto.getUser_id());
			result.put("result", dto);
			break;
		case 1: // 알바생모드
			List<UserStaffDTO> list = staffService.readStaffByUserId(dto.getUser_id());
			dto.setUserStaffs(list);
			session.setAttribute("user_id", dto.getUser_id());
			result.put("result", dto);
			break;
		}
		return result;
	}

}
