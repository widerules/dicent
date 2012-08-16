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

import com.dicent.DicentState;

public class GoldDieData extends FirstEdDieData {
	@Override
	public int getDieType() {
		return GOLD_DIE;
	}

	@Override
	public int getDieColor() {
		return 0xff666600;
	}
	
	@Override
	public boolean isPowerDie() {
		return true;
	}

	@Override
	public SideValues getSideValues() {
		SideValues sv = new SideValues();
		switch (side) {
		case SIDE1:
			sv.enhancement = 3;
			break;
		case SIDE2:
			sv.enhancement = 3;
			break;
		case SIDE3:
			sv.enhancement = 3;
			break;
		case SIDE4:
			sv.surges = 3;
			break;
		case SIDE5:
			sv.surges = 3;
			break;
		case SIDE6:
			
			break;
		}
		return sv;
	}
	
	@Override
	public boolean isVisible() {
		if (DicentState.instance().isRtlEnabled()) return true;
		return false;
	}
}
