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

import com.dicent.DieAdapter;
import com.dicent.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public abstract class Die extends View {
	public static final float scale = 70.0f;
	protected static final float textSize = 20.0f;
	
	protected static float dScale;
	
	protected static Paint borderPaint;
	protected static Paint selectedBorderPaint;
	protected static Paint whiteTextPaint;
	protected static Paint blackTextPaint;
	protected static Paint iconPaint;
	
	protected static Bitmap whiteWound;
	protected static Bitmap blackWound;
	protected static Bitmap whiteSurge;
	protected static Bitmap blackSurge;
	protected static Bitmap whiteFail;
	protected static Bitmap blackFail;
	
	protected static Bitmap dieBackground;
	
	protected static float density = 0.0f;
	protected static int size;
	
	protected static float hPadding = 0.0f;
	protected static float vPadding = 0.0f;
	
	protected static float woundWidth;
	protected static float woundHeight;
	protected static float surgeWidth;
	protected static float surgeHeight;
	protected static float failWidth;
	protected static float failHeight;
	
	protected static float woundsMargin = 0.0f;
	
	private DieData dieData;
	private DieAdapter dieAdapter;
	
	static {
		borderPaint = new Paint();
		borderPaint.setColor(Color.DKGRAY);
		borderPaint.setStyle(Paint.Style.STROKE);
		borderPaint.setStrokeWidth(4.0f);
		
		selectedBorderPaint = new Paint(borderPaint);
		selectedBorderPaint.setColor(Color.CYAN);
		
		whiteTextPaint = new Paint();
		whiteTextPaint.setColor(Color.WHITE);
		whiteTextPaint.setAntiAlias(true);
		
		blackTextPaint = new Paint(whiteTextPaint);
		blackTextPaint.setColor(Color.BLACK);
		
		iconPaint = new Paint();
		iconPaint.setFilterBitmap(true);
		iconPaint.setDither(true);
	}
	
	public Die(Context context, DieData _dieData, DieAdapter _dieAdapter) {
		super(context);
		
		dieAdapter = _dieAdapter;
		dieData = _dieData;
		
		if (density <= 0.0f) {
			density = context.getResources().getDisplayMetrics().density;
			dScale = scale * density;
			size = (int)(dScale);
			hPadding = 13.0f * density;
			vPadding = 10.0f * density;
			whiteTextPaint.setTextSize(textSize * density);
			blackTextPaint.setTextSize(textSize * density);
			borderPaint.setStrokeWidth(4.0f * density);
			selectedBorderPaint.setStrokeWidth(4.0f * density);
		}
		
		if (dieBackground == null)
			dieBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.diebackground, null);
		
		if (whiteWound == null) {
			whiteWound = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitewound, null);
			blackWound = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackwound, null);
			whiteSurge = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitesurge, null);
			blackSurge = BitmapFactory.decodeResource(context.getResources(), R.drawable.blacksurge, null);
			whiteFail = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitefail, null);
			blackFail = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackfail, null);
			
			woundWidth = whiteWound.getWidth();
			woundHeight = whiteWound.getHeight();
			surgeWidth = whiteSurge.getWidth();
			surgeHeight = whiteSurge.getHeight();
			failWidth = whiteFail.getWidth();
			failHeight = whiteFail.getHeight();
		}
		
		if (woundsMargin <= 0.0f) woundsMargin = 2.0f * density;
		
		setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dieData.isSelected) {
					dieData.isSelected = false;
					dieAdapter.notifyDataSetChanged();
				} else if (dieAdapter.isDieSelectable(dieData)) {
					dieData.isSelected = true;
					dieAdapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint usedBorderPaint;
		if (dieData.isSelected) usedBorderPaint = selectedBorderPaint;
		else usedBorderPaint = borderPaint;
		
		//background color
		canvas.drawColor(dieData.getDieColor());
		//border
		canvas.drawRect(0.0f, 0.0f, dScale, dScale, usedBorderPaint);
		//background
		canvas.drawBitmap(dieBackground, 0.0f, 0.0f, null);
	}
	
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(size, size);
	}
	
	protected void drawRange(Canvas canvas, int range) {
		Paint usedPaint;
		if (dieData.usesBlackIcons()) usedPaint = blackTextPaint;
		else usedPaint = whiteTextPaint;
		canvas.drawText(Integer.toString(range), hPadding, dScale - vPadding - 3.0f * density, usedPaint);
	}
	
	protected void drawWounds(Canvas canvas, int wounds) {
		Bitmap usedBitmap;
		if (dieData.usesBlackIcons()) usedBitmap = blackWound;
		else usedBitmap = whiteWound;
		
		if (wounds >= 1) 
			canvas.drawBitmap(usedBitmap, dScale - hPadding - woundWidth, vPadding, iconPaint);
		if (wounds >= 2)
			canvas.drawBitmap(usedBitmap, dScale - hPadding - woundWidth * 2.0f - woundsMargin, vPadding, iconPaint);
		if (wounds == 3)
			canvas.drawBitmap(usedBitmap, dScale - hPadding - woundWidth * 1.5f - woundsMargin / 2,
					vPadding + woundHeight + woundsMargin, iconPaint);
		if (wounds == 4) {
			canvas.drawBitmap(usedBitmap, dScale - hPadding - woundWidth,
					vPadding + woundHeight + woundsMargin, iconPaint);
			canvas.drawBitmap(usedBitmap, dScale - hPadding - woundWidth * 2.0f - woundsMargin,
					vPadding + woundHeight + woundsMargin, iconPaint);
		}
	}
	
	protected void drawSurge(Canvas canvas) {
		Bitmap usedBitmap;
		if (dieData.usesBlackIcons()) usedBitmap = blackSurge;
		else usedBitmap = whiteSurge;
		
		canvas.drawBitmap(usedBitmap, dScale - 5.0f * density - surgeWidth, dScale - vPadding - surgeHeight, iconPaint);
	}
	
	protected void drawFail(Canvas canvas) {
		Bitmap usedBitmap;
		if (dieData.usesBlackIcons()) usedBitmap = blackFail;
		else usedBitmap = whiteFail;
		
		canvas.drawBitmap(usedBitmap, (dScale - failWidth) / 2.0f, (dScale - failHeight) / 2.0f, iconPaint);
	}
	
	public void setDieData(DieData _dieData) {
		dieData = _dieData;
	}
}
