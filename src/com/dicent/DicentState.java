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
import java.util.ArrayList;
import java.util.LinkedList;

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
	
	private String[] defaultPlayers;
	private String[] players;
	private ArrayList<DiceList> firstEdDieDatas = new ArrayList<DiceList>(5);
	private ArrayList<DiceList> secondEdAttackDieDatas = new ArrayList<DiceList>(5);
	private ArrayList<DiceList> secondEdDefenseDieDatas = new ArrayList<DiceList>(5);
	private DiceList resultDice = new DiceList();
	
	private LinkedList<PreferencesChangedNotifier> prefChangedNotifiers = new LinkedList<PreferencesChangedNotifier>();
	
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
		defaultPlayers = context.getResources().getStringArray(R.array.players);
		
		//restore preferences
		restorePreferences(context);
		
		//restore saved player names
		players = new String[defaultPlayers.length];
		SharedPreferences playerNamesPref = context.getSharedPreferences(
				DicentPreferencesActivity.PLAYERNAMES, Context.MODE_PRIVATE);
		for (int i = 0; i < players.length; i++)
			players[i] = playerNamesPref.getString(Integer.toString(i), defaultPlayers[i]);
		
		//player die datas
		//first edition
		DiceList firstEdDice = null;
		DiceList secondEdAttackDice = null;
		DiceList secondEdDefenseDice = null;
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
		
		//overlord
		firstEdDieDatas.add(firstEdDice);
		secondEdAttackDieDatas.add(secondEdAttackDice);
		secondEdDefenseDieDatas.add(secondEdDefenseDice);
		
		//heroes
		for (int i = 1; i <= 4; i++) {
			firstEdDieDatas.add(firstEdDice.copy());
			secondEdAttackDieDatas.add(secondEdAttackDice.copy());
			secondEdDefenseDieDatas.add(secondEdDefenseDice.copy());
		}
		
		//TODO storage.restorePlayesDice(firstEdDieDatas);
		storage.restorePlayesDice(firstEdDieDatas, secondEdAttackDieDatas, secondEdDefenseDieDatas);
	}
	
	public void saveState(Context context) {
		//save player names
		SharedPreferences savedPlayerNames = context.getSharedPreferences(
				DicentPreferencesActivity.PLAYERNAMES, Context.MODE_PRIVATE);
		SharedPreferences.Editor savedPlayerNamesEditor = savedPlayerNames.edit();
		
		for (int i = 0; i < players.length; i++)
			savedPlayerNamesEditor.putString(Integer.toString(i), players[i]);
		savedPlayerNamesEditor.commit();
		
		//save player dice
		storage.savePlayersDice(firstEdDieDatas, secondEdAttackDieDatas, secondEdDefenseDieDatas);
		
		//enable for testing
		//state = null;
	}
	
	public void preferencesChanged(Context context) {
		restorePreferences(context);
		for (PreferencesChangedNotifier notifier : prefChangedNotifiers) notifier.preferencesChanged();
	}
	
	public void registerPreferencesChangedNotifier(PreferencesChangedNotifier notifier) {
		prefChangedNotifiers.add(notifier);
	}
	
	public void unregisterPreferencesChangedNotifier(PreferencesChangedNotifier notifier) {
		for (PreferencesChangedNotifier currentNotifier : prefChangedNotifiers)
			if (currentNotifier == notifier) {
				prefChangedNotifiers.remove(notifier);
				return;
			}
	}
	
	public void rollEffects() {
		if (vibrationEnabled) vibrator.vibrate(150);
		if (rollSoundEnabled) rollSound.start();
	}
	
	public String[] getPlayers() {
		return players;
	}
	
	public String[] getDefaultPlayers() {
		return defaultPlayers;
	}
	
	public DiceList getFirstEdDieDatas(int playerIndex) {
		return firstEdDieDatas.get(playerIndex);
	}
	
	public DiceList getSecondEdAttackDieDatas(int playerIndex) {
		return secondEdAttackDieDatas.get(playerIndex);
	}
	
	public DiceList getSecondEdDefenseDieDatas(int playerIndex) {
		return secondEdDefenseDieDatas.get(playerIndex);
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
