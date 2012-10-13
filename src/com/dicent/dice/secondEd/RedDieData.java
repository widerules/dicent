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

import java.util.HashMap;

import com.dicent.dice.SideValues;

public class RedDieData extends PowerDieData {
	private static HashMap<Side, SideValues> sideValues = new HashMap<Side, SideValues>();
	
	static {
		sideValues.put(Side.SIDE1, new SideValues(0, 3, 1));
		sideValues.put(Side.SIDE2, new SideValues(0, 3, 0));
		sideValues.put(Side.SIDE3, new SideValues(0, 2, 0));
		sideValues.put(Side.SIDE4, new SideValues(0, 2, 0));
		sideValues.put(Side.SIDE5, new SideValues(0, 2, 0));
		sideValues.put(Side.SIDE6, new SideValues(0, 1, 0));
	}
	
	@Override
	public int getDieType() {
		return RED_DIE;
	}
	
	@Override
	public int getDieColor() {
		return 0xffcc0000;
	}
	
	@Override
	public boolean usesBlackIcons() {
		return false;
	}
	
	@Override
	public SideValues getSideValues() {
		return sideValues.get(side);
	}
}
