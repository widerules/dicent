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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;

public class DicentPreferencesActivity extends PreferenceActivity {
	public static final String DESCENT_VERSION = "descentVersion";
	public static final String DESCENT_FIRST_EDITION = "firstEd";
	public static final String DESCENT_SECOND_EDITION = "secondEd";
	public static final String DESCENT_SECOND_EDITION_EXP = "secondEdExperimental";
	public static final String DEFAULT_DESCENT_VERSION = DESCENT_FIRST_EDITION;
	public static final String PLAYERNAMES = "playerNames";
	public static final String RTL_ENABLED = "roadToLegend";
	public static final String TOI_ENABLED = "tombOfIce";
	public static final String VIBRATION_ENABLED = "vibration";
	public static final String SOUNDS_ENABLED = "sounds";
	
	public static final String EXPERIMENTAL = "experimental";
	public static final String ATTACK_ENABLED = "attackEnabled";
	public static final String DEFENSE_ENABLED = "defenseEnabled";
	
	public static final boolean DEFAULT_RTL_ENABLED = false;
	public static final boolean DEFAULT_TOI_ENABLED = false;
	public static final boolean DEFAULT_SOUNDS_ENABLED = true;
	public static final boolean DEFAULT_VIBRATION_ENABLED = true;
	
	private PreferenceCategory firstEdAddons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		ResetDialogPreference reset = (ResetDialogPreference)findPreference("reset");
		reset.setDicentPreferencesActivity(this);
		
		DescentVersionPreference descentVersionPreference = (DescentVersionPreference)findPreference("descentVersion");
		descentVersionPreference.setDicentPreferencesActivity(this);
		
		firstEdAddons = (PreferenceCategory)findPreference("firstEdAddons");
		descentVersionChanged();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		DicentState.instance().preferencesChanged(this);
	}
	
	public void resetEverything() {
		//player names
		resetPreference(PLAYERNAMES);
		
		//dice selections
		int playersCount = getResources().getStringArray(R.array.players).length;
		for (int i = 0; i < playersCount; i++) {
			resetPreference(SelectDiceActivity.SAVED_BASE_DICE_SHARED_PREFERENCE + Integer.toString(i));
			resetPreference(SelectDiceActivity.SAVED_RTL_DICE_SHARED_PREFERENCE + Integer.toString(i));
			resetPreference(SelectDiceActivity.SAVED_TRANSPARENT_DIE_SHARED_PREFERENCE + Integer.toString(i));
		}
		
		//preferences
		((ListPreference)findPreference(DESCENT_VERSION)).setValue(DEFAULT_DESCENT_VERSION);
		((CheckBoxPreference)findPreference(RTL_ENABLED)).setChecked(DEFAULT_RTL_ENABLED);
		((CheckBoxPreference)findPreference(TOI_ENABLED)).setChecked(DEFAULT_TOI_ENABLED);
		((CheckBoxPreference)findPreference(SOUNDS_ENABLED)).setChecked(DEFAULT_SOUNDS_ENABLED);
		((CheckBoxPreference)findPreference(VIBRATION_ENABLED)).setChecked(DEFAULT_VIBRATION_ENABLED);
		
		onContentChanged();
	}
	
	public void descentVersionChanged() {
		SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(this);
		String descentVersion = defaultPref.getString(DESCENT_VERSION, DEFAULT_DESCENT_VERSION);
		firstEdAddons.setEnabled(descentVersion.equals(DESCENT_FIRST_EDITION));
	}
	
	private void resetPreference(String preferenceString) {
		SharedPreferences preference = getSharedPreferences(preferenceString, Context.MODE_PRIVATE);
		SharedPreferences.Editor preferenceEditor = preference.edit();
		preferenceEditor.clear();
		preferenceEditor.commit();
	}
}
