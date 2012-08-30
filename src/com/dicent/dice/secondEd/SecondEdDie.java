package com.dicent.dice.secondEd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.dicent.DieAdapter;
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

	public SecondEdDie(Context context, SecondEdDieData _dieData, DieAdapter _dieAdapter) {
		super(context, _dieData, _dieAdapter);
		
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
