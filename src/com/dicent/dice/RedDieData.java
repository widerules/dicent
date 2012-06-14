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

public class RedDieData extends DieData {
	@Override
	public int getDieType() {
		return RED_DIE;
	}

	@Override
	public int getDieColor() {
		return 0xffcc0000;
	}

	@Override
	public SideValues getSideValues() {
		SideValues sv = new SideValues();
		switch (side) {
		case SIDE1:
			sv.wounds = 4;
			break;
		case SIDE2:
			sv.range = 1;
			sv.wounds = 3;
			sv.surges = 1;
			break;
		case SIDE3:
			sv.range = 1;
			sv.wounds = 3;
			break;
		case SIDE4:
			sv.range = 2;
			sv.wounds = 1;
			sv.surges = 1;
			break;
		case SIDE5:
			sv.range = 2;
			sv.wounds = 2;
			break;
		case SIDE6:
			sv.fail = true;
			break;
		}
		return sv;
	}
}
