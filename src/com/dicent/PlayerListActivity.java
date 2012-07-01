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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class PlayerListActivity extends DicentActivity {
	public static final String FRAGMENT_PLAYERNAME = "playerName";
	private ArrayAdapter<String> playerAdapter;
	
	private ListView playersListView;
	private PlayerNameDialogFragment playerNameDialogFragment;
	
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
    	playersListView.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			Intent selectDiceIntent = new Intent(getBaseContext(), SelectDiceActivity.class);
    			selectDiceIntent.putExtra("playerIndex", position);
    			if (position == 0) selectDiceIntent.putExtra("isOverlord", true);
    			else selectDiceIntent.putExtra("isOverlord", false);
    			startActivity(selectDiceIntent);
    		}
    	});
    	
        playersListView.setOnItemLongClickListener(new OnItemLongClickListener() {
    		public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {
    			playerNameDialogFragment.setPlayerIndex(position);
    			playerNameDialogFragment.setPlayerName(state.getPlayers()[position]);
    			playerNameDialogFragment.show(getSupportFragmentManager(), FRAGMENT_PLAYERNAME);
    			return true;
    		}
    	});
        
        //create dice and adapters
        playerAdapter = new ArrayAdapter<String>(this, R.layout.player_list_item, state.getPlayers());
        playersListView.setAdapter(playerAdapter);
        
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		state.saveState(this);
	}
	
	public ArrayAdapter<String> getPlayerAdapter() {
		return playerAdapter;
	}
}
