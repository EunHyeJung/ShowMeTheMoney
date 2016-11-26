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

import com.dev.smtm.domain.DailyVo;
import com.dev.smtm.service.DailyService;

@Controller
public class DailyController {

	@Autowired
	DailyService dailyService;

	private Logger log = Logger.getLogger(DailyController.class);

	@RequestMapping(value = "/insertDaily", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public @ResponseBody Map<String, String> insertDaily(@RequestBody DailyVo vo) throws Exception {
		log.info("insertDaily");
		log.info(vo.toString());
		Map<String, String> result = new HashMap<String, String>();
		if(dailyService.insertDaily(vo) !=1){
			result.put("RESULT", "FAIL");
			return result;
		}
		result.put("RESULT", "SUCCESS");
		return result;
	}

	@RequestMapping(value = "/checkdailytimelist", method = RequestMethod.GET)
	public @ResponseBody List<DailyVo> storeListView(@RequestParam("staff_id") int staff_id,
			@RequestParam("start_time") String start_time) throws Exception {
		log.info("checkdailytimelist?staff_id=" + staff_id + "&start_time=" + start_time);
		List<DailyVo> result = dailyService.checkDailyTime(staff_id, start_time);
		log.info("dailyVos Size : " + result.size());
		return result;
	}

	@RequestMapping(value = "/deleteDaily", method = RequestMethod.GET)
	public @ResponseBody int deleteStore(@RequestParam("daily_seq") int daily_seq,
			@RequestParam("staff_id") int staff_id) throws Exception {

		return dailyService.deleteDaily(daily_seq, staff_id);
	}

}
