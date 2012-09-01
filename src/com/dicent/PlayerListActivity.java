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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class PlayerListActivity extends DicentActivity {
	public static final String FRAGMENT_PLAYERNAME = "playerName";
	private PlayerListAdapter playerAdapter;
	
	private ListView playersListView;
	private PlayerNameDialogFragment playerNameDialogFragment;
	private PrefChangedNotifier prefChangedNotifier = new PrefChangedNotifier();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playerlist);
		
		//collect objects created in XML
		playersListView = (ListView)findViewById(R.id.playersListView);
		
		//fragments
		playerNameDialogFragment = (PlayerNameDialogFragment)getSupportFragmentManager().findFragmentByTag(FRAGMENT_PLAYERNAME);
		if (playerNameDialogFragment == null) playerNameDialogFragment = new PlayerNameDialogFragment();
		
		//set listeners
		playersListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {
				playerNameDialogFragment.setPlayerIndex(position);
				playerNameDialogFragment.setPlayerName(state.getPlayers()[position]);
				playerNameDialogFragment.show(getSupportFragmentManager(), FRAGMENT_PLAYERNAME);
				return true;
			}
		});
		
		//playerAdapter = new ArrayAdapter<String>(this, R.layout.player_list_item, state.getPlayers());
		playerAdapter = new PlayerListAdapter(this);
		playersListView.setAdapter(playerAdapter);
		
		state.registerPreferencesChangedNotifier(prefChangedNotifier);
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
	
	private class PrefChangedNotifier implements PreferencesChangedNotifier {
		@Override
		public void preferencesChanged() {
			playerAdapter.preferencesChanged();
		}
	}
}
