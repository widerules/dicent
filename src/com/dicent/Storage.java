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

import com.dicent.dice.DieData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteOpenHelper;

public class Storage extends SQLiteOpenHelper {
	private static final int FIRSTED = 0;
	private static final int SECONDED_ATTACK = 1;
	private static final int SECONDED_DEFENSE = 2;
	
	public static final String DBNAME = "dicentDb";
	public static final int DBVERSION = 3;
	public static final String TABLE_DICE = "dice";
	
	public static final String MODE = "mode";
	public static final String DICE_DIE = "die";
	public static final String DICE_SELECTED = "selected";
	
	public Storage(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_DICE + "(" +
				MODE + " INTEGER," +
				DICE_DIE + " INTEGER," +
				DICE_SELECTED + " BOOLEAN" +
				")");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE_DICE);
		onCreate(db);
	}
	
	public void saveDice(DiceList firstEd, DiceList secondEdAttack, DiceList secondEdDefense) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_DICE, null, null);
		SQLiteStatement insertStatement = db.compileStatement("INSERT INTO " + TABLE_DICE +
				" VALUES (?, ?, ?)");
		saveDiceList(insertStatement, FIRSTED, firstEd);
		saveDiceList(insertStatement, SECONDED_ATTACK, secondEdAttack);
		saveDiceList(insertStatement, SECONDED_DEFENSE, secondEdDefense);
		
		db.close();
	}
	
	public void restoreDice(DiceList firstEd,DiceList secondEdAttack, DiceList secondEdDefense) {
		SQLiteDatabase db = getReadableDatabase();
		restoreDiceList(db, FIRSTED, firstEd);
		restoreDiceList(db, SECONDED_ATTACK, secondEdAttack);
		restoreDiceList(db, SECONDED_DEFENSE, secondEdDefense);
		db.close();
	}
	
	private void saveDiceList(SQLiteStatement insertStatement, int mode, DiceList diceList) {
		for (int i = 0; i < diceList.size(); i++) {
			DieData data = diceList.get(i);
			insertStatement.bindLong(1, mode);
			insertStatement.bindLong(2, i);
			if (data.isSelected) insertStatement.bindLong(3, 1);
			else insertStatement.bindLong(3, 0);
			insertStatement.execute();
		}
	}
	
	private void restoreDiceList(SQLiteDatabase db,  int mode, DiceList diceList) {
		Cursor c = db.rawQuery("SELECT " + DICE_DIE + 
				", " + DICE_SELECTED + 
				" FROM " + TABLE_DICE + " WHERE " + MODE + " = " + mode, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			DieData data = diceList.get(c.getInt(0));
			if (c.getInt(1) == 1) data.isSelected = true;
			else data.isSelected = false;
			
			c.moveToNext();
		}
	}
}
