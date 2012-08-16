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

package com.dicent.dice.firstEd;

import com.dicent.dice.DieData;

public abstract class FirstEdDieData extends DieData {
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
	
	public static FirstEdDieData create(int dieType) {
		FirstEdDieData newDieData;
		switch (dieType) {
		case FirstEdDieData.BLUE_DIE:
			newDieData = new BlueDieData();
			break;
		case FirstEdDieData.WHITE_DIE:
			newDieData = new WhiteDieData();
			break;
		case FirstEdDieData.GREEN_DIE:
			newDieData = new GreenDieData();
			break;
		case FirstEdDieData.YELLOW_DIE:
			newDieData = new YellowDieData();
			break;
		case FirstEdDieData.BLACK_DIE:
			newDieData = new BlackDieData();
			break;
		case FirstEdDieData.TRANSPARENT_DIE:
			newDieData = new TransparentDieData();
			break;
		case FirstEdDieData.SILVER_DIE:
			newDieData = new SilverDieData();
			break;
		case FirstEdDieData.GOLD_DIE:
			newDieData = new GoldDieData();
			break;
		default:
			newDieData = new RedDieData();	
		}
		return newDieData;
	}
	
	public FirstEdDieData copy() {
		FirstEdDieData newData = create(getDieType());
		newData.side = side;
		newData.isSelected = isSelected;
		return newData;
	}
	
	public boolean usesBlackIcons() {
		return false;
	}
	
	public boolean isPowerDie() {
		return false;
	}
	
	public abstract SideValues getSideValues();
}
