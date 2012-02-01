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

package com.dicent.dice;

import android.content.Context;

public class DieData {
	public static final int RED_DIE = 0;
	public static final int BLUE_DIE = 1;
	public static final int WHITE_DIE = 2;
	public static final int GREEN_DIE = 3;
	public static final int YELLOW_DIE = 4;
	public static final int BLACK_DIE = 5;
	public static final int SILVER_DIE = 6;
	public static final int GOLD_DIE = 7;
	public static final int TRANSPARENT_DIE = 8;
	public static final int[] POWER_DICE = {BLACK_DIE, SILVER_DIE, GOLD_DIE};
	
	public static final int POWER_DICE_TYPES_COUNT = POWER_DICE.length;
	public static final int DIE_TYPES_COUNT = 9;
	
	public int dieType;
	public int side;
	public boolean isSelected;
	
	public DieData(int type) {
		dieType = type;
		side = 0;
		isSelected = false;
	}
	
	public Die createDie(Context context) {
		Die newDie;
		switch (dieType) {
		case DieData.BLUE_DIE:
			newDie = new BlueDie(context, side, isSelected);
			break;
		case DieData.WHITE_DIE:
			newDie = new WhiteDie(context, side, isSelected);
			break;
		case DieData.GREEN_DIE:
			newDie = new GreenDie(context, side, isSelected);
			break;
		case DieData.YELLOW_DIE:
			newDie = new YellowDie(context, side, isSelected);
			break;
		case DieData.BLACK_DIE:
			newDie = new BlackDie(context, side, isSelected);
			break;
		case DieData.TRANSPARENT_DIE:
			newDie = new TransparentDie(context, side, isSelected);
			break;
		case DieData.SILVER_DIE:
			newDie = new SilverDie(context, side, isSelected);
			break;
		case DieData.GOLD_DIE:
			newDie = new GoldDie(context, side, isSelected);
			break;
		default:
			newDie = new RedDie(context, side, isSelected);	
		}
		return newDie;
	}
}
