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

public class DicentPreferencesActivity extends PreferenceActivity {
	private static final int DIALOG_RESET = 0;
	public static final boolean DEFAULT_RTL_CHECKED = false;
	public static final boolean DEFAULT_TOI_CHECKED = false;
	public static final boolean DEFAULT_SOUNDS_CHECKED = true;
	public static final boolean DEFAULT_VIBRATION_CHECKED = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		//collect objects created in XML
		Preference reset = (Preference)findPreference("reset");
		
		//add listeners
		reset.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				showDialog(DIALOG_RESET);
				
				return true;
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		DicentState.instance().preferencesChanged(this);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
        Dialog dialog = super.onCreateDialog(id);
        if (dialog != null) return dialog;
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
        case DIALOG_RESET:
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
	
	public void resetEverything() {
		//player names
		resetPreference(DicentState.PREFERENCES_PLAYERNAMES);
		
		//dice selections
		int playersCount = getResources().getStringArray(R.array.players).length;
		for (int i = 0; i < playersCount; i++) {
			resetPreference(SelectDiceActivity.SAVED_BASE_DICE_SHARED_PREFERENCE + Integer.toString(i));
			resetPreference(SelectDiceActivity.SAVED_RTL_DICE_SHARED_PREFERENCE + Integer.toString(i));
			resetPreference(SelectDiceActivity.SAVED_TRANSPARENT_DIE_SHARED_PREFERENCE + Integer.toString(i));
		}
		
		//preferences
		((CheckBoxPreference)findPreference("roadToLegend")).setChecked(DEFAULT_RTL_CHECKED);
		((CheckBoxPreference)findPreference("tombOfIce")).setChecked(DEFAULT_TOI_CHECKED);
		((CheckBoxPreference)findPreference("sounds")).setChecked(DEFAULT_SOUNDS_CHECKED);
		((CheckBoxPreference)findPreference("vibration")).setChecked(DEFAULT_VIBRATION_CHECKED);
	}
	
	private void resetPreference(String preferenceString) {
		SharedPreferences preference = getSharedPreferences(preferenceString, Context.MODE_PRIVATE);
		SharedPreferences.Editor preferenceEditor = preference.edit();
		preferenceEditor.clear();
		preferenceEditor.commit();
	}
}