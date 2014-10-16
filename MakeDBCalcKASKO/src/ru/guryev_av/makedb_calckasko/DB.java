package ru.guryev_av.makedb_calckasko;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {
	
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
	/*public static final String TARIF_EXTRA = "extra";
	public static final String TARIF_AGE = "age";
	public static final String TARIF_AGE_OLD = "old";*/
	
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
	
	private static final String CREATE_TABLE_TS = "create table " + TABLE_TS + "(" 
			+ TS_ID + " integer primary key autoincrement,"
			+ TS_MARKA_ID + " integer," 
			+ TS_MARKA_NAME + " text,"
			+ TS_MODEL_ID + " integer,"
			+ TS_MODEL_NAME + " text,"
			+ TS_RF + " integer,"
			+ TS_TYPE_ID + " integer,"
			+ TS_SUBTYPE_ID + " integer"
			+ ");";
	
	private static final String CREATE_TABLE_PRICE = "create table " + TABLE_PRICE + "(" 
			+ PRICE_ID + " integer primary key autoincrement,"
			+ PRICE_ID_YEAR + " text,"
			+ PRICE_MIN + " integer,"
			+ PRICE_AVG + " integer,"
			+ PRICE_MAX + " integer"
			+ ");";
	
	private static final String CREATE_TABLE_TARIF = "create table " + TABLE_TARIF + "(" 
			+ TARIF_ID + " integer primary key autoincrement,"
			+ TARIF_ID_MODEL + " integer,"
			+ TARIF_H0 + " real,"
			+ TARIF_H1 + " real,"
			+ TARIF_H2 + " real,"
			+ TARIF_H3 + " real,"
			+ TARIF_H4 + " real,"
			+ TARIF_H5 + " real,"
			+ TARIF_H6 + " real,"
			+ TARIF_H7 + " real,"
			+ TARIF_Y0 + " real,"
			+ TARIF_Y1 + " real,"
			+ TARIF_Y2 + " real,"
			+ TARIF_Y3 + " real,"
			+ TARIF_Y4 + " real,"
			+ TARIF_Y5 + " real,"
			+ TARIF_Y6 + " real,"
			+ TARIF_Y7 + " real,"
			+ TARIF_RISK + " text"
			/*+ TARIF_EXTRA + " integer,"
			+ TARIF_AGE + " integer,"
			+ TARIF_AGE_OLD + " integer"*/
			+ ");";
	
	private static final String CREATE_TABLE_KLADR = "create table " + TABLE_KLADR + "(" 
			+ KLADR_ID + " integer primary key autoincrement,"
			+ KLADR_CODE + " text,"
			+ KLADR_NAME + " text,"
			+ KLADR_TYPE + " text,"
			+ KLADR_POST_INDEX + " integer"
			+ ");";
	
	private static final String CREATE_TABLE_INSURED = "create table " + TABLE_INSURED + "("
			+ INSURED_ID + " integer primary key autoincrement,"
			+ INSURED_lbEntityType + " text,"
			+ INSURED_lbSubjectID + " integer,"
			+ INSURED_chIsBank + " integer,"//Boolean
			+ INSURED_lbDocument + " text,"
			+ INSURED_lstFaceType + " text,"
			+ INSURED_chAsPerson + " integer,"//Boolean
			+ INSURED_tbLastName + " text,"
			+ INSURED_tbFirstName + " text,"
			+ INSURED_tbMidName + " text,"
			+ INSURED_tbBirthDate + " text,"//Date
			+ INSURED_lstSex + " text,"
			+ INSURED_lstCountry + " integer,"
			+ INSURED_lstOrgForm + " text,"
			+ INSURED_tbName + " text,"
			+ INSURED_tbINN + " text,"
			+ INSURED_tbFullName + " text,"
			+ INSURED_lstDocType + " text,"
			+ INSURED_lbDocType + " text,"
			+ INSURED_tbDocSerial + " text,"
			+ INSURED_tbDocNumber + " text,"
			+ INSURED_tbDocDate + " text,"//Date
			+ INSURED_tbDocPlace + " text,"
			+ INSURED_lbAddrReg + " text,"
			+ INSURED_tbAddrReg + " text,"
			+ INSURED_lbAddrFact + " text,"
			+ INSURED_tbAddrFact + " text,"
			+ INSURED_chEven + " integer,"//Boolean
			+ INSURED_tbPhone1 + " text,"
			+ INSURED_tbPhone2 + " text,"
			+ INSURED_tbPhone3 + " text"
			+ ");";
	
	private static final String CREATE_TABLE_TSU = "create table " + TABLE_TSU + "("
			+ TSU_ID + " integer primary key autoincrement,"
			+ TSU_lbEntityType
			+ TSU_lstType
			+ TSU_tbType
			+ TSU_tbCategory
			+ TSU_lstProducer
			+ TSU_lstBrands
			+ TSU_lbBrandID 
			+ TSU_lbBrand
			+ TSU_lstModels
			+ TSU_lbModelID
			+ TSU_lbModel
			+ TSU_tbModify
			+ TSU_tbName
			+ TSU_tbYear
			+ TSU_tbMadeDate
			+ TSU_tbPower
			+ TSU_lbPower
			+ TSU_lbPowerGroup
			+ TSU_lbKM
			+ TSU_tbGross
			+ TSU_tbPlaces
			+ TSU_lstTarget
			+ TSU_lstDocType
			+ TSU_lbDocType
			+ TSU_tbDocSerial
			+ TSU_tbDocNumber
			+ TSU_tbDocIssue
			+ TSU_lstPTSType
			+ TSU_lbPTSType
			+ TSU_tbPTSSerial
			+ TSU_tbPTSNumber
			+ TSU_tbPTSIssue
			+ TSU_chTTONeed
			+ TSU_lstTTOType
			+ TSU_lbTTOType
			+ TSU_tbTTOSerial
			+ TSU_tbTTONumber
			+ TSU_lstTTOMonth
			+ TSU_lstTTOYear
			+ TSU_tbTTOTill
			+ TSU_lbTTOTill
			+ TSU_tbVIN
			+ TSU_tbRegNum
			+ TSU_chTransit
			+ TSU_tbTub
			+ TSU_tbBody
			+ TSU_chSignal
			+ TSU_chVAI
			+ ");";
	
	private static final String CREATE_TABLE_REGION = "create table " + TABLE_REGION + "(" 
			+ REGION_ID + " integer primary key autoincrement,"
			+ REGION_CODE + " text,"
			+ REGION_NAME + " text,"
			+ REGION_TYPE + " text,"
			+ REGION_UNICUS + " integer,"
			+ REGION_DAGO + " integer"
			+ ");";
	
	private static final String CREATE_TABLE_FILIAL = "create table " + TABLE_FILIAL + "(" 
			+ FILIAL_ID + " integer primary key autoincrement,"
			+ FILIAL_NAME + " text,"
			+ FILIAL_CODE + " text,"
			+ FILIAL_COEF + " real,"
			+ FILIAL_UNICUS + " integer,"
			+ FILIAL_SERIA + " integer,"
			+ FILIAL_DAGO + " integer"
			+ ");";
	
	Cursor c;
	ContentValues cv = new ContentValues();
	
	public DB(Context ctx) {
		mCtx = ctx;
	}
	
	//открываем подключение
	public void open() {
		mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
		mDB = mDBHelper.getWritableDatabase();
	}
		
	//закрываем подключение
	public void close() {
		if (mDBHelper != null) mDBHelper.close();
	}
		
	//добавить ТС
	public void AddTS(String[] ts) {
		cv.clear();
		cv.put(TS_MARKA_ID, ts[0]);
		cv.put(TS_MARKA_NAME, ts[1]);
		cv.put(TS_MODEL_ID, ts[2]);
		cv.put(TS_MODEL_NAME, ts[3]);
		cv.put(TS_RF, ts[4]);
		cv.put(TS_TYPE_ID, ts[5]);
		cv.put(TS_SUBTYPE_ID, ts[6]);
		mDB.insert(TABLE_TS, null, cv);
	}
	
	//добавить цену
	public void AddPrice(String[] price) {
		cv.clear();
		cv.put(PRICE_ID_YEAR, price[0]);
		cv.put(PRICE_MIN, price[1]);
		cv.put(PRICE_AVG, price[2]);
		cv.put(PRICE_MAX, price[3]);
		mDB.insert(TABLE_PRICE, null, cv);
	}
	
	//добавить элемент классификатора
	public void AddKLADR(String[] kladr, int index) {
		cv.clear();
		cv.put(KLADR_CODE, kladr[0]);
		cv.put(KLADR_NAME, kladr[1]);
		cv.put(KLADR_TYPE, kladr[2]);
		cv.put(KLADR_POST_INDEX, index);
		mDB.insert(TABLE_KLADR, null, cv);
	}
	
	//добавить регион
	public void AddRegion(String[] region, int[] data) {
		cv.clear();
		cv.put(REGION_CODE, region[0]);
		cv.put(REGION_NAME, region[1]);
		cv.put(REGION_TYPE, region[2]);
		cv.put(REGION_UNICUS, data[0]);
		cv.put(REGION_DAGO, data[1]);
		mDB.insert(TABLE_REGION, null, cv);
	}
	
	//добавить филиал
	public void AddFilial(String[] fil, float coef, int[] data) {
		cv.clear();
		cv.put(FILIAL_NAME, fil[0]);
		cv.put(FILIAL_CODE, fil[1]);
		cv.put(FILIAL_COEF, coef);
		cv.put(FILIAL_UNICUS, fil[2]);
		cv.put(FILIAL_SERIA, fil[3]);
		cv.put(FILIAL_DAGO, data[0]);
		mDB.insert(TABLE_FILIAL, null, cv);
	}
	
	//добавить тариф
	public void AddTarif(String risk, float[] tarif, int[] data) {
		cv.clear();
		cv.put(TARIF_ID_MODEL, data[0]);
		cv.put(TARIF_H0, tarif[0]);
		cv.put(TARIF_H1, tarif[1]);
		cv.put(TARIF_H2, tarif[2]);
		cv.put(TARIF_H3, tarif[3]);
		cv.put(TARIF_H4, tarif[4]);
		cv.put(TARIF_H5, tarif[5]);
		cv.put(TARIF_H6, tarif[6]);
		cv.put(TARIF_H7, tarif[7]);
		cv.put(TARIF_Y0, tarif[8]);
		cv.put(TARIF_Y1, tarif[9]);
		cv.put(TARIF_Y2, tarif[10]);
		cv.put(TARIF_Y3, tarif[11]);
		cv.put(TARIF_Y4, tarif[12]);
		cv.put(TARIF_Y5, tarif[13]);
		cv.put(TARIF_Y6, tarif[14]);
		cv.put(TARIF_Y7, tarif[15]);
		cv.put(TARIF_RISK, risk);
		/*cv.put(TARIF_EXTRA, data[1]);
		cv.put(TARIF_AGE, data[2]);
		cv.put(TARIF_AGE_OLD, data[3]);*/
		mDB.insert(TABLE_TARIF, null, cv);
	}
	
	// получить все данные из таблицы TS
	public Cursor getAllMarka() {
	    return mDB.query(TABLE_TS, null, null, null, TS_MARKA_NAME, null, null);
	}
	
	// получить данные по маркам
	public Cursor getModel(String marka) {
		return mDB.query(TABLE_TS, null, TS_MARKA_NAME + " = ?", new String[] {marka}, null, null, null);
	}
	
	private class DBHelper extends SQLiteOpenHelper {

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
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			//создаем таблицу ТС
			db.execSQL(CREATE_TABLE_TS);
			db.execSQL(CREATE_TABLE_PRICE);
			db.execSQL(CREATE_TABLE_TARIF);
			db.execSQL(CREATE_TABLE_KLADR);
			db.execSQL(CREATE_TABLE_INSURED);
			db.execSQL(CREATE_TABLE_REGION);
			db.execSQL(CREATE_TABLE_FILIAL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}
}
