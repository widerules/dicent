package com.dicent;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;

public class DescentVersionPreference extends ListPreference {
	private DicentPreferencesActivity activity;
	
	public DescentVersionPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setDicentPreferencesActivity(DicentPreferencesActivity _activity) {
		activity = _activity;
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		
		if (positiveResult) activity.descentVersionChanged();
	}
}
