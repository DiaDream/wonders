package com.xyb.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class SMSUtil {
	static public String SendSMS(String mobile) throws UnsupportedEncodingException {
		int code = getRandom();
		String content =code+ "  用户注册验证码，30分钟内有效，请勿泄露【wonders】" ;
		String real_content = java.net.URLEncoder.encode(content, "utf-8");
		String httpUrl = "http://sms.kingtto.com:9999/sms.aspx";
		String httpArg = "action=send&userid=*****&account=******&password=******mobile="
				+ mobile + "&content=" + real_content;
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
		return Integer.toString(code);
	}

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 生成随机数
	 * 
	 * @return
	 */
	private static int getRandom() {
		Random random = new Random();
		int x = random.nextInt(899999);
		x = x + 100000;
		return x;
	}

	
}
