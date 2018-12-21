package com.example.cccp.classified;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

public class DB {

    private static final String DB_CREATE =
            "create table " + "mytab" + "(" +
                    "_id" + " integer primary key autoincrement, " + //id, cursor.getString(0);
                    "img" + " integer, " + //картинка, cursor.getString(1);
                    "txt" + " text, " + //текст, cursor.getString(2);
                    "name" + " text, " + //название, cursor.getString(3);
                    "key" + " integer, " + //пин-код, cursor.getString(4);
                    "secret" + " text " + //секретное название , cursor.getString(5);
                    ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        mDBHelper = new DBHelper(mCtx, "mydb", null, 1);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query("mytab", null, null, null, null, null, null);
    }

    public void addRec(String txt, int img, String text) { //добавить
        ContentValues cv = new ContentValues();
        cv.put("name", txt);
        cv.put("img", img);
        cv.put("txt", text);
        cv.put("key", 0);
        cv.put("secret", "ClASSIFIED");
        mDB.insert("mytab", null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) { //удалить

        mDB.delete("mytab", "_id" + " = " + id, null);


    }

    public void updName(String replacementName, long id){
        ContentValues cv = new ContentValues();
        cv.put("name", replacementName);
        mDB.update("mytab", cv, "_id = " + id, null);
    }
    public void updTxt(String replacementTxt, long id){
        ContentValues cv = new ContentValues();
        cv.put("txt", replacementTxt);
        mDB.update("mytab", cv, "_id = " + id, null);
    }

    public void updImg(int replacementImg, long id){

        ContentValues cv = new ContentValues();
        cv.put("img", replacementImg);
        mDB.update("mytab", cv, "_id = " + id, null);
    }
    public void updKey(int replacementKey, long id){
        ContentValues cv = new ContentValues();
        cv.put("key", replacementKey);
        mDB.update("mytab", cv, "_id = " + id, null);
    }
    public void updSecret(String replacementSecret, long id){
        ContentValues cv = new ContentValues();
        cv.put("secret", replacementSecret);
        mDB.update("mytab", cv, "_id = " + id, null);
    }

    public void del() { //удалить всё

        mDB.delete("mytab", null, null);

    }


    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            cv.put("name", "Добро пожаловать в приложение");
            cv.put("img", R.drawable.locked);
            cv.put("txt", "Здесь вы можете вводить текст");
            cv.put("key", 0);
            cv.put("secret", "ClASSIFIED");
            db.insert("mytab", null, cv);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}