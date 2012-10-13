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

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;

public class DicentState {
	private static DicentState state;
	
	private Storage storage;
	private Vibrator vibrator;
	private MediaPlayer rollSound;
	
	private String descentVersion;
	private boolean rtlEnabled;
	private boolean toiEnabled;
	private boolean vibrationEnabled;
	private boolean rollSoundEnabled;
	
	private DiceList firstEdDice;
	private DiceList secondEdAttackDice;
	private DiceList secondEdDefenseDice;
	private DiceList resultDice = new DiceList();
	
	//experimental
	private boolean attackEnabled = true;
	private boolean defenseEnabled = true;
	
	public static DicentState init(Context context) {
		if (state == null) state = new DicentState(context);
		return state;
	}
	
	public static DicentState instance() {
		return state;
	}
	
	private DicentState(Context context) {
		storage = new Storage(context);
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		rollSound = MediaPlayer.create(context, R.raw.rollsound);
		
		//restore preferences
		restorePreferences(context);
		
		SharedPreferences diceGroupsPref = context.getSharedPreferences(
				DicentPreferencesActivity.SECONDED_DICE_GROUPS, Context.MODE_PRIVATE);
		attackEnabled = diceGroupsPref.getBoolean(DicentPreferencesActivity.ATTACK_ENABLED, true);
		defenseEnabled = diceGroupsPref.getBoolean(DicentPreferencesActivity.DEFENSE_ENABLED, true);
		
		//player die datas
		try {
			firstEdDice = DiceXmlParser.parse(context.getResources(), R.xml.firsted_basedice);
			firstEdDice.addAll(DiceXmlParser.parse(context.getResources(), R.xml.firsted_rtldice));
			firstEdDice.addAll(DiceXmlParser.parse(context.getResources(), R.xml.firsted_toidice));
			secondEdAttackDice = DiceXmlParser.parse(context.getResources(), R.xml.seconded_attackdice);
			secondEdDefenseDice = DiceXmlParser.parse(context.getResources(), R.xml.seconded_defensedice);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		storage.restoreDice(firstEdDice, secondEdAttackDice, secondEdDefenseDice);
	}
	
	public void saveState(Context context) {
		//save dice
		storage.saveDice(firstEdDice, secondEdAttackDice, secondEdDefenseDice);
		
		SharedPreferences diceGroupsPref = context.getSharedPreferences(
				DicentPreferencesActivity.SECONDED_DICE_GROUPS, Context.MODE_PRIVATE);
		SharedPreferences.Editor diceGroupsEditor = diceGroupsPref.edit();
		diceGroupsEditor.putBoolean(DicentPreferencesActivity.ATTACK_ENABLED, isAttackEnabled());
		diceGroupsEditor.putBoolean(DicentPreferencesActivity.DEFENSE_ENABLED, isDefenseEnabled());
		diceGroupsEditor.commit();
		
		//enable for testing
		//state = null;
	}
	
	public void preferencesChanged(Context context) {
		restorePreferences(context);
	}
	
	public void rollEffects() {
		if (vibrationEnabled) vibrator.vibrate(150);
		if (rollSoundEnabled) rollSound.start();
	}
	
	public DiceList getFirstEdDice() {
		return firstEdDice;
	}
	
	public DiceList getSecondEdAttackDice() {
		return secondEdAttackDice;
	}
	
	public DiceList getSecondEdDefenseDice() {
		return secondEdDefenseDice;
	}
	
	public DiceList getResultDice() {
		return resultDice;
	}
	
	public boolean isRtlEnabled() {
		return rtlEnabled;
	}
	
	public boolean isToiEnabled() {
		return toiEnabled;
	}
	
	public String getDescentVersion() {
		return descentVersion;
	}
	
	public boolean isAttackEnabled() {
		return attackEnabled;
	}
	
	public boolean isDefenseEnabled() {
		return defenseEnabled;
	}
	
	public void setAttackEnabled(boolean _attackEnabled) {
		attackEnabled = _attackEnabled;
	}
	
	public void setDefenseEnabled(boolean _defenseEnabled) {
		defenseEnabled = _defenseEnabled;
	}
	
	private void restorePreferences(Context context) {
		SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(context);
		descentVersion = defaultPref.getString(DicentPreferencesActivity.DESCENT_VERSION,
				DicentPreferencesActivity.DEFAULT_DESCENT_VERSION);
		rtlEnabled = defaultPref.getBoolean(DicentPreferencesActivity.RTL_ENABLED, 
				DicentPreferencesActivity.DEFAULT_RTL_ENABLED);
		toiEnabled = defaultPref.getBoolean(DicentPreferencesActivity.TOI_ENABLED, 
				DicentPreferencesActivity.DEFAULT_TOI_ENABLED);
		vibrationEnabled = defaultPref.getBoolean(DicentPreferencesActivity.VIBRATION_ENABLED, 
				DicentPreferencesActivity.DEFAULT_VIBRATION_ENABLED);
		rollSoundEnabled = defaultPref.getBoolean(DicentPreferencesActivity.SOUNDS_ENABLED, 
				DicentPreferencesActivity.DEFAULT_SOUNDS_ENABLED);
	}
}
