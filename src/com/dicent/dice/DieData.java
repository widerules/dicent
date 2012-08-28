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

import java.util.Random;

public abstract class DieData {
	private static Random generator = new Random();
	
	public Side side;
	public boolean isSelected;
	
	public DieData() {
		side = Side.SIDE1;
		isSelected = false;
	}
	
	public abstract int getDieType();
	
	public abstract int getDieColor();
	
	public void roll() {
		int random = generator.nextInt(Integer.MAX_VALUE);
		side = Side.values()[random % 6];
	}
	
	public boolean isVisible() {
		return true;
	}
	
	public abstract boolean usesBlackIcons();
	
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
