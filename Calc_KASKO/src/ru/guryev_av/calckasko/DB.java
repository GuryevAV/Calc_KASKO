package ru.guryev_av.calckasko;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {
	
	//стандартный системный путь к базе данных приложения
    private static final String DB_PATH = "/data/data/ru.guryev_av.calckasko/databases/";
	private static final String DB_NAME = "mydb";
	private static final int DB_VERSION = 1;
	
	public static final String TABLE_TS = "ts";
	public static final String TS_ID = "_id";
	public static final String TS_MARKA_ID = "marka_id";
	public static final String TS_MARKA_NAME = "marka_name";
	public static final String TS_MODEL_ID = "model_id";
	public static final String TS_MODEL_NAME = "model_name";
	public static final String TS_RF = "rf";
	public static final String TS_TYPE_ID = "type_id";
	public static final String TS_SUBTYPE_ID = "subtype_id";
	
	public static final String TABLE_PRICE = "price";
	public static final String PRICE_ID = "_id";
	public static final String PRICE_ID_YEAR = "id_year";
	public static final String PRICE_MIN = "price_min";
	public static final String PRICE_AVG = "price_avg";
	public static final String PRICE_MAX = "price_max";
	
	public static final String TABLE_TARIF = "tarif";
	public static final String TARIF_ID = "_id";
	public static final String TARIF_ID_MODEL = "id_model";
	public static final String TARIF_H0 = "h0";
	public static final String TARIF_H1 = "h1";
	public static final String TARIF_H2 = "h2";
	public static final String TARIF_H3 = "h3";
	public static final String TARIF_H4 = "h4";
	public static final String TARIF_H5 = "h5";
	public static final String TARIF_H6 = "h6";
	public static final String TARIF_H7 = "h7";
	public static final String TARIF_Y0 = "y0";
	public static final String TARIF_Y1 = "y1";
	public static final String TARIF_Y2 = "y2";
	public static final String TARIF_Y3 = "y3";
	public static final String TARIF_Y4 = "y4";
	public static final String TARIF_Y5 = "y5";
	public static final String TARIF_Y6 = "y6";
	public static final String TARIF_Y7 = "y7";
	public static final String TARIF_RISK = "risk";
	public static final String TARIF_EXTRA = "extra";
	public static final String TARIF_AGE = "age";
	public static final String TARIF_AGE_OLD = "old";
	
	public static final String TABLE_KLADR = "kladr";
	public static final String KLADR_ID = "_id";
	public static final String KLADR_CODE = "code";
	public static final String KLADR_NAME = "name";
	public static final String KLADR_TYPE = "type";
	public static final String KLADR_POST_INDEX = "post_index";
	
	public static final String TABLE_REGION = "region";
	public static final String REGION_ID = "_id";
	public static final String REGION_CODE = "code";
	public static final String REGION_NAME = "name";
	public static final String REGION_TYPE = "type";
	public static final String REGION_UNICUS = "unicus";
	public static final String REGION_DAGO = "dago";
	
	public static final String TABLE_FILIAL = "filial";
	public static final String FILIAL_ID = "_id";
	public static final String FILIAL_CODE = "code";
	public static final String FILIAL_NAME = "name";
	public static final String FILIAL_UNICUS = "unicus";
	public static final String FILIAL_DAGO = "dago";
	public static final String FILIAL_COEF = "coef";
	public static final String FILIAL_SERIA = "seria";
	
	public static final String TABLE_INSURED = "insured";
	public static final String INSURED_ID = "_id";
	//Тип
	public static final String INSURED_lbEntityType = "lbEntityType";
	//ИД в Уникус
	public static final String INSURED_lbSubjectID = "lbSubjectID";
	//Является банком
	public static final String INSURED_chIsBank = "chIsBank";
	//Документ (для связки)
	public static final String INSURED_lbDocument = "lbDocument";
	//Тип лица
	public static final String INSURED_lstFaceType = "lstFaceType";
	//Тарификация как физик
	public static final String INSURED_chAsPerson = "chAsPerson";
	//Фамилия
	public static final String INSURED_tbLastName = "tbLastName";
	//Имя
	public static final String INSURED_tbFirstName = "tbFirstName";
	//Отчество
	public static final String INSURED_tbMidName = "tbMidName";
	//Дата рождения
	public static final String INSURED_tbBirthDate = "tbBirthDate";
	//Пол
	public static final String INSURED_lstSex = "lstSex";
	//Гражданство
	public static final String INSURED_lstCountry = "lstCountry";
	//Орг.форма
	public static final String INSURED_lstOrgForm = "lstOrgForm";
	//Краткое наименование
	public static final String INSURED_tbName = "tbName";
	//ИНН
	public static final String INSURED_tbINN = "tbINN";
	//Наименование
	public static final String INSURED_tbFullName = "tbFullName";
	//Код типа документа
	public static final String INSURED_lstDocType = "lstDocType";
	//Тип документа
	public static final String INSURED_lbDocType = "lbDocType";
	//Серия документа
	public static final String INSURED_tbDocSerial = "tbDocSerial";
	//Номер документа
	public static final String INSURED_tbDocNumber = "tbDocNumber";
	//Дата выдачи
	public static final String INSURED_tbDocDate = "tbDocDate";
	//Место выдачи
	public static final String INSURED_tbDocPlace = "tbDocPlace";
	//Адрес регистрации (строка)
	public static final String INSURED_lbAddrReg = "lbAddrReg";
	//Адрес регистрации
	public static final String INSURED_tbAddrReg = "tbAddrReg";
	//Адрес нахождения (строка)
	public static final String INSURED_lbAddrFact = "lbAddrFact";
	//Адрес нахождения
	public static final String INSURED_tbAddrFact = "tbAddrFact";
	//Адреса не совпадают
	public static final String INSURED_chEven = "chEven";
	//Телефон 1
	public static final String INSURED_tbPhone1 = "tbPhone1";
	//Телефон 2
	public static final String INSURED_tbPhone2 = "tbPhone2";
	//Телефон 3
	public static final String INSURED_tbPhone3 = "tbPhone3";
	
	public static final String TABLE_TSU = "tsu";
	public static final String TSU_ID = "_id";
	//Тип сущности
	public static final String TSU_lbEntityType = "lbEntityType";
	//Тип ТС
	public static final String TSU_lstType = "lstType";
	//Тип, назначение ТС
	public static final String TSU_tbType = "tbType";
	//Категория ТС
	public static final String TSU_tbCategory = "tbCategory";
	//Производитель ТС
	public static final String TSU_lstProducer = "lstProducer";
	//Марки ТС
	public static final String TSU_lstBrands = "lstBrands";
	//ИД марки ТС
	public static final String TSU_lbBrandID = "lbBrandID";
	//Марка ТС
	public static final String TSU_lbBrand = "lbBrand";
	//Модели ТС
	public static final String TSU_lstModels = "lstModels";
	//ИД модели
	public static final String TSU_lbModelID = "lbModelID";
	//Наименование модели
	public static final String TSU_lbModel = "lbModel";
	//Модификация ТС
	public static final String TSU_tbModify = "tbModify";
	//Наименование
	public static final String TSU_tbName = "tbName";
	//Год выпуска
	public static final String TSU_tbYear = "tbYear";
	//Дата выпуска
	public static final String TSU_tbMadeDate = "tbMadeDate";
	//Мощность двигателя Л.С.
	public static final String TSU_tbPower = "tbPower";
	//Мощность двигателя кВт
	public static final String TSU_lbPower = "lbPower";
	//Группа по мощности
	public static final String TSU_lbPowerGroup = "lbPowerGroup";
	//КМ
	public static final String TSU_lbKM = "lbKM";
	//Разрешенная масса ТС
	public static final String TSU_tbGross = "tbGross";
	//Кол-во пассажирских мест
	public static final String TSU_tbPlaces = "tbPlaces";
	//Цель использования
	public static final String TSU_lstTarget = "lstTarget";
	//Тип документа на ТС
	public static final String TSU_lstDocType = "lstDocType";
	//Наименование документа ТС
	public static final String TSU_lbDocType = "lbDocType";
	//Серия документа на ТС
	public static final String TSU_tbDocSerial = "tbDocSerial";
	//Номер документа на ТС
	public static final String TSU_tbDocNumber = "tbDocNumber";
	//Дата выдачи документа на ТС
	public static final String TSU_tbDocIssue = "tbDocIssue";
	//Тип ПТС
	public static final String TSU_lstPTSType = "lstPTSType";
	//Наименование ПТС
	public static final String TSU_lbPTSType = "lbPTSType";
	//Серия ПТС
	public static final String TSU_tbPTSSerial = "tbPTSSerial";
	//Номер ПТС
	public static final String TSU_tbPTSNumber = "tbPTSNumber";
	//Дата выдачи ПТС
	public static final String TSU_tbPTSIssue = "tbPTSIssue";
	//Требовать данные техталона
	public static final String TSU_chTTONeed = "chTTONeed";
	//ИД типа ТТО
	public static final String TSU_lstTTOType = "lstTTOType";
	//Тип ТТО
	public static final String TSU_lbTTOType = "lbTTOType";
	//Серия техталона
	public static final String TSU_tbTTOSerial = "tbTTOSerial";
	//Номер техталона
	public static final String TSU_tbTTONumber = "tbTTONumber";
	//Срок талона (месяц)
	public static final String TSU_lstTTOMonth = "lstTTOMonth";
	//Срок талона (год)
	public static final String TSU_lstTTOYear = "lstTTOYear";
	//Дата срока диагностической карты
	public static final String TSU_tbTTOTill = "tbTTOTill";
	//Срок действия ТТО
	public static final String TSU_lbTTOTill = "lbTTOTill";
	//VIN
	public static final String TSU_tbVIN = "tbVIN";
	//Рег.номер
	public static final String TSU_tbRegNum = "tbRegNum";
	//Транзитный номер
	public static final String TSU_chTransit = "chTransit";
	//Номер шасси
	public static final String TSU_tbTub = "tbTub";
	//Номер кузова ТС
	public static final String TSU_tbBody = "tbBody";
	//Спец. сигнал
	public static final String TSU_chSignal = "chSignal";
	//Зарегистрировано в ВАИ
	public static final String TSU_chVAI = "chVAI";
	
	private final Context mCtx;
	
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	
	
	Cursor c;
	ContentValues cv = new ContentValues();
	
	public DB(Context ctx) {
		mCtx = ctx;
	}
	
	//открываем подключение
	public void open() {
		mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
		try {
			mDBHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
	 
		/*try {
			mDBHelper.openDataBase();
		}catch(SQLException sqle){
			throw sqle;
		}*/
		mDB = mDBHelper.getWritableDatabase();
	}
		
	//закрываем подключение
	public void close() {
		if (mDBHelper != null) mDBHelper.close();
	}
	
	// получить все данные из таблицы TS
	public Cursor getAllMarka() {
	    return mDB.query(TABLE_TS, null, null, null, TS_MARKA_NAME, null, null);
	}
	
	// получить данные по маркам
	public Cursor getModel(String marka) {
		return mDB.query(TABLE_TS, null, TS_MARKA_NAME + " = ?", new String[] {marka}, null, null, null);
	}
	
	// получить все регионы
	public Cursor getAllRegion() {
	    return mDB.query(TABLE_REGION, null, null, null, null, null, null);
	}
	
	// получить все филиалы
	public Cursor getAllFilial() {
		return mDB.query(TABLE_FILIAL, null, null, null, null, null, null);
	}
	
	//получить цены ТС
	public String[] getPrice(String id_year) {
		String[] result = { "", "", "" };
		Cursor c1 = mDB.query(TABLE_PRICE, null, PRICE_ID_YEAR + " = ?", new String[] { id_year }, null, null, null);
		if (c1 != null) {
			if (c1.moveToFirst()) {
				result[0] = c1.getString(c1.getColumnIndex(PRICE_MIN)); 
				result[1] = c1.getString(c1.getColumnIndex(PRICE_MAX)); 
				result[2] = c1.getString(c1.getColumnIndex(PRICE_AVG)); 
			}
		}
		return result;
	}
	
	//получить id модели по названию
	
		
	private class DBHelper extends SQLiteOpenHelper {

		private SQLiteDatabase myDataBase;
	    private final Context myContext;
	 
	    /*Конструктор
	    Принимает и сохраняет ссылку на переданный контекст
	    для доступа к ресурсам приложения */ 
	    
		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			this.myContext = context;
			// TODO Auto-generated constructor stub
		}

		//Создает пустую базу данных и перезаписывает ее нашей собственной базой
	    
	    public void createDataBase() throws IOException{
	 
	        boolean dbExist = checkDataBase();
	 
	        if (dbExist) {
	            //ничего не делать - база уже есть
	        } else {
	 
	            //вызывая этот метод создаем пустую базу, позже она будет перезаписана
	            this.getReadableDatabase();
	 
	            try {
	 
	                //copyDataBase();
	                copyFromZipFile();
	 
	            } catch (IOException e) {
	 
	                throw new Error("Error copying database");
	 
	            }
	        }
	 
	    }
	 
	    /**
	     * Проверяет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложения
	     * @return true если существует, false если не существует
	     */
	    private boolean checkDataBase(){
	        File dbFile = new File(DB_PATH + DB_NAME);
	        return dbFile.exists();
	    }
	 
	    /**
	     * Копирует базу из папки assets заместо созданной локальной БД
	     * Выполняется путем копирования потока байтов.
	     * */
	    
	    
	    private void copyFromZipFile() throws IOException{
	        InputStream is = myContext.getResources().openRawResource(R.raw.mydb);
	        File outFile = new File(DB_PATH ,DB_NAME);
	        OutputStream myOutput = new FileOutputStream(outFile.getAbsolutePath());
	        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
	        try {
	            ZipEntry ze;
	            while ((ze = zis.getNextEntry()) != null) {
	                ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                byte[] buffer = new byte[1024];
	                int count;
	                
	                while ((count = zis.read(buffer)) != -1) {
	                    baos.write(buffer, 0, count);
	                }
	                baos.writeTo(myOutput);
	            }
	        } finally {
	            zis.close();
	            myOutput.flush();
	            myOutput.close();
	            is.close();
	        }
	    }
	 
	    public void openDataBase() throws SQLException{
	 
	        //открываем БД
	        String myPath = DB_PATH + DB_NAME;
	        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	    }
	 
	    @Override
	    public synchronized void close() {
	 
	            if(myDataBase != null)
	                myDataBase.close();
	 
	            super.close();
	 
	    }
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
