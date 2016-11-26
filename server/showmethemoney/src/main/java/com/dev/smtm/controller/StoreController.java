package com.dev.smtm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.smtm.domain.StoreVO;
import com.dev.smtm.dto.StoreDTO;
import com.dev.smtm.service.StoreService;

@Controller
@RequestMapping("/stores/*")
public class StoreController {

	@Autowired
	StoreService storeService;

	private Logger log = Logger.getLogger(StoreController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<StoreDTO> storeListView(@RequestParam(value = "user_id") int user_id) throws Exception {
		log.info("storelist/user_id=" + user_id);
		List<StoreDTO> storeList = storeService.selectStoreName(user_id);

		for (int i = 0; i < storeList.size(); i++) {
			int cnt = storeList.get(i).getCnt_staffs();
			log.info(cnt);
		}
		return storeList;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public @ResponseBody Map<String, String> insertStore(@RequestBody StoreVO vo) throws Exception {
		log.info("insertStore / store_name " + vo.getStore_name());

		Map<String, String> result = new HashMap<String, String>();
		if (storeService.insertStore(vo) == 1) {
			result.put("RESULT", "SUCCESS/" + vo.getStore_id());
			return result;
		}
		result.put("RESULT", "FAIL");
		return result;
	}

	@RequestMapping(value = "read", method = RequestMethod.GET)
	public @ResponseBody Map<String, StoreVO> readStore(@RequestParam(value = "store_id") int store_id)
			throws Exception {
		log.info("readStaff Info");

		Map<String, StoreVO> result = new HashMap<String, StoreVO>();
		StoreVO vo = storeService.readStore(store_id);
		result.put("result", vo);

		log.info(vo.toString());

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public @ResponseBody Map<String, String> deleteStore(@RequestBody StoreVO vo) throws Exception {
		int store_id = vo.getStore_id();
		log.info("deleteStore / store_id : " + store_id);

		Map<String, String> result = new HashMap<String, String>();
		if (storeService.deleteStore(store_id) == 1) {
			result.put("RESULT", "SUCCESS");
			return result;
		}
		result.put("RESULT", "FAIL");
		return result;
	}

}
