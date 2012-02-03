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

public abstract class DieData {
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
	
	public Side side;
	public boolean isSelected;
	
	public static DieData create(int dieType) {
		DieData newDieData;
		switch (dieType) {
		case DieData.BLUE_DIE:
			newDieData = new BlueDieData();
			break;
		case DieData.WHITE_DIE:
			newDieData = new WhiteDieData();
			break;
		case DieData.GREEN_DIE:
			newDieData = new GreenDieData();
			break;
		case DieData.YELLOW_DIE:
			newDieData = new YellowDieData();
			break;
		case DieData.BLACK_DIE:
			newDieData = new BlackDieData();
			break;
		case DieData.TRANSPARENT_DIE:
			newDieData = new TransparentDieData();
			break;
		case DieData.SILVER_DIE:
			newDieData = new SilverDieData();
			break;
		case DieData.GOLD_DIE:
			newDieData = new GoldDieData();
			break;
		default:
			newDieData = new RedDieData();	
		}
		return newDieData;
	}
	
	public DieData() {
		side = Side.SIDE1;
		isSelected = false;
	}
	
	public abstract int getDieType();
	
	public abstract int getDieColor();
	
	public boolean usesBlackIcons() {
		return false;
	}
	
	public boolean isPowerDie() {
		return false;
	}
	
	public abstract SideValues getSideValues();
	
	public enum Side {
		SIDE1,
		SIDE2,
		SIDE3,
		SIDE4,
		SIDE5,
		SIDE6
	}
}
