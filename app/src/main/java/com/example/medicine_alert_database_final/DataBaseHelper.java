package com.example.medicine_alert_database_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String MEDICINE_TABLE = "MEDICINE_TABLE";
    public static final String COLUMN_MEDICINE_NAME = "MEDICINE_NAME";
    public static final String COLUMN_MEDICINE_DATE = "MEDICINE_DATE";
    public static final String COLUMN_MEDICINE_TIME = "MEDICINE_TIME";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {

        super(context, "medicine.db", null, 1);
    }

    //this is called when its used first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + MEDICINE_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_MEDICINE_NAME + " TEXT, " + COLUMN_MEDICINE_DATE + " TEXT, " + COLUMN_MEDICINE_TIME + " TEXT) ";
        db.execSQL(createTableStatement);

    }
    // version number of database changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(MedicineModel medicineModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MEDICINE_NAME,medicineModel.getName());
        cv.put(COLUMN_MEDICINE_DATE,medicineModel.getDate());
        cv.put(COLUMN_MEDICINE_TIME,medicineModel.getTime());
        long insert = db.insert(MEDICINE_TABLE,null,cv);
        return insert != -1;
    }
    public boolean deleteOne(MedicineModel medicineModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + MEDICINE_TABLE + " WHERE " + COLUMN_ID + " = " + medicineModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {
            return true;
        }
        else
        {
            return false;
        }

    }

    public List<MedicineModel> getEveryone(){
        List<MedicineModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + MEDICINE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            //loop through cursor and create a new medicine
            do{
                int medicineId = cursor.getInt(0);
                String medicineName = cursor.getString(1);
                String medicineDate = cursor.getString(2);
                String medicineTime = cursor.getString(3);

                MedicineModel newMedicine = new MedicineModel(medicineId,medicineName,medicineDate,medicineTime);
                returnList.add(newMedicine);

            }while (cursor.moveToNext());
        }
        else
        {
        }
        cursor.close();
        db.close();

        return returnList;
    }
}

