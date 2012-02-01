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
import android.widget.ImageView;
import android.widget.RelativeLayout;

public abstract class PowerDie extends Die {
	protected ImageView topLeftSurge;
	protected ImageView centeredSurge;


	public PowerDie(Context context, int _side, boolean _selected) {
		super(context, _side, _selected);
	}
	
	public void setSide(int side) {
		if (this.side == side) return;
		reset();
		if (topLeftSurge == null) {
			topLeftSurge = surge();
			RelativeLayout.LayoutParams leftSurgeLayoutParams = new RelativeLayout.LayoutParams((int)(surgeWidth * scaleFactor), (int)(surgeHeight * scaleFactor));
			leftSurgeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			leftSurgeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			topLeftSurge.setLayoutParams(leftSurgeLayoutParams);
		}
		
		if (centeredSurge == null) {
			centeredSurge = surge();
			RelativeLayout.LayoutParams centeredSurgeLayoutParams = new RelativeLayout.LayoutParams((int)(surgeWidth * scaleFactor), (int)(surgeHeight * scaleFactor));
			centeredSurgeLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			centeredSurgeLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
			centeredSurge.setLayoutParams(centeredSurgeLayoutParams);
		}
	}
}
