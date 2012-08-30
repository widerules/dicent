/** This file is part of Dicent.
 *
 *  Dicent is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  Dicent is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with Dicent.  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.dicent;

import java.util.ArrayList;

import com.dicent.dice.DieData;
import com.dicent.dice.firstEd.FirstEdDieData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteOpenHelper;

public class Storage extends SQLiteOpenHelper {
	public static final String DBNAME = "dicentDb";
	public static final int DBVERSION = 1;
	public static final String TABLE_DICE = "dice";
	
	public static final String DICE_PLAYER = "dieType";
	public static final String DICE_DIE = "die";
	public static final String DICE_SELECTED = "selected";
	
	public Storage(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_DICE + "(" +
				DICE_PLAYER + " INTEGER," +
				DICE_DIE + " INTEGER," +
				DICE_SELECTED + " BOOLEAN" +
				")");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	
	public void savePlayersDice(ArrayList<DiceList> playersDice) {
		SQLiteDatabase db = getWritableDatabase();
		//db.execSQL("DELTE FROM " + TABLE_DICE);
		db.delete(TABLE_DICE, null, null);
		SQLiteStatement insertStatement = db.compileStatement("INSERT INTO " + TABLE_DICE +
				" VALUES (?, ?, ?)");
		for (int i = 0; i < playersDice.size(); i++) {
			DiceList dice = playersDice.get(i);
			for (int j = 0; j < dice.size(); j++) {
				DieData data = dice.get(j);
				insertStatement.bindLong(1, i);
				insertStatement.bindLong(2, j);
				if (data.isSelected) insertStatement.bindLong(3, 1);
				else insertStatement.bindLong(3, 0);
				insertStatement.execute();
			}
		}
		db.close();
	}
	
	public void restorePlayesDice(ArrayList<DiceList> playersDice) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DICE, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			DieData data = playersDice.get(c.getInt(0)).get(c.getInt(1));
			if (c.getInt(2) == 1) data.isSelected = true;
			else data.isSelected = false;
			
			c.moveToNext();
		}
		db.close();
	}
	
}
