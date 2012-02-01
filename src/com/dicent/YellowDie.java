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

import android.content.Context;

public class YellowDie extends Die {

	public YellowDie(Context context, int _side, boolean _selected) {
		super(context, _side, _selected);
		dieContent.setBackgroundColor(0xFFDDDD00);
		useBlack = true;
		setSide(_side);
	}
	
	public void setSide(int side) {
		if (this.side == side) return;
		reset();
		if (side >= 0 && side <= 5) this.side = side;
		switch (side) {
		case 0:
			setRange(3);
			break;
		case 1:
			setRange(3);
			break;
		case 2:
			setRange(2);
			setSingleSurge();
			break;
		case 3:
			setRange(2);
			setSingleSurge();
			break;
		case 4:
			setRange(2);
			setWounds(1);
			break;
		case 5:
			setRange(1);
			setWounds(1);
			break;
		}
	}
}
