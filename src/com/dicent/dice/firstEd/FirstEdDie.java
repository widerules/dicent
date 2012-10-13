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

import com.dicent.R;
import com.dicent.dice.Die;
import com.dicent.dice.DieData;
import com.dicent.dice.SideValues;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class FirstEdDie extends Die {
	protected static Bitmap whiteSeparator;
	
	protected static float separatorWidth;
	protected static float separatorHeight;
	
	private FirstEdDieData firstEdDieData;
	
	public FirstEdDie(Context context, FirstEdDieData _firstEdDieData) {
		super(context, _firstEdDieData);
		
		firstEdDieData = _firstEdDieData;
		
		if (whiteSeparator == null) {
			whiteSeparator = BitmapFactory.decodeResource(context.getResources(), R.drawable.whiteseparator, null);
			separatorWidth = whiteSeparator.getWidth();
			separatorHeight = whiteSeparator.getHeight();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		SideValues sv = firstEdDieData.getSideValues();
		if (firstEdDieData.isPowerDie()) {
			if (sv.getEnhancement() > 0) drawEnhancement(canvas, sv.getEnhancement());
			else if (sv.getSurges() > 0) drawPowerDieSurges(canvas, sv.getSurges());
		} else {
			if (sv.isFail()) drawFail(canvas);
			else if (firstEdDieData.getDieType() == FirstEdDieData.TRANSPARENT_DIE) {}
			else {
				drawRange(canvas, sv.getRange());
				if (sv.getWounds() > 0) drawWounds(canvas, sv.getWounds());
				if (sv.getSurges() > 0) drawSurge(canvas);
			}
		}
	}
	
	
	
	protected void drawPowerDieSurges(Canvas canvas, int surges) {
		Bitmap usedBitmap;
		if (firstEdDieData.usesBlackIcons()) usedBitmap = blackSurge;
		else usedBitmap = whiteSurge;
		
		if (surges >= 1)
			canvas.drawBitmap(usedBitmap, 5.0f * density, vPadding, iconPaint);
		if (surges >= 2)
			canvas.drawBitmap(usedBitmap, (dScale - surgeWidth) / 2.0f, (dScale - surgeHeight) / 2.0f, iconPaint);
		if (surges >= 3) 
			canvas.drawBitmap(usedBitmap, dScale - 5.0f * density - surgeWidth, dScale - vPadding - surgeHeight, iconPaint);
	}
	
	protected void drawEnhancement(Canvas canvas, int enhancement) {
		drawRange(canvas, enhancement);
		drawWounds(canvas, enhancement);
		canvas.drawBitmap(whiteSeparator, (dScale - separatorWidth) / 2.0f, (dScale - separatorHeight) / 2.0f, iconPaint);
	}
	
	@Override
	public void setDieData(DieData _dieData) {
		if (_dieData instanceof FirstEdDieData) {
			firstEdDieData = (FirstEdDieData)_dieData;
			super.setDieData(firstEdDieData);
		}
	}
}
