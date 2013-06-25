package com.example.lovegame_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

public class CreateDB extends SQLiteOpenHelper {

	private String CreateTable = "create table  oioiTable( ID integer primary key autoincrement, Pergunta text not null, Status integer not null);";
	Context context;

	public CreateDB(Context contex, String name, CursorFactory factory,
			int version) {
		super(contex, name, factory, version);
		// TODO Auto-generated constructor stub
		context = contex;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CreateTable);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS oioiTable");

		onCreate(db);

	}

	public int Length() {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM oioiTable;", null);
		int ListLength = c.getCount();
		db.close();
		return ListLength;
	}

	public void Add(int ID, String Pergunta, int Status) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put("ID", ID);
		cv.put("Pergunta", Pergunta);
		cv.put("Status", Status);

		db.insert("oioiTable", "ID", cv);

		db.close();
	}

	public void Update(int ID, int Status) {
		SQLiteDatabase db = this.getWritableDatabase();
		String upd = "UPDATE oioiTable SET Status=" + Status + " where ID="
				+ ID;
		db.execSQL(upd);

		db.close();
	}

	public String Queery(int ID) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM oioiTable;", null);

		c.move(ID);

	
		db.close();
		return c.getString(1);
	}
}
