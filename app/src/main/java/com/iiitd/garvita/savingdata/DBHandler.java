package com.iiitd.garvita.savingdata;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME="login.db";
    private static final String TABLE_LOGIN ="login";
    private static final String COLUMN_USERNAME ="_username";
    private static final String COLUMN_PASSWORD = "_password";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DATABASE_NAME , factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String query = "CREATE TABLE " + TABLE_LOGIN + "(" + COLUMN_USERNAME +" VARCHAR(50) PRIMARY KEY, " + COLUMN_PASSWORD + " VARCHAR(50) " + ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
   sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+TABLE_LOGIN);
        onCreate(sqLiteDatabase);
    }
    // add user to database
    public void addUser(Database user)
    {
        ContentValues value =new ContentValues();
        value.put(COLUMN_USERNAME,user.get_username());
        value.put(COLUMN_PASSWORD,user.get_password());
        SQLiteDatabase db = getWritableDatabase();
        db.insert( TABLE_LOGIN,null,value);
        db.close();
    }
    //delete user
    public void deleteuser(String username)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LOGIN + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\";" );
    }
    public void updateuser(String username,String Password)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_LOGIN + " SET " + COLUMN_PASSWORD + "=\"" + Password + "\"" + " WHERE " +COLUMN_USERNAME + "=\"" + username + "\";" );
    }
   public String displaydata()
   {
       String string = null;
       SQLiteDatabase db = getWritableDatabase();
       String query = "SELECT * FROM " + TABLE_LOGIN + " WHERE 1";
       //cursor to point to database
       Cursor c = db.rawQuery(query,null);
       //mover cursor to first location
       c.moveToFirst();
       while(c.moveToNext())
       {
           if(c.getString(c.getColumnIndex(COLUMN_USERNAME))!=null)
           {
               string +=((c.getString(c.getColumnIndex(COLUMN_USERNAME)))+ "  \t  " +(c.getString(c.getColumnIndex(COLUMN_PASSWORD))));
               string +="\n";
           }
       }

       db.close();
      // System.out.println(string);
       return string;

   }


}
