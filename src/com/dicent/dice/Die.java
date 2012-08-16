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
import com.dicent.dice.firstEd.FirstEdDieData;

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
	
	protected static Paint borderPaint;
	protected static Paint selectedBorderPaint;
	protected static Paint whiteTextPaint;
	protected static Paint blackTextPaint;
	protected static Paint iconPaint;
	
	protected static Bitmap dieBackground;
	
	protected static float density = 0.0f;
	protected static int size;
	
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
	
	public Die(Context context, FirstEdDieData _dieData, DieAdapter _dieAdapter) {
		super(context);
		
		dieAdapter = _dieAdapter;
		dieData = _dieData;
		
		if (density <= 0.0f) {
			density = context.getResources().getDisplayMetrics().density;
			size = (int)(density * scale);
			whiteTextPaint.setTextSize(textSize * density);
			blackTextPaint.setTextSize(textSize * density);
			borderPaint.setStrokeWidth(4.0f * density);
			selectedBorderPaint.setStrokeWidth(4.0f * density);
		}
		
		if (dieBackground == null)
			dieBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.diebackground, null);
		
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
		canvas.drawRect(0.0f, 0.0f, (float)size, (float)size, usedBorderPaint);
		//background
		canvas.drawBitmap(dieBackground, 0.0f, 0.0f, null);
	}
	
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(size, size);
	}
}
