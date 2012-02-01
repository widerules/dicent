package com.dicent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class DicentPreferences extends PreferenceActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		 
		//collect objects created in XML
		Preference reset = (Preference)findPreference("reset"); 
		
		//add listeners
		reset.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				showDialog(DicentActivity.DIALOG_RESET);
				
				return true;
			}
		});
	}
	
	protected Dialog onCreateDialog(int id) {
        Dialog dialog = super.onCreateDialog(id);
        if (dialog != null) return dialog;
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
        case DicentActivity.DIALOG_RESET:
        	builder.setTitle(getResources().getString(R.string.preferencesResetDialog));
        	builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) {
        			resetEverything();
        			onContentChanged();
        		}
            });
        	builder.setNegativeButton(getResources().getString(R.string.cancel), null);
        	dialog = builder.create();
        	break;
        default:
            dialog = null;
        }
        return dialog;
    }
	
	private void resetEverything() {
		//player names
		resetPreference(Dicent.SAVED_PLAYER_NAMES);
		
		//dice selections
		int playersCount = getResources().getStringArray(R.array.players).length;
		for (int i = 0; i < playersCount; i++) {
			resetPreference(SelectDice.SAVED_BASE_DICE_SHARED_PREFERENCE + Integer.toString(i));
			resetPreference(SelectDice.SAVED_RTL_DICE_SHARED_PREFERENCE + Integer.toString(i));
			resetPreference(SelectDice.SAVED_TRANSPARENT_DIE_SHARED_PREFERENCE + Integer.toString(i));
		}
		
		//preferences
		((CheckBoxPreference)findPreference("roadToLegend")).setChecked(false);
		((CheckBoxPreference)findPreference("tombOfIce")).setChecked(false);
		((CheckBoxPreference)findPreference("sounds")).setChecked(true);
		((CheckBoxPreference)findPreference("vibration")).setChecked(true);
	}
	
	private void resetPreference(String preferenceString) {
		SharedPreferences preference = getSharedPreferences(preferenceString, Context.MODE_PRIVATE);
		SharedPreferences.Editor preferenceEditor = preference.edit();
		preferenceEditor.clear();
		preferenceEditor.commit();
	}
}
