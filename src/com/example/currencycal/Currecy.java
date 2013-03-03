package com.example.currencycal;

import java.util.Arrays;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Currecy extends Activity {

	Handler handler;

	String[] viaLocales = { "JPY", "CNY", "SDG", "RON", "MKD", "MXN", "CAD",
		    "ZAR", "AUD", "NOK", "ILS", "ISK", "SYP", "LYD", "UYU", "YER", "CSD",
		    "EEK", "THB", "IDR", "LBP", "AED", "BOB", "QAR", "BHD", "HNL", "HRK",
		    "COP", "ALL", "DKK", "MYR", "SEK", "RSD", "BGN", "DOP", "KRW", "LVL",
		    "VEF", "CZK", "TND", "KWD", "VND", "JOD", "NZD", "PAB", "CLP", "PEN",
		    "GBP", "DZD", "CHF", "RUB", "UAH", "ARS", "SAR", "EGP", "INR", "PYG",
		    "TWD", "TRY", "BAM", "OMR", "SGD", "MAD", "BYR", "NIO", "HKD", "LTL",
		    "SKK", "GTQ", "BRL", "EUR", "HUF", "IQD", "CRC", "PHP", "SVC", "PLN",
		    "USD" };
	
	public ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currecy);
		Arrays.sort(viaLocales);
		dialog =ProgressDialog.show(Currecy.this,
				null, "Loading Data");

		
		
		Spinner sp = (Spinner) (findViewById(R.id.sp_FromCurrency));
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, viaLocales);

		sp.setAdapter(aa);
		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, viaLocales);
		sp = (Spinner) (findViewById(R.id.sp_ToCurrency));
		sp.setAdapter(aa);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				
				String sb=(String)msg.obj;
				dialog.dismiss();
				TextView tv=(TextView)(findViewById(R.id.tv_Result));
				tv.setText(sb);
				
			}
		};

		Button btn = (Button) (findViewById(R.id.btnGetCurrency));

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View vv=(View)(findViewById(R.id.layout_UnitInputs));
				EditText ed = (EditText) (vv.findViewById(R.id.ed_value));
				String val = ed.getText().toString();

				float value = 0;
				try {
					value = Float.parseFloat(val);
					vv=(View)(findViewById(R.id.layout_fromCurrency));
					Spinner sps = (Spinner) (vv.findViewById(R.id.sp_FromCurrency));
					String fromCurrency = sps.getSelectedItem().toString();
					vv=(View)(findViewById(R.id.layout_toCurrency));
					sps = (Spinner) (vv.findViewById(R.id.sp_ToCurrency));
					String toCurrency = sps.getSelectedItem().toString();
					dialog.setMessage("Please wait.....");
					dialog.show();
				
					new CurrencyParser(handler,value,fromCurrency,toCurrency);
					
					

89				} catch (Exception e) {
					Toast.makeText(getBaseContext(),
							"Please enter a valid Number", Toast.LENGTH_SHORT)
							.show();
					return;
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_currecy, menu);
		return true;
	}

}
