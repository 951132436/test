package com.itheima.bos.web.action;


import java.io.IOException;
import java.util.Random;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.struts2.ServletActionContext;

public class SendMsg_webchinese {
	
	public static void sendMessage() throws Exception{
		String random = getRandom();   
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("Uid", "yyxing"),new NameValuePair("Key", "83c655ff3652b10b388a"),new NameValuePair("smsMob","15910542069"),new NameValuePair("smsText",random)};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers)
		{
		System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
		System.out.println(result);


		post.releaseConnection();
		//将随机数存到session 中 没问题
		
		ServletActionContext.getRequest().getSession().setAttribute("mobileRandom", random);
	}
	//来一个随机数的方法 六位的随机数
	public static String getRandom(){
		Random random = new Random();
		String str="";
		for(int i=0;i<6;i++){
			str+=random.nextInt(10);
		}
		return str;
	}
	
}

