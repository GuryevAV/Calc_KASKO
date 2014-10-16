package ru.guryev_av.calckasko;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

public class Attention_KV extends Activity{

	Spinner spinSkidkaRazm;
	CheckBox cbYaOznak;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attention_kv);
		
		spinSkidkaRazm = (Spinner) findViewById(R.id.spinSkidkaRazm);
		cbYaOznak = (CheckBox) findViewById(R.id.cbYaOznak);
		
		if (cbYaOznak.isChecked()) {
			spinSkidkaRazm.setEnabled(true);
		} else {
			spinSkidkaRazm.setEnabled(false);
		}
		
		cbYaOznak.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					spinSkidkaRazm.setEnabled(true);
				} else {
					spinSkidkaRazm.setEnabled(false);
				}
			}
			
		});
	}
}
