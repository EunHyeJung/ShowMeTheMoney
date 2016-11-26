package com.dev.smtm.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

@Service
public class PushServiceImpl implements PushService {

	private static final String API_KEY = "AIzaSyDp1dI3HXWRtjORO5CmL5NGOIVytzwjcLQ";
	private final String title ="ShowMeTheMoney";

	@Override
	public boolean SendPush(String regId, String msg) throws Exception {
		Sender sender = new Sender(API_KEY);
		try {
			final Message.Builder messageBuilder = new Message.Builder();
			messageBuilder.addData("title", title);
			messageBuilder.addData("message", msg);
			

			final com.google.android.gcm.server.Result result = sender.send(messageBuilder.build(), regId, 5);
			final String messageId = result.getMessageId();

			return (messageId != null);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
