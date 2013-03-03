package com.example.currencycal;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

public class CurrencyParser extends Thread {

	Handler hh;
	float value;
	String fromCurrency,toCurrency;
	CurrencyParser(Handler hh,float value,String fromCurrency,String toCurrency)
	{
		this.hh=hh;
		this.value=value;
		this.fromCurrency=fromCurrency;
		this.toCurrency=toCurrency;
		this.start();
	}
	public void run()
	{
		try
		{
		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
		String parsURL="http://www.google.com/ig/calculator?hl=en&q="+value+""+fromCurrency+"=?"+toCurrency+"";
		//HttpGet httpget = new HttpGet("http://rate-exchange.appspot.com//currency?from="+fromCurrency+"&to="+toCurrency+"&q="+value+"");
		HttpGet httpget = new HttpGet(parsURL);
		HttpResponse  response = httpclient.execute(httpget);
		String result = EntityUtils.toString(response.getEntity());
	//	JSONArray ja = new JSONArray(result);
		JSONObject temp = new JSONObject(result);
		//int n = ja.length();
		Message m = new Message();
		m.obj=temp.get("rhs");
		hh.sendMessage(m);
		}
		catch(Exception e)
		{
			Message m = new Message();
			m.obj=e.toString()+"";
			hh.sendMessage(m);
		}

		
		
	}
}
