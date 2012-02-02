package com.dicent.dice;

import com.dicent.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Die extends View {
	public static final float scale = 70.0f;
	protected static final float textSize = 20.0f;
	protected static Paint borderPaint;
	protected static Paint selectedBorderPaint;
	protected static Paint whiteTextPaint;
	protected static Paint blackTextPaint;
	protected static Paint iconPaint;
	protected static Bitmap dieBackground;
	protected static Bitmap whiteWound;
	protected static Bitmap blackWound;
	protected static Bitmap whiteSurge;
	protected static Bitmap blackSurge;
	protected static Bitmap whiteSeparator;
	protected static Bitmap whiteFail;
	protected static Bitmap blackFail;
	
	protected static float density = 0.0f;
	protected static int size;
	protected static float woundWidth;
	protected static float woundHeight;
	protected static float surgeWidth;
	protected static float surgeHeight;
	protected static float separatorSize;
	protected static float failSize;
	
	protected Context context;
	
	protected DieData dieData;
	
	static {
		borderPaint = new Paint();
		borderPaint.setColor(Color.GRAY);
		borderPaint.setStyle(Paint.Style.STROKE);
		borderPaint.setStrokeWidth(5.0f);
		
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
	
	public Die(Context context, DieData _dieData) {
		super(context);
		
		this.context = context;
		if (density <= 0.0f) {
			density = context.getResources().getDisplayMetrics().density;
			size = (int)(density * scale);
			whiteTextPaint.setTextSize(textSize * density);
			blackTextPaint.setTextSize(textSize * density);
			woundWidth = 14.0f * density;
			woundHeight = 12.0f * density;
			surgeWidth = 35.0f * density;
			surgeHeight = 12.0f * density;
			separatorSize = (size - 30.0f) * density;
			failSize = (size - 30.0f) * density;
		}
		
		if (dieBackground == null) {
			Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.diebackground, null);
			dieBackground = Bitmap.createScaledBitmap(decoded, size, size, true);
			
			decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitewound, null);
			whiteWound = Bitmap.createScaledBitmap(decoded, (int)woundWidth, (int)woundHeight, true);
			
			decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackwound, null);
			blackWound = Bitmap.createScaledBitmap(decoded, (int)woundWidth, (int)woundHeight, true);
			
			decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitesurge, null);
			whiteSurge = Bitmap.createScaledBitmap(decoded, (int)surgeWidth, (int)surgeHeight, true);
			
			decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.blacksurge, null);
			blackSurge = Bitmap.createScaledBitmap(decoded, (int)surgeWidth, (int)surgeHeight, true);
			
			decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.whiteseparator, null);
			whiteSeparator = Bitmap.createScaledBitmap(decoded, (int)separatorSize, (int)separatorSize, true);
			
			decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitefail, null);
			whiteFail = Bitmap.createScaledBitmap(decoded, (int)failSize, (int)failSize, true);
			
			decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackfail, null);
			blackFail = Bitmap.createScaledBitmap(decoded, (int)failSize, (int)failSize, true);
		}
		
		dieData = _dieData;
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint usedBorderPaint;
		if (dieData.isSelected) usedBorderPaint = selectedBorderPaint;
		else usedBorderPaint = borderPaint;
		
		//background color
		canvas.drawColor(dieData.backgroundColor);
		//border
		canvas.drawRect(0.0f, 0.0f, (float)size, (float)size, usedBorderPaint);
		//background
		canvas.drawBitmap(dieBackground, 0.0f, 0.0f, null);
		
		SideValues sv = dieData.getSideValues();
		if (dieData.powerDie) {
			if (sv.enhancement > 0) drawEnhancement(canvas, sv.enhancement);
			else if (sv.surges > 0) drawPowerDieSurges(canvas, sv.surges);
		} else {
			if (sv.fail) drawFail(canvas);
			else {
				drawRange(canvas, sv.range);
				if (sv.wounds > 0) drawWounds(canvas, sv.wounds);
				if (sv.surges > 0) drawSurge(canvas);
			}
		}
	}
	
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(size, size);
	}
	
	protected void drawRange(Canvas canvas, int range) {
		Paint usedPaint;
		if (dieData.blackIcons) usedPaint = blackTextPaint;
		else usedPaint = whiteTextPaint;
		//int margin = size - (int)(10.0f * density);
		canvas.drawText(Integer.toString(range), 10.0f * density, ((float)size - 13.0f) * density, usedPaint);
	}
	
	protected void drawWounds(Canvas canvas, int wounds) {
		Bitmap usedBitmap;
		if (dieData.blackIcons) usedBitmap = blackWound;
		else usedBitmap = whiteWound;
		
		if (wounds >= 1) 
			canvas.drawBitmap(usedBitmap, ((float)size - 10.0f - woundWidth) * density, 12.0f * density, iconPaint);
		if (wounds >= 2)
			canvas.drawBitmap(usedBitmap, ((float)size - 10.0f - woundWidth * 2.0f - 2.0f) * density, 12.0f * density, iconPaint);
		if (wounds == 3)
			canvas.drawBitmap(usedBitmap, ((float)size - 10.0f - woundWidth * 1.5f - 2.0f) * density, (12.0f + woundHeight + 2.0f) * density, iconPaint);
		if (wounds == 4) {
			canvas.drawBitmap(usedBitmap, ((float)size - 10.0f - woundWidth) * density, (12.0f + woundHeight + 2.0f) * density, iconPaint);
			canvas.drawBitmap(usedBitmap, ((float)size - 10.0f - woundWidth * 2.0f - 2.0f) * density, (12.0f + woundHeight + 2.0f) * density, iconPaint);
		}
	}
	
	protected void drawSurge(Canvas canvas) {
		Bitmap usedBitmap;
		if (dieData.blackIcons) usedBitmap = blackSurge;
		else usedBitmap = whiteSurge;
		
		canvas.drawBitmap(usedBitmap, ((float)size - 5.0f - surgeWidth) * density, ((float)size - 10.0f - surgeHeight) * density, iconPaint);
	}
	
	protected void drawPowerDieSurges(Canvas canvas, int surges) {
		Bitmap usedBitmap;
		if (dieData.blackIcons) usedBitmap = blackSurge;
		else usedBitmap = whiteSurge;
		
		if (surges >= 1)
			canvas.drawBitmap(usedBitmap, 10.0f * density, 10.0f * density, iconPaint);
		if (surges >= 2)
			canvas.drawBitmap(usedBitmap, (2.5f + ((float)size - surgeWidth) / 2) * density, (((float)size - surgeHeight) / 2) * density, iconPaint);
		if (surges >= 3) 
			canvas.drawBitmap(usedBitmap, ((float)size - 5.0f - surgeWidth) * density, ((float)size - 10.0f - surgeHeight) * density, iconPaint);
	}
	
	protected void drawEnhancement(Canvas canvas, int enhancement) {
		drawRange(canvas, enhancement);
		drawWounds(canvas, enhancement);
		canvas.drawBitmap(whiteSeparator, 15.0f * density, 15.0f * density, iconPaint);
	}
	
	protected void drawFail(Canvas canvas) {
		Bitmap usedBitmap;
		if (dieData.blackIcons) usedBitmap = blackFail;
		else usedBitmap = whiteFail;
		
		canvas.drawBitmap(usedBitmap, 15.0f * density, 15.0f * density, iconPaint);
	}
	
	public void setDieData(DieData _dieData) {
		dieData = _dieData;
	}
}
