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
import com.dicent.dice.DieData;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class PlayerListActivity extends DicentActivity {
	public static final String FRAGMENT_PLAYERNAME = "playerName";
	private PlayerListAdapter playerAdapter;
	
	private ListView playersListView;
	private PlayerNameDialogFragment playerNameDialogFragment;
	private PrefChangedNotifier prefChangedNotifier = new PrefChangedNotifier();
	
	//experimental stuff
	private DiceFragment attackDiceFragment;
	private DiceFragment defenseDiceFragment;
	private CheckBox attackCheckBox;
	private CheckBox defenseCheckBox;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (state.getDescentVersion().equals(DicentPreferencesActivity.DESCENT_SECOND_EDITION_EXP)) {
			setContentView(R.layout.exp_select_dice);
			
			FragmentManager fm = getSupportFragmentManager();
			attackDiceFragment = (DiceFragment)fm.findFragmentByTag("attackDiceGrid");
			attackDiceFragment.setDice(state.getSecondEdAttackDieDatas(1));
			defenseDiceFragment = (DiceFragment)fm.findFragmentByTag("defenseDiceGrid");
			defenseDiceFragment.setDice(state.getSecondEdDefenseDieDatas(1));
			
			attackCheckBox = (CheckBox)findViewById(R.id.attackCheckBox);
			defenseCheckBox = (CheckBox)findViewById(R.id.defenseCheckBox);
			
			attackCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) attackDiceFragment.setVisibility(View.VISIBLE);
					else attackDiceFragment.setVisibility(View.INVISIBLE);
					state.setAttackEnabled(isChecked);
				}
			});
			
			defenseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) defenseDiceFragment.setVisibility(View.VISIBLE);
					else defenseDiceFragment.setVisibility(View.INVISIBLE);
					state.setDefenseEnabled(isChecked);
				}
			});
			
			attackCheckBox.setChecked(state.isAttackEnabled());
			defenseCheckBox.setChecked(state.isDefenseEnabled());
			
		} else {
			setContentView(R.layout.playerlist);
			
			//collect objects created in XML
			playersListView = (ListView)findViewById(R.id.playersListView);
			
			//fragments
			playerNameDialogFragment = (PlayerNameDialogFragment)getSupportFragmentManager().findFragmentByTag(FRAGMENT_PLAYERNAME);
			if (playerNameDialogFragment == null) playerNameDialogFragment = new PlayerNameDialogFragment();
			
			//playerAdapter = new ArrayAdapter<String>(this, R.layout.player_list_item, state.getPlayers());
			playerAdapter = new PlayerListAdapter(this);
			playersListView.setAdapter(playerAdapter);
			
			state.registerPreferencesChangedNotifier(prefChangedNotifier);
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if (!state.isExpNoticeShown()) {
			new ExpNoticeDialogFragment().show(getSupportFragmentManager(), "expNotice");
			state.setExpNoticeShown(true);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		state.unregisterPreferencesChangedNotifier(prefChangedNotifier);
		state.saveState(this);
	}
	
	public PlayerListAdapter getPlayerAdapter() {
		return playerAdapter;
	}
	
	public void startDiceSelection(int player, int mode) {
		Intent selectDiceIntent = new Intent(getBaseContext(), SelectDiceActivity.class);
		selectDiceIntent.putExtra(SelectDiceActivity.INTENTKEY_PLAYERINDEX, player);
		if (player == 0) selectDiceIntent.putExtra(SelectDiceActivity.INTENTKEY_ISOVERLORD, true);
		else selectDiceIntent.putExtra(SelectDiceActivity.INTENTKEY_ISOVERLORD, false);
		selectDiceIntent.putExtra(INTENTKEY_MODE, mode);
		startActivity(selectDiceIntent);
	}
	
	public void renamePlayer(int player) {
		playerNameDialogFragment.setPlayerIndex(player);
		playerNameDialogFragment.setPlayerName(state.getPlayers()[player]);
		playerNameDialogFragment.show(getSupportFragmentManager(), FRAGMENT_PLAYERNAME);
	}
	
	public void roll(View v) {
		state.rollEffects();
		DiceList resultDice = state.getResultDice();
		resultDice.clear();
		if (attackCheckBox.isChecked()) addRolledDice(resultDice, attackDiceFragment.getDice());
		if (defenseCheckBox.isChecked()) addRolledDice(resultDice, defenseDiceFragment.getDice());
		
		Intent resultsIntent = new Intent(getBaseContext(), ResultsActivity.class);
		resultsIntent.putExtra(INTENTKEY_MODE, MODE_EXPERIMENTAL);
		startActivity(resultsIntent);
	}
	
	private void addRolledDice(DiceList base, DiceList add) {
		for (DieData data : add) {
			if (!data.isSelected || !data.isVisible()) continue;
			DieData newData = data.copy();
			newData.isSelected = false;
			newData.roll();
			base.add(newData);
		}
	}
	
	private class PrefChangedNotifier implements PreferencesChangedNotifier {
		@Override
		public void diceChanged() {
			//playerAdapter.preferencesChanged();
			
			//TODO dirty hack!
			//can be replaced when experimental code is properly implemented or removed
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}
	}
}
