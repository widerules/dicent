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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteOpenHelper;

public class Storage extends SQLiteOpenHelper {
	public static final String DBNAME = "dicentDb";
	public static final int DBVERSION = 2;
	public static final String TABLE_DICE = "dice";
	
	public static final String MODE = "mode";
	public static final String DICE_PLAYER = "dieType";
	public static final String DICE_DIE = "die";
	public static final String DICE_SELECTED = "selected";
	
	public Storage(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_DICE + "(" +
				MODE + " INTEGER," + 
				DICE_PLAYER + " INTEGER," +
				DICE_DIE + " INTEGER," +
				DICE_SELECTED + " BOOLEAN" +
				")");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE_DICE);
		onCreate(db);
	}
	
	public void savePlayersDice(ArrayList<DiceList> firstEd, ArrayList<DiceList> secondEdAttack,
			ArrayList<DiceList> secondEdDefense) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_DICE, null, null);
		SQLiteStatement insertStatement = db.compileStatement("INSERT INTO " + TABLE_DICE +
				" VALUES (?, ?, ?, ?)");
		saveDiceLists(insertStatement, DicentActivity.MODE_FIRSTED, firstEd);
		saveDiceLists(insertStatement, DicentActivity.MODE_SECONDED_ATTACK, secondEdAttack);
		saveDiceLists(insertStatement, DicentActivity.MODE_SECONDED_DEFENSE, secondEdDefense);
		
		db.close();
	}
	
	public void restorePlayesDice(ArrayList<DiceList> firstEd, ArrayList<DiceList> secondEdAttack,
			ArrayList<DiceList> secondEdDefense) {
		SQLiteDatabase db = getReadableDatabase();
		restoreDiceLists(db, DicentActivity.MODE_FIRSTED, firstEd);
		restoreDiceLists(db, DicentActivity.MODE_SECONDED_ATTACK, secondEdAttack);
		restoreDiceLists(db, DicentActivity.MODE_SECONDED_DEFENSE, secondEdDefense);
		db.close();
	}
	
	private void saveDiceLists(SQLiteStatement insertStatement, int mode, ArrayList<DiceList> diceLists) {
		for (int i = 0; i < diceLists.size(); i++) {
			DiceList diceList = diceLists.get(i);
			for (int j = 0; j < diceList.size(); j++) {
				DieData data = diceList.get(j);
				insertStatement.bindLong(1, mode);
				insertStatement.bindLong(2, i);
				insertStatement.bindLong(3, j);
				if (data.isSelected) insertStatement.bindLong(4, 1);
				else insertStatement.bindLong(4, 0);
				insertStatement.execute();
			}
		}
	}
	
	private void restoreDiceLists(SQLiteDatabase db,  int mode, ArrayList<DiceList> diceLists) {
		Cursor c = db.rawQuery("SELECT " + DICE_PLAYER +
				", " + DICE_DIE + 
				", " + DICE_SELECTED + 
				" FROM " + TABLE_DICE + " WHERE " + MODE + " = " + mode, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			DieData data = diceLists.get(c.getInt(0)).get(c.getInt(1));
			if (c.getInt(2) == 1) data.isSelected = true;
			else data.isSelected = false;
			
			c.moveToNext();
		}
	}
}
