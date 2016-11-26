package com.dev.smtm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.smtm.domain.StaffVO;
import com.dev.smtm.domain.UserVO;
import com.dev.smtm.dto.StaffDTO;
import com.dev.smtm.dto.UserStaffDTO;
import com.dev.smtm.service.GcmService;
import com.dev.smtm.service.PushService;
import com.dev.smtm.service.StaffService;

@Controller
@RequestMapping("/staffs/*")
public class StaffController {
	private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

	@Inject
	private StaffService service;
	@Inject
	private GcmService gcmService;
	@Inject
	private PushService pushService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody List<StaffDTO> getStaffList(@RequestParam(value = "store_id") int store_id, Model model)
			throws Exception {
		logger.info("staff/listAll called ");
		logger.info("storeid : " + store_id);
		List<StaffDTO> list = service.getStaffList(store_id);

		model.addAttribute("staffList", list);
		return list;
	}

	@RequestMapping(value = "read", method = RequestMethod.GET)
	public @ResponseBody Map<String, StaffVO> readStaff(@RequestParam(value = "staff_id") int staff_id) throws Exception {
		logger.info("readStaff Info");
		
		Map<String, StaffVO> result = new HashMap<String, StaffVO>();
		StaffVO vo = service.readStaff(staff_id);
		result.put("result", vo);
		
		logger.info(vo.toString());
		
		return result;
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public @ResponseBody Map<String, String> addNewStaff(@RequestBody StaffVO vo, Model model) throws Exception {
		logger.info("addNewStaff() called.");
		Map<String, String> result = new HashMap<String, String>();

		if (service.addStaff(vo) == 1) {
			result.put("RESULT", "SUCCESS/" + vo.getStaff_id());
			logger.info("inserted staff id : " + vo.getStaff_id());

			int staff_id = vo.getStaff_id(); // 새로 추가된 스태프의 ID
			int userId = service.readStaff(staff_id).getUser_id();
			System.out.println("추가된 아르바이트생의 userID : " + userId);
			//String deviceId = gcmService.readGcmInfo(userId).getDevice_id();
		/*	System.out.println("추가된 아르바이트의 device " + deviceId);
			pushService.SendPush(deviceId, "아르바이트생추가요청/"+staff_id);
		*/	
			return result;
		}
		result.put("RESULT", "FAIL");
		return result;
	}
	
/*	@RequestMapping(value = "readTempStaff", method = RequestMethod.GET)
	public @ResponseBody Map<String ,MessageDTO> readTempStaff(@RequestParam(value="staff_id") int staff_id) throws Exception {
		logger.info("readTempStaff called()");
		logger.info("storeID : "+staff_id);
		Map<String, MessageDTO> result = new HashMap<String, MessageDTO>();
		MessageDTO dto = service.readTempStaff(staff_id);
			
		if(dto != null){ 
			result.put("RESULT", dto);
			return result;
		} 
		result.put("RESULT", null);
		return result;
	}
*/
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> deleteStaff(@RequestParam(value = "staff_id") int staff_id)
			throws Exception {
		logger.info("delete called / " + staff_id);
		Map<String, String> result = new HashMap<String, String>();
		if (service.deleteStaff(staff_id) == -1) {
			result.put("RESULT", "FAIL");
			return result;
		}
		result.put("RESULT", "SUCCESS");
		return result;
	}


	// abc@naver.com, name : kim
	@RequestMapping(value = "searchEmail", method = RequestMethod.GET)
	public @ResponseBody Map<String, UserVO> searchEmail(@RequestParam(value = "email") String email, Model model)
			throws Exception {
		logger.info("searchEmail() called.");
		UserVO staffInfo = service.searchEmail(email);

		Map<String, UserVO> result = new HashMap<String, UserVO>();
		logger.info("searchStaff Name : " + staffInfo.getName());
		result.put("RESULT", staffInfo);
		return result;
	}

	@RequestMapping(value = "searchPhone", method = RequestMethod.GET)
	public @ResponseBody Map<String, UserVO> searchPhone(@RequestParam(value = "phone") String phone, Model model)
			throws Exception {
		logger.info("searchPhone() called.");
		UserVO staffInfo = service.searchPhone(phone);

		Map<String, UserVO> result = new HashMap<String, UserVO>();
		logger.info("searStaff Name : " + staffInfo.getName());
		result.put("RESULT", staffInfo);
		return result;
	}


	/*
	 * Staff
	 */
	@RequestMapping(value = "/info")
	public String temp() {
		return "employer/staffInfo";
	}
}
