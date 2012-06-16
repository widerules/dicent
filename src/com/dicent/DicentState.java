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

import com.dicent.dice.DieData;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;

public class DicentState {
	public static final String PREFERENCES_PLAYERNAMES = "playerNames";
	public static final String PREFERENCES_RTL_ENABLED = "roadToLegend";
	public static final String PREFERENCES_TOI_ENABLED = "tombOfIce";
	public static final String PREFERENCES_VIBRATION_ENABLED = "vibration";
	public static final String PREFERENCES_ROLLSOUND_ENABLED = "sounds";
	private static DicentState state;
	
	private Storage storage;
	private Vibrator vibrator;
	private MediaPlayer rollSound;
	
	private boolean rtlEnabled;
	private boolean toiEnabled;
	private boolean vibrationEnabled;
	private boolean rollSoundEnabled;
	
	private String[] defaultPlayers;
	private String[] players;
	private ArrayList<DiceList> playerDieDatas = new ArrayList<DiceList>(5);
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
		Log.d(null, "CREATING THEM STATES");
		storage = new Storage(context);
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		rollSound = MediaPlayer.create(context, R.raw.rollsound);
		defaultPlayers = context.getResources().getStringArray(R.array.players);
		
		//restore preferences
		restorePreferences(context);
		
		//restore saved player names
		players = new String[defaultPlayers.length];
		SharedPreferences playerNamesPref = context.getSharedPreferences(
				PREFERENCES_PLAYERNAMES, Context.MODE_PRIVATE);
		for (int i = 0; i < players.length; i++)
			players[i] = playerNamesPref.getString(Integer.toString(i), defaultPlayers[i]);
		
		//player die datas
		DiceList baseList = null;
		try {
			baseList = DiceXmlParser.parse(context.getResources(), R.xml.basedice);
			baseList.addAll(DiceXmlParser.parse(context.getResources(), R.xml.rtldice));
			baseList.addAll(DiceXmlParser.parse(context.getResources(), R.xml.toidice));
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		playerDieDatas.add(baseList); //overlord
		for (int i = 1; i <= 4; i++) playerDieDatas.add(baseList.copy()); //heroes
		storage.restorePlayesDice(playerDieDatas);
		
	}
	
	public void saveState(Context context) {
		//save player names
		SharedPreferences savedPlayerNames = context.getSharedPreferences(
				PREFERENCES_PLAYERNAMES, Context.MODE_PRIVATE);
		SharedPreferences.Editor savedPlayerNamesEditor = savedPlayerNames.edit();
		
		for (int i = 0; i < players.length; i++)
			savedPlayerNamesEditor.putString(Integer.toString(i), players[i]);
		savedPlayerNamesEditor.commit();
		
		//save player dice
		storage.savePlayersDice(playerDieDatas);
		
		//enable for testing
		//state = null;
	}
	
	public void preferencesChanged(Context context) {
		restorePreferences(context);
		for (PreferencesChangedNotifier notifier : prefChangedNotifiers) notifier.diceChanged();
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
	
	public DiceList getPlayerDieDatas(int playerIndex) {
		return playerDieDatas.get(playerIndex);
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
	
	private void restorePreferences(Context context) {
		SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(context);
		rtlEnabled = defaultPref.getBoolean(PREFERENCES_RTL_ENABLED, false);
		toiEnabled = defaultPref.getBoolean(PREFERENCES_TOI_ENABLED, false);
		vibrationEnabled = defaultPref.getBoolean(PREFERENCES_VIBRATION_ENABLED, true);
		rollSoundEnabled = defaultPref.getBoolean(PREFERENCES_ROLLSOUND_ENABLED, true);
	}
}
