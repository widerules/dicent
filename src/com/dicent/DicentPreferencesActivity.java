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

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;

public class DicentPreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	public static final String DESCENT_VERSION = "descentVersion";
	public static final String DESCENT_FIRST_EDITION = "firstEd";
	public static final String DESCENT_SECOND_EDITION = "secondEd";
	public static final String DEFAULT_DESCENT_VERSION = DESCENT_FIRST_EDITION;
	public static final String PLAYERNAMES = "playerNames";
	public static final String RTL_ENABLED = "roadToLegend";
	public static final String TOI_ENABLED = "tombOfIce";
	public static final String VIBRATION_ENABLED = "vibration";
	public static final String SOUNDS_ENABLED = "sounds";
	
	public static final String SECONDED_DICE_GROUPS = "secondEdDiceGroups";
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
				
		firstEdAddons = (PreferenceCategory)findPreference("firstEdAddons");
		
		refreshAddons();
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
	}
	
	private void refreshAddons() {
		String descentVersion = PreferenceManager.getDefaultSharedPreferences(this)
				.getString(DESCENT_VERSION, DEFAULT_DESCENT_VERSION);
		firstEdAddons.setEnabled(descentVersion.equals(DESCENT_FIRST_EDITION));
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(DESCENT_VERSION)) {
			refreshAddons();
		}
		DicentState.instance().preferencesChanged(this);
	}
}
