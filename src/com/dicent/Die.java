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

import com.dicent.R;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

public abstract class Die extends FrameLayout {
	
	protected static LayoutParams dieLayoutParams;
	protected static float scaleFactor;
	
	public static final int borderColor = 0xFF333333;
	public static final int selectedBorderColor = 0xFF00CCCC;
	public static final float surgeWidth = 35.0f;
	public static final float surgeHeight = 12.0f;
	public static final float woundWidth = 14.0f;
	public static final float woundHeight = 12.0f;
	
	protected Context context;
	protected RelativeLayout dieContent;
	protected boolean useBlack;
	
	protected int side;
	protected int range;
	protected int wounds;
	protected int enhancement;
	protected int surges;
	protected boolean fail;
	protected boolean selected;
	
	public Die(Context context, int _side, boolean _selected) {
		super(context);
		this.context = context;
		
		if (dieLayoutParams == null) {
			scaleFactor = context.getResources().getDisplayMetrics().density;
			dieLayoutParams = new LayoutParams((int)(70.0f * scaleFactor), (int)(70.0f * scaleFactor));
		}
		
		setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		int scaledPadding = (int)(3.0f * scaleFactor);
		setPadding(scaledPadding, scaledPadding, scaledPadding, scaledPadding);
		setBackgroundColor(borderColor);
		
		dieContent = new RelativeLayout(context);
		dieContent.setLayoutParams(dieLayoutParams);
		scaledPadding = (int)(9.0f * scaleFactor);
		dieContent.setPadding(scaledPadding, scaledPadding, scaledPadding, scaledPadding);
		addView(dieContent);
		
		ImageView dieBackground = new ImageView(context);
		dieBackground.setImageResource(R.drawable.diebackground);
		dieBackground.setLayoutParams(dieLayoutParams);
		addView(dieBackground);
		
		side = -1;
		
		setDieSelected(_selected);
	}
	
	public abstract void setSide(int side);
	
	public int getRange() {
		return range;
	}
	
	public int getWounds() {
		return wounds;
	}
	
	public int getEnhancement() {
		return enhancement;
	}
	
	public int getSurges() {
		return surges;
	}
	
	public boolean isFail() {
		return fail;
	}
	
	protected void setRange(int _range) {
		range = _range;
		dieContent.addView(range(range));
	}
	
	protected void setWounds(int _wounds) {
		wounds = _wounds;
		if (wounds > 0) dieContent.addView(wounds(wounds));
	}
	
	protected void setSingleSurge() {
		surges =  1;
		dieContent.addView(surge());
	}
	
	protected void setFail(boolean _fail) {
		fail = _fail;
		if (fail) dieContent.addView(fail());
	}
	
	protected void setDieSelected(boolean _selected) {
		selected = _selected;
		if (selected) setBackgroundColor(selectedBorderColor);
		else setBackgroundColor(borderColor);
	}
	
	protected void reset() {
		dieContent.removeAllViews();
		range = 0;
		wounds = 0;
		enhancement = 0;
		surges = 0;
		fail = false;
	}
	
	protected ImageView surge() {
		ImageView surgeView = new ImageView(context);
		if (useBlack) surgeView.setImageResource(R.drawable.blacksurge);
		else surgeView.setImageResource(R.drawable.whitesurge);
		
		RelativeLayout.LayoutParams surgeLayoutParams = new RelativeLayout.LayoutParams((int)(surgeWidth * scaleFactor), (int)(surgeHeight * scaleFactor));
		surgeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		surgeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		surgeView.setLayoutParams(surgeLayoutParams);
		
		return surgeView;
	}
	
	protected ImageView wound() {
		ImageView woundView = new ImageView(context);
		if (useBlack) woundView.setImageResource(R.drawable.blackwound);
		else woundView.setImageResource(R.drawable.whitewound);
		TableRow.LayoutParams woundParams = new TableRow.LayoutParams((int)(woundWidth * scaleFactor), (int)(woundHeight * scaleFactor));
		int scaledMargin = (int)(1.0f * scaleFactor);
		woundParams.setMargins(scaledMargin, scaledMargin, scaledMargin, scaledMargin);
		woundView.setLayoutParams(woundParams);
		
		return woundView;
	}
	
	protected TableLayout wounds(int woundsNum) {
		TableLayout woundsTable = new TableLayout(context);
		TableRow row1 = new TableRow(context);
		TableRow row2 = new TableRow(context);
		woundsTable.addView(row1);
		woundsTable.addView(row2);
		row1.addView(wound());
		if (woundsNum >= 2) row1.addView(wound());
		if (woundsNum >= 3) {
			View newWound = wound();
			row2.addView(newWound);
			if (woundsNum == 3) ((TableRow.LayoutParams)newWound.getLayoutParams()).span = 2;
		}
		if (woundsNum >= 4) row2.addView(wound());
		
		RelativeLayout.LayoutParams woundsTableLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		woundsTableLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		woundsTableLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		woundsTable.setLayoutParams(woundsTableLayoutParams);
		
		
		return woundsTable;
	}
	
	protected TextView range(int rangeVal) {
		TextView rangeView = new TextView(context);
		rangeView.setSingleLine();
		rangeView.setText(Integer.toString(rangeVal));
		if (useBlack) rangeView.setTextColor(0xFF000000);
		else rangeView.setTextColor(0xFFFFFFFF);
		rangeView.setTextSize(20.0f);
		
		RelativeLayout.LayoutParams rangeLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rangeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rangeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rangeView.setLayoutParams(rangeLayoutParams);
		
		return rangeView;
	}
	
	protected ImageView fail() {
		ImageView failView = new ImageView(context);
		if (useBlack) failView.setImageResource(R.drawable.blackfail);
		else failView.setImageResource(R.drawable.whitefail);
		
		RelativeLayout.LayoutParams failLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int)(50.0f * scaleFactor));
		failLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		failView.setLayoutParams(failLayoutParams);
		
		return failView;
	}
	
	protected ImageView separator() {
		ImageView separatorView = new ImageView(context);
		separatorView.setImageResource(R.drawable.whiteseparator);
		
		RelativeLayout.LayoutParams separatorLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int)(50.0f * scaleFactor));
		separatorLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		separatorView.setLayoutParams(separatorLayoutParams);
		
		return separatorView;
	}
}
