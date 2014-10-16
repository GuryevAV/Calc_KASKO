package ru.guryev_av.calckasko;

import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Raschet extends Activity {
	
	DB db;
	Cursor cursorMarka, cursorModel;
	SimpleCursorAdapter scAdapterMarka, scAdapterModel;
	TextView tv, tvDataPriobr, tvStoimOt, tvStoimDo;
	Button bOptimiz;
	Intent intent;
	EditText etGodIzgotovl, etStoimost;
	Spinner spinModel;
	
	private static final int DIALOG_DATE_PRIOBR = 1;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bazovjy_raschet);
		
		//подключаемся к БД
      	db = new DB(this);
      	db.open();
		
        // Получаем спиннер:
	    Spinner spinMarka = (Spinner) findViewById(R.id.spinMarka);
	    spinModel = (Spinner) findViewById(R.id.spinModel);
	    
	    // получаем курсор
	    cursorMarka = db.getAllMarka();
	    startManagingCursor(cursorMarka);
	     
	    // формируем столбцы сопоставления
	    String[] fromMarka = new String[] { DB.TS_MARKA_NAME };
	    String[] fromModel = new String[] { DB.TS_MODEL_NAME };
	    int[] to = new int[] { android.R.id.text1 };
	
	    // создааем адаптер и настраиваем список
	    scAdapterMarka = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursorMarka, fromMarka, to);
	    scAdapterMarka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinMarka.setAdapter(scAdapterMarka);   
	    scAdapterModel = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, null, fromModel, to);
	    scAdapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinModel.setAdapter(scAdapterModel);   

	    spinMarka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	    	public void onItemSelected(AdapterView<?> parent,
	    				View itemSelected, int selectedItemPosition, long selectedId) {
	    			tv = (TextView)itemSelected;
	    			//Toast.makeText(getApplicationContext(), "" + tv.getText(),
	    			//		Toast.LENGTH_LONG).show();
	    			cursorModel = db.getModel("" + tv.getText());
	    		    startManagingCursor(cursorMarka);
	    		    scAdapterModel.changeCursor(cursorModel);
	    		}
	    		public void onNothingSelected(AdapterView<?> parent) {
	    	}
	    });
	     
	    tvDataPriobr = (TextView) findViewById(R.id.tvDataPriobr);
	    tvDataPriobr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DIALOG_DATE_PRIOBR);
			}
		});
	    
	    etGodIzgotovl = (EditText) findViewById(R.id.etGodIzgotovl);
	    etGodIzgotovl.addTextChangedListener(watcher);
	    
	    tvStoimOt = (TextView) findViewById(R.id.tvStoimOt);
	    tvStoimDo = (TextView) findViewById(R.id.tvStoimDo);
	    
	    etStoimost = (EditText) findViewById(R.id.etStoimost);
	}
	
	TextWatcher watcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable edt) {
			// TODO Auto-generated method stub
			if (cursorModel.moveToFirst()) { 
				while (!cursorModel.isAfterLast()) { 
					if (cursorModel.getLong(cursorModel.getColumnIndex("_id")) == spinModel.getSelectedItemId()) { 
						String model_id = cursorModel.getString(cursorModel.getColumnIndex(db.TS_MODEL_ID));
		                //showToast(model_id + "-" + edt.toString());
		                String[] price = {"", "", "" };
		                price = db.getPrice(model_id + "-" + edt.toString());
		                tvStoimOt.setText("от: " + price[0]);
		                tvStoimDo.setText("до: " + price[1]);
		                etStoimost.setText(price[2]);
		                break; 
		            } 
		            cursorModel.moveToNext(); 
		        } 
		    } 
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
	
	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
	      if (id == DIALOG_DATE_PRIOBR) {
	        DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, MainActivity.currentYear, 
	        		MainActivity.currentMonth - 1, MainActivity.currentDay);
	        return tpd;
	      }
	      return super.onCreateDialog(id);
	}
	    
	OnDateSetListener myCallBack = new OnDateSetListener() {

	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	        int dayOfMonth) {
	        String strDay = (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
	        String strMonth = ((monthOfYear + 1) < 10 ? "0" : "") + (monthOfYear + 1);
	        tvDataPriobr.setText(strDay + "." + strMonth + "." + year);
	    }
	};
}
