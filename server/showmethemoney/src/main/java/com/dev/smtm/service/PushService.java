package com.dev.smtm.service;

public interface PushService {
	boolean SendPush(String regId, String msg) throws Exception;
}
