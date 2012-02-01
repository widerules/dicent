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

public class SilverDie extends PowerDie {

	public SilverDie(Context context, int _side, boolean _selected) {
		super(context, _side, _selected);
		dieContent.setBackgroundColor(0xFFAAAAAA);
		setSide(_side);
	}
	
	public void setSide(int side) {
		super.setSide(side);
		if (this.side == side) return;
		reset();
		if (side >= 0 && side <= 5) this.side = side;
		switch (side) {
		case 0:
			dieContent.addView(separator());
			dieContent.addView(wounds(2));
			dieContent.addView(range(2));
			enhancement = 2;
			break;
		case 1:
			dieContent.addView(separator());
			dieContent.addView(wounds(2));
			dieContent.addView(range(2));
			enhancement = 2;
			break;
		case 2:
			dieContent.addView(separator());
			dieContent.addView(wounds(2));
			dieContent.addView(range(2));
			enhancement = 2;
			break;
		case 3:
			dieContent.addView(topLeftSurge);
			dieContent.addView(centeredSurge);
			surges = 2;
			break;
		case 4:
			dieContent.addView(topLeftSurge);
			dieContent.addView(centeredSurge);
			surges = 2;
			break;
		}
	}
}
