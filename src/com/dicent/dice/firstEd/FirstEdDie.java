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

import com.dicent.DieAdapter;
import com.dicent.R;
import com.dicent.dice.Die;
import com.dicent.dice.SideValues;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class FirstEdDie extends Die {
	protected static Bitmap whiteWound;
	protected static Bitmap blackWound;
	protected static Bitmap whiteSurge;
	protected static Bitmap blackSurge;
	protected static Bitmap whiteSeparator;
	protected static Bitmap whiteFail;
	protected static Bitmap blackFail;
	
	protected static float woundWidth;
	protected static float woundHeight;
	protected static float surgeWidth;
	protected static float surgeHeight;
	
	private FirstEdDieData firstEdDieData;
	
	public FirstEdDie(Context context, FirstEdDieData _firstEdDieData, DieAdapter _dieAdapter) {
		super(context, _firstEdDieData, _dieAdapter);
		
		firstEdDieData = _firstEdDieData;
		
		if (whiteWound == null) {
			whiteWound = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitewound, null);
			blackWound = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackwound, null);
			whiteSurge = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitesurge, null);
			blackSurge = BitmapFactory.decodeResource(context.getResources(), R.drawable.blacksurge, null);
			whiteSeparator = BitmapFactory.decodeResource(context.getResources(), R.drawable.whiteseparator, null);
			whiteFail = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitefail, null);
			blackFail = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackfail, null);
			
			woundWidth = whiteWound.getWidth();
			woundHeight = whiteWound.getHeight();
			surgeWidth = whiteSurge.getWidth();
			surgeHeight = whiteSurge.getHeight();
		}
	}
	
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
	
	protected void drawRange(Canvas canvas, int range) {
		Paint usedPaint;
		if (firstEdDieData.usesBlackIcons()) usedPaint = blackTextPaint;
		else usedPaint = whiteTextPaint;
		canvas.drawText(Integer.toString(range), 10.0f * density, (scale - 13.0f) * density, usedPaint);
	}
	
	protected void drawWounds(Canvas canvas, int wounds) {
		Bitmap usedBitmap;
		if (firstEdDieData.usesBlackIcons()) usedBitmap = blackWound;
		else usedBitmap = whiteWound;
		
		if (wounds >= 1) 
			canvas.drawBitmap(usedBitmap, (scale - 10.0f) * density - woundWidth, 12.0f * density, iconPaint);
		if (wounds >= 2)
			canvas.drawBitmap(usedBitmap, (scale - 12.0f) * density - woundWidth * 2.0f, 12.0f * density, iconPaint);
		if (wounds == 3)
			canvas.drawBitmap(usedBitmap, (scale - 10.0f - 2.0f) * density - woundWidth * 1.5f, (12.0f + 2.0f) * density + woundHeight, iconPaint);
		if (wounds == 4) {
			canvas.drawBitmap(usedBitmap, (scale - 10.0f) * density - woundWidth, (12.0f + 2.0f) * density + woundHeight, iconPaint);
			canvas.drawBitmap(usedBitmap, (scale - 10.0f - 2.0f) * density - woundWidth * 2.0f, (12.0f + 2.0f) * density + woundHeight, iconPaint);
		}
	}
	
	protected void drawSurge(Canvas canvas) {
		Bitmap usedBitmap;
		if (firstEdDieData.usesBlackIcons()) usedBitmap = blackSurge;
		else usedBitmap = whiteSurge;
		
		canvas.drawBitmap(usedBitmap, (scale - 5.0f) * density - surgeWidth, (scale - 10.0f) * density - surgeHeight, iconPaint);
	}
	
	protected void drawPowerDieSurges(Canvas canvas, int surges) {
		Bitmap usedBitmap;
		if (firstEdDieData.usesBlackIcons()) usedBitmap = blackSurge;
		else usedBitmap = whiteSurge;
		
		if (surges >= 1)
			canvas.drawBitmap(usedBitmap, 10.0f * density, 10.0f * density, iconPaint);
		if (surges >= 2)
			canvas.drawBitmap(usedBitmap, (2.5f + scale / 2.0f) * density - surgeWidth / 2.0f, (scale / 2.0f) * density - surgeHeight / 2.0f, iconPaint);
		if (surges >= 3) 
			canvas.drawBitmap(usedBitmap, (scale - 5.0f) * density  - surgeWidth, (scale - 10.0f) * density - surgeHeight, iconPaint);
	}
	
	protected void drawEnhancement(Canvas canvas, int enhancement) {
		drawRange(canvas, enhancement);
		drawWounds(canvas, enhancement);
		canvas.drawBitmap(whiteSeparator, 15.0f * density, 15.0f * density, iconPaint);
	}
	
	protected void drawFail(Canvas canvas) {
		Bitmap usedBitmap;
		if (firstEdDieData.usesBlackIcons()) usedBitmap = blackFail;
		else usedBitmap = whiteFail;
		
		canvas.drawBitmap(usedBitmap, 15.0f * density, 15.0f * density, iconPaint);
	}
	
	public void setDieData(FirstEdDieData _firstEdDieData) {
		firstEdDieData = _firstEdDieData;
	}
}
