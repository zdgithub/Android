package com.example.lenovo.mytime.utils;

//发送http请求的工具类


import com.example.lenovo.mytime.bean.ChatMessage;
import com.example.lenovo.mytime.bean.Result;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;


public class HttpUtils
{
	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API_KEY = "b345de2d20e841c2b1e18b7e2e3a007f";

	/**
	 * 发送一个消息，得到返回的消息
	 */
	public static ChatMessage sendMessage(String msg)
	{
		ChatMessage chatMessage = new ChatMessage();
		String jsonRes = doGet(msg);
		Gson gson = new Gson();
		Result result = null;
		try
		{
			result = gson.fromJson(jsonRes, Result.class);
			chatMessage.setMsg(result.getText());
		} catch (Exception e)
		{
			chatMessage.setMsg("服务器繁忙，请稍候再试");
		}
		chatMessage.setDate(new Date());
		chatMessage.setType(ChatMessage.Type.INCOMING);
		return chatMessage;
	}

    //由用户传入一条消息，拼接成一个完整的url,得到一个返回结果
	public static String doGet(String msg)
	{
		String result = "";
		String url = setParams(msg);

		ByteArrayOutputStream baos = null;
		InputStream is = null;
		try
		{
			java.net.URL urlNet = new java.net.URL(url); //通过net.url打开一个连接
			HttpURLConnection conn = (HttpURLConnection) urlNet
					.openConnection();

			//设置connect参数
			conn.setReadTimeout(5 * 1000);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");

			//通过conn拿到服务器返回的流
			is = conn.getInputStream();

			int len = -1;
			byte[] buf = new byte[128]; //声明一个缓冲区128字节
			baos = new ByteArrayOutputStream(); //将流转换成string
          //对流进行读操作，保存到本地，并将流转换成字符串，返回字符串
			while ((len = is.read(buf)) != -1)
			{
				baos.write(buf, 0, len);
			}
			baos.flush();
			result = new String(baos.toByteArray()); //用字符串进行接收
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (baos != null)
					baos.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			try
			{
				if (is != null)
				{
					is.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	//拼接一个完整的url
	private static String setParams(String msg)
	{
		String url = "";
		try
		{
			url = URL + "?key=" + API_KEY + "&info="
					+ URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return url;
	}

}
