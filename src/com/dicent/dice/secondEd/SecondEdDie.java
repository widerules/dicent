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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.dicent.R;
import com.dicent.dice.Die;
import com.dicent.dice.DieData;
import com.dicent.dice.SideValues;

public class SecondEdDie extends Die {
	protected static Bitmap whiteShield;
	protected static Bitmap blackShield;
	
	protected static float shieldWidth;
	protected static float shieldHeight;
	
	private SecondEdDieData secondEdDieData;

	public SecondEdDie(Context context, SecondEdDieData _dieData) {
		super(context, _dieData);
		
		secondEdDieData = _dieData;
		
		if (whiteShield == null) {
			whiteShield = BitmapFactory.decodeResource(context.getResources(), R.drawable.whiteshield, null);
			blackShield = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackshield, null);
			
			shieldWidth = whiteShield.getWidth();
			shieldHeight = whiteShield.getHeight();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		SideValues sv = secondEdDieData.getSideValues();
		
		if (secondEdDieData instanceof DefenseDieData) drawShields(canvas, sv.getShields());
		else if (sv.isFail()) drawFail(canvas);
		else {
			if (sv.getRange() > 0) drawRange(canvas, sv.getRange());
			if (sv.getWounds() > 0) drawWounds(canvas, sv.getWounds());
			if (sv.getSurges() > 0) drawSurge(canvas);
		}
	}
	
	private void drawShields(Canvas canvas, int shields) {
		Bitmap usedBitmap;
		if (secondEdDieData.usesBlackIcons()) usedBitmap = blackShield;
		else usedBitmap = whiteShield;
		
		if (shields == 1) {
			canvas.drawBitmap(usedBitmap, (dScale - shieldWidth) / 2,
					(dScale - shieldHeight) / 2, iconPaint);
			return;
		}
		
		if (shields >= 2) canvas.drawBitmap(usedBitmap, hPadding, vPadding, iconPaint);
		if (shields == 2 || shields == 4) canvas.drawBitmap(usedBitmap, dScale - hPadding - shieldWidth,
				dScale - vPadding - shieldHeight, iconPaint);
		if (shields >= 3) canvas.drawBitmap(usedBitmap, dScale - hPadding - shieldWidth,
				vPadding, iconPaint);
		if (shields == 3) canvas.drawBitmap(usedBitmap, (dScale - shieldWidth) / 2,
				dScale - vPadding- shieldHeight, iconPaint);
		if (shields == 4) canvas.drawBitmap(usedBitmap, hPadding,
				dScale - vPadding - shieldHeight, iconPaint);
	}
	
	@Override
	public void setDieData(DieData _dieData) {
		if (_dieData instanceof SecondEdDieData) {
			secondEdDieData = (SecondEdDieData)_dieData;
			super.setDieData(secondEdDieData);
		}
	}
}
