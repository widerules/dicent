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

package com.dicent.dice.secondEd;

import com.dicent.dice.DieData;

public abstract class SecondEdDieData extends DieData {
	public static final int BROWN_DIE = 100;
	public static final int GREY_DIE = 101;
	public static final int DKGREY_DIE = 102;
	public static final int BLUE_DIE = 103;
	public static final int RED_DIE = 104;
	public static final int YELLOW_DIE = 105;
	public static final int GREEN_DIE = 106;
	
	public static SecondEdDieData create(int dieType) {
		SecondEdDieData secondEdDieData;
		switch (dieType) {
		case BROWN_DIE:
			secondEdDieData = new BrownDieData();
			break;
		case GREY_DIE:
			secondEdDieData = new GreyDieData();
			break;
		case DKGREY_DIE:
			secondEdDieData = new DkGreyDieData();
			break;
		case BLUE_DIE:
			secondEdDieData = new BlueDieData();
			break;
		case RED_DIE:
			secondEdDieData = new RedDieData();
			break;
		case YELLOW_DIE:
			secondEdDieData = new YellowDieData();
			break;
		case GREEN_DIE:
			secondEdDieData = new GreenDieData();
			break;
		default:
			secondEdDieData = new BlueDieData();	
		}
		return secondEdDieData;
	}
	
	@Override
	public SecondEdDieData copy() {
		SecondEdDieData newData = create(getDieType());
		newData.side = side;
		newData.isSelected = isSelected;
		return newData;
	}
}
