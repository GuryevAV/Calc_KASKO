package ru.guryev_av.makedb_calckasko;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends Activity {

	String response;
	DB db;
	ProgressDialog pd;
	Handler h;
	ExcelTask mt;
	KladrTask kt;
	Scanner scanner;
	Intent intent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //подключаемся к БД
      	db = new DB(this);
      	db.open();
      	
      		pd = new ProgressDialog(this);
      		pd.setTitle(getString(R.string.downloading));
      		pd.setMessage(getString(R.string.please_wait));
      		// меняем стиль на индикатор
      		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
      		// включаем анимацию ожидания
      		pd.setIndeterminate(true);
      		pd.show();
  	      
      		mt = new ExcelTask();
      		mt.execute();
      		      		
      	setContentView(R.layout.main);
	}
    
    protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
   
    class ExcelTask extends AsyncTask<Void, Integer, Void> {
	    
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }

	    @Override
	    protected Void doInBackground(Void... urls) {
	      try {
	    	  int cnt = 0;
	    	  int total;
	        	String[] data = {"", "", "", "", "", "", ""};
	        	float[] tarif = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	        	float coef;
	        	int[] data_int = {0, 0, 0, 0};
	        	String risk;
	        	Sheet sheet;
	        	AssetManager am = getAssets();
	        	InputStream is = am.open("data.xls");
				Workbook workbook = Workbook.getWorkbook(is);
								
				sheet = workbook.getSheet(1);
				total = sheet.getRows();
				sheet = workbook.getSheet(8);
				total += sheet.getRows();
				sheet = workbook.getSheet(2);
				total += sheet.getRows();
				total += 156;//регионы филиалы
				
				// устанавливаем максимум
			    pd.setMax(total);
				// выключаем анимацию ожидания
		        pd.setIndeterminate(false);
		          
				//ТС
				sheet = workbook.getSheet(1);
				for (int i = 3; i < sheet.getRows(); i++) {
					data[0] = sheet.getCell(0, i).getContents();
					data[1] = sheet.getCell(1, i).getContents();
					data[2] = sheet.getCell(2, i).getContents();
					data[3] = sheet.getCell(3, i).getContents();
					data[4] = sheet.getCell(4, i).getContents();
					data[5] = sheet.getCell(5, i).getContents();
					data[6] = sheet.getCell(6, i).getContents();
					Log.d("1", i + " " + data[0] + " " + data[1] + " " + data[2] + " " +
							data[3] + " " + data[4] + " " + data[5] + " " + data[6]);
					// выводим промежуточные результаты
			        publishProgress(++cnt);
					db.AddTS(data);
				}	
				
			    //цены
				sheet = workbook.getSheet(8);
				for (int i = 3; i < sheet.getRows(); i++) {
					data[0] = sheet.getCell(0, i).getContents();
					data[1] = sheet.getCell(1, i).getContents();
					data[3] = sheet.getCell(2, i).getContents();
					data[2] = sheet.getCell(3, i).getContents();
					Log.d("1", i + " " + data[0] + " " + data[1] + " " + data[2] + " " + data[3]);
					publishProgress(++cnt);
					db.AddPrice(data);
				}
				
				//тарифы
				sheet = workbook.getSheet(2);
				for (int i = 3; i < sheet.getRows(); i++) {
					data_int[0] = Integer.parseInt(sheet.getCell(0, i).getContents());
					tarif[0] = Float.parseFloat(sheet.getCell(1, i).getContents().replace(",", "."));
					tarif[1] = Float.parseFloat(sheet.getCell(2, i).getContents().replace(",", "."));
					tarif[2] = Float.parseFloat(sheet.getCell(3, i).getContents().replace(",", "."));
					tarif[3] = Float.parseFloat(sheet.getCell(4, i).getContents().replace(",", "."));
					tarif[4] = Float.parseFloat(sheet.getCell(5, i).getContents().replace(",", "."));
					tarif[5] = Float.parseFloat(sheet.getCell(6, i).getContents().replace(",", "."));
					tarif[6] = Float.parseFloat(sheet.getCell(7, i).getContents().replace(",", "."));
					tarif[7] = Float.parseFloat(sheet.getCell(8, i).getContents().replace(",", "."));
					tarif[8] = Float.parseFloat(sheet.getCell(9, i).getContents().replace(",", "."));
					tarif[9] = Float.parseFloat(sheet.getCell(10, i).getContents().replace(",", "."));
					tarif[10] = Float.parseFloat(sheet.getCell(11, i).getContents().replace(",", "."));
					tarif[11] = Float.parseFloat(sheet.getCell(12, i).getContents().replace(",", "."));
					tarif[12] = Float.parseFloat(sheet.getCell(13, i).getContents().replace(",", "."));
					tarif[13] = Float.parseFloat(sheet.getCell(14, i).getContents().replace(",", "."));
					tarif[14] = Float.parseFloat(sheet.getCell(15, i).getContents().replace(",", "."));
					tarif[15] = Float.parseFloat(sheet.getCell(16, i).getContents().replace(",", "."));
					risk = sheet.getCell(17, i).getContents();
					/*temp = sheet.getCell(18, i).getContents();
					data_int[1] = (TextUtils.isEmpty(temp)) ? 0 : Integer.parseInt(temp);
					temp = sheet.getCell(19, i).getContents();
					data_int[2] = (TextUtils.isEmpty(temp)) ? 0 : Integer.parseInt(temp);
					temp = sheet.getCell(20, i).getContents();
					data_int[3] = (TextUtils.isEmpty(temp)) ? 0 : Integer.parseInt(temp);*/
					Log.d("1", i + " " + data_int[0] + " " + tarif[0] + " " + tarif[1] +
							" " + tarif[2] + " " + tarif[3] + " " + tarif[4] + " " +
							tarif[5] + " " + tarif[6] + " " + tarif[7] + " " + tarif[8] +
							" " + tarif[9] + " " + tarif[10] + " " + tarif[11] + " " +
							tarif[12] + " " + tarif[13] + " " + tarif[14] + " " +
							tarif[15] + " " + risk);
					publishProgress(++cnt);
					db.AddTarif(risk, tarif, data_int);
				}
				workbook.close();
				is.close();
				
				//2-ой файл
				is = am.open("data2.xls");
				workbook = Workbook.getWorkbook(is);
				//регионы
				sheet = workbook.getSheet(3);
				//начинаются с 390-й строки по 476
				for (int i = 389; i < 476; i++) {
					data[0] = sheet.getCell(0, i).getContents();
					data[1] = sheet.getCell(1, i).getContents();
					data[2] = sheet.getCell(2, i).getContents();
					data_int[0] = Integer.parseInt(sheet.getCell(3, i).getContents());
					if (TextUtils.isEmpty(sheet.getCell(4, i).getContents())) {
						data_int[1] = 0;
					} else {
						data_int[1] = 1;
					}
					Log.d("1", i + " " + data[0] + " " + data[1] + " " + data[2] 
							+ " " + data_int[0] + " " + data_int[1]);
					publishProgress(++cnt);
					db.AddRegion(data, data_int);
				}
				
				//филиалы с 480 по 546
				for (int i = 479; i < 546; i++) {
					data[0] = sheet.getCell(0, i).getContents();
					data[1] = sheet.getCell(1, i).getContents();
					coef = Float.parseFloat(sheet.getCell(2, i).getContents().replace(",", "."));
					data[2] = sheet.getCell(4, i).getContents();
					data[3] = sheet.getCell(5, i).getContents();
					if (TextUtils.isEmpty(sheet.getCell(6, i).getContents())) {
						data_int[0] = 0;
					} else {
						data_int[0] = 1;
					}
					Log.d("1", i + " " + data[0] + " " + data[1] + " " + coef 
							+ " " + data[2] + " " + data[3] + " " + data_int[0]);
					publishProgress(++cnt);
					db.AddFilial(data, coef, data_int);
				}
				workbook.close();
				is.close();
														 
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      return null;
	    }

	    @Override
	    protected void onProgressUpdate(Integer... values) {
	      super.onProgressUpdate(values);
	      pd.incrementProgressBy(1);
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	    	super.onPostExecute(result);
	    	kt = new KladrTask();
    		kt.execute();
	    }
  	    
	  }
    
class KladrTask extends AsyncTask<Void, Integer, Void> {
	    
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }

	    @Override
	    protected Void doInBackground(Void... urls) {
	    		int cnt = 0;
	    		// устанавливаем максимум
			    pd.setMax(190328);
				// выключаем анимацию ожидания
		        pd.setIndeterminate(false);
		        
		        final String DIR_SD = "MyFiles";
				final String FILENAME_SD = "kladr.txt";
			    // проверяем доступность SD
			    if (!Environment.getExternalStorageState().equals(
			        Environment.MEDIA_MOUNTED)) {
			      Log.d("1", "SD-карта не доступна: " + Environment.getExternalStorageState());
			      
			    }
			    // получаем путь к SD
			    File sdPath = Environment.getExternalStorageDirectory();
			    // добавляем свой каталог к пути
			    sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
			    // формируем объект File, который содержит путь к файлу
			    File sdFile = new File(sdPath, FILENAME_SD);
			    try {
					scanner = new Scanner(sdFile);
					while (scanner.hasNext()) {
						String[] split_line = {"", "", "", ""};
					    String line = scanner.nextLine();
					    split_line = line.split(";");
					    String line4 = (split_line.length == 4) ? split_line[3] : "";
					    int index = (split_line.length == 4) ? Integer.parseInt(split_line[3]) : 0;
					    Log.d("1", split_line[0] + " " + split_line[1] +
					    		" " + split_line[2] + " " + line4);
					    db.AddKLADR(split_line, index);
					 // выводим промежуточные результаты
				        publishProgress(++cnt);
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    pd.dismiss();
				return null;
			  }
					
		
	    @Override
	    protected void onProgressUpdate(Integer... values) {
	    	super.onProgressUpdate(values);
	      	pd.incrementProgressBy(1);
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	    	super.onPostExecute(result);
	    	pd.dismiss();
	    }
  	    
	  }
    
}
