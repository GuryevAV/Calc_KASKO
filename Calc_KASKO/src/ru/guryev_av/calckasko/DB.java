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
	
	//����������� ��������� ���� � ���� ������ ����������
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
	//���
	public static final String INSURED_lbEntityType = "lbEntityType";
	//�� � ������
	public static final String INSURED_lbSubjectID = "lbSubjectID";
	//�������� ������
	public static final String INSURED_chIsBank = "chIsBank";
	//�������� (��� ������)
	public static final String INSURED_lbDocument = "lbDocument";
	//��� ����
	public static final String INSURED_lstFaceType = "lstFaceType";
	//����������� ��� �����
	public static final String INSURED_chAsPerson = "chAsPerson";
	//�������
	public static final String INSURED_tbLastName = "tbLastName";
	//���
	public static final String INSURED_tbFirstName = "tbFirstName";
	//��������
	public static final String INSURED_tbMidName = "tbMidName";
	//���� ��������
	public static final String INSURED_tbBirthDate = "tbBirthDate";
	//���
	public static final String INSURED_lstSex = "lstSex";
	//�����������
	public static final String INSURED_lstCountry = "lstCountry";
	//���.�����
	public static final String INSURED_lstOrgForm = "lstOrgForm";
	//������� ������������
	public static final String INSURED_tbName = "tbName";
	//���
	public static final String INSURED_tbINN = "tbINN";
	//������������
	public static final String INSURED_tbFullName = "tbFullName";
	//��� ���� ���������
	public static final String INSURED_lstDocType = "lstDocType";
	//��� ���������
	public static final String INSURED_lbDocType = "lbDocType";
	//����� ���������
	public static final String INSURED_tbDocSerial = "tbDocSerial";
	//����� ���������
	public static final String INSURED_tbDocNumber = "tbDocNumber";
	//���� ������
	public static final String INSURED_tbDocDate = "tbDocDate";
	//����� ������
	public static final String INSURED_tbDocPlace = "tbDocPlace";
	//����� ����������� (������)
	public static final String INSURED_lbAddrReg = "lbAddrReg";
	//����� �����������
	public static final String INSURED_tbAddrReg = "tbAddrReg";
	//����� ���������� (������)
	public static final String INSURED_lbAddrFact = "lbAddrFact";
	//����� ����������
	public static final String INSURED_tbAddrFact = "tbAddrFact";
	//������ �� ���������
	public static final String INSURED_chEven = "chEven";
	//������� 1
	public static final String INSURED_tbPhone1 = "tbPhone1";
	//������� 2
	public static final String INSURED_tbPhone2 = "tbPhone2";
	//������� 3
	public static final String INSURED_tbPhone3 = "tbPhone3";
	
	public static final String TABLE_TSU = "tsu";
	public static final String TSU_ID = "_id";
	//��� ��������
	public static final String TSU_lbEntityType = "lbEntityType";
	//��� ��
	public static final String TSU_lstType = "lstType";
	//���, ���������� ��
	public static final String TSU_tbType = "tbType";
	//��������� ��
	public static final String TSU_tbCategory = "tbCategory";
	//������������� ��
	public static final String TSU_lstProducer = "lstProducer";
	//����� ��
	public static final String TSU_lstBrands = "lstBrands";
	//�� ����� ��
	public static final String TSU_lbBrandID = "lbBrandID";
	//����� ��
	public static final String TSU_lbBrand = "lbBrand";
	//������ ��
	public static final String TSU_lstModels = "lstModels";
	//�� ������
	public static final String TSU_lbModelID = "lbModelID";
	//������������ ������
	public static final String TSU_lbModel = "lbModel";
	//����������� ��
	public static final String TSU_tbModify = "tbModify";
	//������������
	public static final String TSU_tbName = "tbName";
	//��� �������
	public static final String TSU_tbYear = "tbYear";
	//���� �������
	public static final String TSU_tbMadeDate = "tbMadeDate";
	//�������� ��������� �.�.
	public static final String TSU_tbPower = "tbPower";
	//�������� ��������� ���
	public static final String TSU_lbPower = "lbPower";
	//������ �� ��������
	public static final String TSU_lbPowerGroup = "lbPowerGroup";
	//��
	public static final String TSU_lbKM = "lbKM";
	//����������� ����� ��
	public static final String TSU_tbGross = "tbGross";
	//���-�� ������������ ����
	public static final String TSU_tbPlaces = "tbPlaces";
	//���� �������������
	public static final String TSU_lstTarget = "lstTarget";
	//��� ��������� �� ��
	public static final String TSU_lstDocType = "lstDocType";
	//������������ ��������� ��
	public static final String TSU_lbDocType = "lbDocType";
	//����� ��������� �� ��
	public static final String TSU_tbDocSerial = "tbDocSerial";
	//����� ��������� �� ��
	public static final String TSU_tbDocNumber = "tbDocNumber";
	//���� ������ ��������� �� ��
	public static final String TSU_tbDocIssue = "tbDocIssue";
	//��� ���
	public static final String TSU_lstPTSType = "lstPTSType";
	//������������ ���
	public static final String TSU_lbPTSType = "lbPTSType";
	//����� ���
	public static final String TSU_tbPTSSerial = "tbPTSSerial";
	//����� ���
	public static final String TSU_tbPTSNumber = "tbPTSNumber";
	//���� ������ ���
	public static final String TSU_tbPTSIssue = "tbPTSIssue";
	//��������� ������ ���������
	public static final String TSU_chTTONeed = "chTTONeed";
	//�� ���� ���
	public static final String TSU_lstTTOType = "lstTTOType";
	//��� ���
	public static final String TSU_lbTTOType = "lbTTOType";
	//����� ���������
	public static final String TSU_tbTTOSerial = "tbTTOSerial";
	//����� ���������
	public static final String TSU_tbTTONumber = "tbTTONumber";
	//���� ������ (�����)
	public static final String TSU_lstTTOMonth = "lstTTOMonth";
	//���� ������ (���)
	public static final String TSU_lstTTOYear = "lstTTOYear";
	//���� ����� ��������������� �����
	public static final String TSU_tbTTOTill = "tbTTOTill";
	//���� �������� ���
	public static final String TSU_lbTTOTill = "lbTTOTill";
	//VIN
	public static final String TSU_tbVIN = "tbVIN";
	//���.�����
	public static final String TSU_tbRegNum = "tbRegNum";
	//���������� �����
	public static final String TSU_chTransit = "chTransit";
	//����� �����
	public static final String TSU_tbTub = "tbTub";
	//����� ������ ��
	public static final String TSU_tbBody = "tbBody";
	//����. ������
	public static final String TSU_chSignal = "chSignal";
	//���������������� � ���
	public static final String TSU_chVAI = "chVAI";
	
	private final Context mCtx;
	
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	
	
	Cursor c;
	ContentValues cv = new ContentValues();
	
	public DB(Context ctx) {
		mCtx = ctx;
	}
	
	//��������� �����������
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
		
	//��������� �����������
	public void close() {
		if (mDBHelper != null) mDBHelper.close();
	}
	
	// �������� ��� ������ �� ������� TS
	public Cursor getAllMarka() {
	    return mDB.query(TABLE_TS, null, null, null, TS_MARKA_NAME, null, null);
	}
	
	// �������� ������ �� ������
	public Cursor getModel(String marka) {
		return mDB.query(TABLE_TS, null, TS_MARKA_NAME + " = ?", new String[] {marka}, null, null, null);
	}
	
	// �������� ��� �������
	public Cursor getAllRegion() {
	    return mDB.query(TABLE_REGION, null, null, null, null, null, null);
	}
	
	// �������� ��� �������
	public Cursor getAllFilial() {
		return mDB.query(TABLE_FILIAL, null, null, null, null, null, null);
	}
	
	//�������� ���� ��
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
	
	//�������� id ������ �� ��������
	
		
	private class DBHelper extends SQLiteOpenHelper {

		private SQLiteDatabase myDataBase;
	    private final Context myContext;
	 
	    /*�����������
	    ��������� � ��������� ������ �� ���������� ��������
	    ��� ������� � �������� ���������� */ 
	    
		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			this.myContext = context;
			// TODO Auto-generated constructor stub
		}

		//������� ������ ���� ������ � �������������� �� ����� ����������� �����
	    
	    public void createDataBase() throws IOException{
	 
	        boolean dbExist = checkDataBase();
	 
	        if (dbExist) {
	            //������ �� ������ - ���� ��� ����
	        } else {
	 
	            //������� ���� ����� ������� ������ ����, ����� ��� ����� ������������
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
	     * ���������, ���������� �� ��� ��� ����, ����� �� ���������� ������ ��� ��� ������� ����������
	     * @return true ���� ����������, false ���� �� ����������
	     */
	    private boolean checkDataBase(){
	        File dbFile = new File(DB_PATH + DB_NAME);
	        return dbFile.exists();
	    }
	 
	    /**
	     * �������� ���� �� ����� assets ������� ��������� ��������� ��
	     * ����������� ����� ����������� ������ ������.
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
	 
	        //��������� ��
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
