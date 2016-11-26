package com.dev.smtm.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.smtm.service.StoreService;

@Controller
@RequestMapping("/setting/*")
public class SettingController {
	@Autowired
	StoreService storeService;

	private Logger logger = Logger.getLogger(SettingController.class);
}
