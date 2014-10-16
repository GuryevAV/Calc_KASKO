package ru.guryev_av.calckasko;

import java.util.Date;
import java.util.Scanner;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ProgressDialog dialog;
	String response;
	DB db;
	ProgressDialog pd;
	Handler h;
	SharedPreferences sPref;
	public final String APP_PREF = "Calc_KASKO_Pref";
	public static int currentDay, currentMonth, currentYear;
	Scanner scanner;
	Intent intent;
	
	private static final int MENU_AGENT_ID = 1;
	private static final int MENU_LOAD_ID = 2;
	private static final int MENU_SAVE_ID = 3;
	private static final int MENU_EXCH_ID = 4;
	private static final int MENU_BASE_ID = 5;
	private static final int MENU_OPTIMIZ_ID = 6;
	private static final int MENU_OPTION_ID = 7;
	private static final int MENU_SETTING_ID = 8;
	private static final int MENU_ATTENTION_ID = 9;
	private static final int MENU_NEWS = 10;
	
	private static final int ACT_AGENT = 1;
	private static final int ACT_RASCHET = 2;
	private static final int ACT_OPTIMIZ = 3;
	private static final int ACT_OPTIONS = 4;
	private static final int ACT_ATTENTION = 5;
	private static final int ACT_NEWS = 6;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        sPref = getSharedPreferences(APP_PREF, MODE_PRIVATE);
        
        //подключаемся к БД
      	db = new DB(this);
      	db.open();
      	
      	//String currentDateTimeString = (String) DateFormat.format("yyyy-MM-dd kk:mm:ss",new Date());
        currentDay = Integer.parseInt((String) DateFormat.format("dd",new Date()));
        currentMonth = Integer.parseInt((String) DateFormat.format("MM",new Date()));
        currentYear = Integer.parseInt((String) DateFormat.format("yyyy",new Date()));
      	
      	if (!sPref.getString("ratesDate", "").equals
      			(((String) DateFormat.format("dd.MM.yyyy",new Date())))) {
      		new RequestTask().execute(getString(R.string.www_cbr));
      	}
      	
	    setContentView(R.layout.main);
	    
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu oMenu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		oMenu.add(0, MENU_AGENT_ID, 0, R.string.agent);
		oMenu.add(0, MENU_LOAD_ID, 0, R.string.load_project);
		oMenu.add(0, MENU_SAVE_ID, 0, R.string.save_project);
		oMenu.add(0, MENU_EXCH_ID, 0, R.string.exch_rates);
		oMenu.add(0, MENU_BASE_ID, 0, R.string.bazovyj_raschet);
		oMenu.add(0, MENU_OPTIMIZ_ID, 0, R.string.optim_stoim);
		oMenu.add(0, MENU_OPTION_ID, 0, R.string.risk_option);
		oMenu.add(0, MENU_SETTING_ID, 0, R.string.setting);
		oMenu.add(0, MENU_ATTENTION_ID, 0, R.string.attention);
		oMenu.add(0, MENU_NEWS, 0, R.string.news);
		
		return super.onCreateOptionsMenu(oMenu);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem oItem) {
		switch (oItem.getItemId()) {
			case MENU_AGENT_ID:
				intent = new Intent(this, Agent.class);
				//intent.putExtra("new", true);
				startActivityForResult(intent, ACT_AGENT);
			break;
			case MENU_BASE_ID:
				intent = new Intent(this, Raschet.class);
				//intent.putExtra("new", true);
				startActivityForResult(intent, ACT_RASCHET);
			break;
			case MENU_OPTIMIZ_ID:
				intent = new Intent(this, Optimiz.class);
				//intent.putExtra("new", true);
				startActivityForResult(intent, ACT_OPTIMIZ);
			break;
			case MENU_OPTION_ID:
				intent = new Intent(this, Options.class);
				//intent.putExtra("new", true);
				startActivityForResult(intent, ACT_OPTIONS);
			break;
			case MENU_ATTENTION_ID:
				intent = new Intent(this, Attention_KV.class);
				//intent.putExtra("new", true);
				startActivityForResult(intent, ACT_ATTENTION);
				break;
			case MENU_EXCH_ID:
				new RequestTask().execute(getString(R.string.www_cbr));
			break;
			case MENU_NEWS:
				intent = new Intent(this, News.class);
				startActivityForResult(intent, ACT_NEWS);
			break;
		}
		return super.onOptionsItemSelected(oItem);
	}
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == ACT_AGENT) {
    		if (resultCode == RESULT_CANCELED) {
    			onExit();
    		}
    	}
    }
    
    protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
    
    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

                try {
                        //создаем запрос на сервер
                        DefaultHttpClient hc = new DefaultHttpClient();
                        ResponseHandler<String> res = new BasicResponseHandler();
                        //он у нас будет посылать post запрос
                        HttpPost postMethod = new HttpPost(params[0]);
                        //получаем ответ от сервера
                        response = hc.execute(postMethod, res);
                        //Log.d("1", response);
                        
                } catch (Exception e) {
                        System.out.println("Exp=" + e);
                }
                              
                return null;
        }

        @Override
        protected void onPostExecute(String result) {
                dialog.dismiss();
                XML(response);
                super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Получаем курсы валют...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                dialog.show();
                super.onPreExecute();
        }
    }
    
    void XML(String resp) {
    	if (TextUtils.isEmpty(resp)) {
    		Toast.makeText(this, "Пустой ответ из банка", Toast.LENGTH_LONG).show();
    	} else {
	    	char bufusd[] = new char[7];
	    	int start = resp.indexOf("Value", resp.indexOf("USD"));
	    	resp.getChars(start + 6, start + 13, bufusd, 0);
	    	String outusd = new String(bufusd);
	    	
	    	char bufeur[] = new char[7];
	    	start = resp.indexOf("Value", resp.indexOf("EUR"));
	    	resp.getChars(start + 6, start + 13, bufeur, 0);
	    	String outeur = new String(bufeur);
	    	
	    	char bufdate[] = new char[10];
	    	start = resp.indexOf("ValCurs", 0);
	    	resp.getChars(start + 14, start + 24, bufdate, 0);
	    	String outdate = new String(bufdate);
	    	
	    	Toast.makeText(this, outdate + "\n" + "USD=" + outusd + "\n" + "EUR=" +
	    			outeur, Toast.LENGTH_LONG).show();
	    	
	    	Editor ed = sPref.edit();
			ed.putString("ratesDate", outdate);
			ed.putString("usd", outusd);
			ed.putString("eur", outeur);
			ed.apply();
    	}
    }

    public void onExit() {
		db.close();
		finish();
	}
}
