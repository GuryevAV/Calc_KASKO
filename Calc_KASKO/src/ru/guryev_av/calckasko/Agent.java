package ru.guryev_av.calckasko;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Agent extends Activity {
	
	DB db;
	Cursor cursorRegion, cursorFilial;
	SimpleCursorAdapter scAdapterRegion, scAdapterFilial;
	EditText etKodAgent, etFIOAgent, etDoverenNomer;
	TextView tvDataDoveren, tvError;
	Button btnAgree;
	private static final int DIALOG_DATE_DOVEREN = 1;
	SharedPreferences sPref;
	public final String APP_PREF = "Calc_KASKO_Pref";
	DialogFragment dlgCancel;
	Intent intent;
	Spinner spinTipAgent, spinRegion, spinFilial;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agent);
		
		intent = new Intent();
		
		dlgCancel = new DialogCancelAgent();
		
		sPref = getSharedPreferences(APP_PREF, MODE_PRIVATE);
		
		//подключаемся к БД
      	db = new DB(this);
      	db.open();
      	
      	// Получаем спиннер
      	spinTipAgent = (Spinner) findViewById(R.id.spinTipAgent);
	    spinRegion = (Spinner) findViewById(R.id.spinRegion);
	    spinFilial = (Spinner) findViewById(R.id.spinFilial);
	    
	    // получаем курсор
	    cursorRegion = db.getAllRegion();
	    startManagingCursor(cursorRegion);
	    cursorFilial = db.getAllFilial();
	    startManagingCursor(cursorFilial);
	     
	    // формируем столбцы сопоставления
	    String[] fromRegion = new String[] { DB.REGION_NAME };
	    String[] fromFilial = new String[] { DB.FILIAL_NAME };
	    int[] to = new int[] { android.R.id.text1 };
	    
	    // создааем адаптер и настраиваем список
	    scAdapterRegion = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursorRegion, fromRegion, to);
	    scAdapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinRegion.setAdapter(scAdapterRegion);
	    scAdapterFilial = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursorFilial, fromFilial, to);
	    scAdapterFilial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinFilial.setAdapter(scAdapterFilial);
	    
	    etKodAgent = (EditText) findViewById(R.id.etKodAgent);
	    etFIOAgent = (EditText) findViewById(R.id.etFIOAgent);
	    etDoverenNomer = (EditText) findViewById(R.id.etDoverenNomer);
	    tvDataDoveren = (TextView) findViewById(R.id.tvDataDoveren);
	    tvDataDoveren.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DIALOG_DATE_DOVEREN);
			}
		});
	    tvError = (TextView) findViewById(R.id.tvError);
	    etKodAgent.addTextChangedListener(watcher);
	    etFIOAgent.addTextChangedListener(watcher);
	    etDoverenNomer.addTextChangedListener(watcher);
	    btnAgree = (Button) findViewById(R.id.btnAgree);
	    
	    etKodAgent.setText(sPref.getString("agentKod", ""));
	    etFIOAgent.setText(sPref.getString("agentFIO", ""));
	    etDoverenNomer.setText(sPref.getString("agentDovNomer", ""));
	    tvDataDoveren.setText(sPref.getString("agentDovDate", ""));
	    spinTipAgent.setSelection(sPref.getInt("agentTip", 0));
	    spinRegion.setSelection(sPref.getInt("agentRegion", 0));
	    spinFilial.setSelection(sPref.getInt("agentFilial", 0));
	    checkFields();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
	
	private Boolean checkFields() {
		Boolean result = true;
		String ErrorText = "";
		if (etKodAgent.getText().toString().trim().length() < 4) {
			etKodAgent.setBackgroundColor(Color.RED);
			ErrorText = (String) getText(R.string.kod_agent_) + "\n";
			result = false;
		} else {
			etKodAgent.setBackground(getResources().getDrawable(
					android.R.drawable.editbox_background));
		}
		
		if (etFIOAgent.getText().toString().trim().length() < 5) {
			etFIOAgent.setBackgroundColor(Color.RED);
			ErrorText = ErrorText + (String) getText(R.string.name_agent) + "\n";
			result = false;
		} else {
			etFIOAgent.setBackground(getResources().getDrawable(
					android.R.drawable.editbox_background)); 
		}
		
		if (TextUtils.isEmpty(etDoverenNomer.getText())) {
			etDoverenNomer.setBackgroundColor(Color.RED);
			ErrorText = ErrorText + (String) getText(R.string.nomer_doveren) + "\n";
			result = false;
		} else {
			etDoverenNomer.setBackground(getResources().getDrawable(
					android.R.drawable.editbox_background)); 
		}
		
		if (TextUtils.isEmpty(tvDataDoveren.getText())) {
			tvDataDoveren.setBackgroundColor(Color.RED);
			ErrorText = ErrorText + (String) getText(R.string.data_doveren) + "\n";
			result = false;
		} else {
			tvDataDoveren.setBackground(getResources().getDrawable(
					android.R.drawable.editbox_background)); 
		}
		tvError.setText(ErrorText);
		//btnAgree.setClickable(result);
		btnAgree.setEnabled(result);
		return result;
	} 
	
	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
	      if (id == DIALOG_DATE_DOVEREN) {
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
	        tvDataDoveren.setText(strDay + "." + strMonth + "." + year);
	        checkFields();
	    }
	};
	
	TextWatcher watcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable edt) {
			// TODO Auto-generated method stub
			checkFields();
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
	
	public void onClick(View v) {
		Editor ed = sPref.edit();
		ed.putString("agentKod", etKodAgent.getText().toString());
		ed.putString("agentFIO", etFIOAgent.getText().toString());
		ed.putString("agentDovNomer", etDoverenNomer.getText().toString());
		ed.putString("agentDovDate", tvDataDoveren.getText().toString());
		ed.putInt("agentTip", spinTipAgent.getSelectedItemPosition());
		ed.putInt("agentRegion", spinRegion.getSelectedItemPosition());
		ed.putInt("agentFilial", spinFilial.getSelectedItemPosition());
		ed.apply();
		setResult(RESULT_OK, intent);
		onExit();
	}
	
	@Override
	public void onBackPressed() {
		dlgCancel.show(getFragmentManager(), "dlgCancel");
	}
	
	public void onExit() {
		db.close();
		finish();
	}
	
	public void doNotAgree() {
		setResult(RESULT_CANCELED, intent);
		onExit();
	}
}
