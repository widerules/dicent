package com.dicent;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class ResetDialogPreference extends DialogPreference {
	private DicentPreferencesActivity activity;
	
	public ResetDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setDicentPreferencesActivity(DicentPreferencesActivity _activity) {
		activity = _activity;
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		
		if (positiveResult) activity.resetEverything();
	}
}
