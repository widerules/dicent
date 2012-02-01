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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Dicent extends DicentActivity {
	public static final String SAVED_PLAYER_NAMES = "savedPlayerNames";
	private String[] players;
	private String[] defaultPlayers;
	private ArrayAdapter<String> playerAdapter;
	
	private int currentNameChangePlayerIndex;
	
	private ListView playersListView;
	private EditText changePlayerNameEditText;
	
	private Toast exitToast;
	
	private Intent selectDiceIntent;
	
	private boolean backPressed;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //collect objects created in XML
        players = getResources().getStringArray(R.array.players);
        playersListView = (ListView)findViewById(R.id.playersListView);
        
    	//create stuff
        changePlayerNameEditText = new EditText(this);
        changePlayerNameEditText.setSingleLine();
        selectDiceIntent = new Intent(getBaseContext(), SelectDice.class);
        defaultPlayers = getResources().getStringArray(R.array.players);
        
        exitToast = Toast.makeText(this, getResources().getString(R.string.exitNotification), Toast.LENGTH_SHORT);
    	
    	//set listeners
    	playersListView.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			backPressed = false;
    			selectDiceIntent.putExtra("playerIndex", position);
    			if (position == 0) selectDiceIntent.putExtra("isOverlord", true);
    			else selectDiceIntent.putExtra("isOverlord", false);
    			startActivity(selectDiceIntent);
    		}
    	});
    	
        playersListView.setOnItemLongClickListener(new OnItemLongClickListener() {
    		public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {
    			backPressed = false;
    			currentNameChangePlayerIndex = position;
    			changePlayerNameEditText.setText(players[position]);
    			showDialog(DicentActivity.DIALOG_CHANGE_PLAYER_NAME);
    			return true;
    		}
    	});
        
        //create dice and adapters
        playerAdapter = new ArrayAdapter<String>(this, R.layout.player_list_item, players);
        playersListView.setAdapter(playerAdapter);
        
    }
    
    protected void onStart() {
    	super.onStart();
    	SharedPreferences savedPlayerNames = getSharedPreferences(SAVED_PLAYER_NAMES, Context.MODE_PRIVATE);
    	
    	int playersLength = players.length;
		for (int i = 0; i < playersLength; i++)
			players[i] = savedPlayerNames.getString(Integer.toString(i), defaultPlayers[i]);
		playerAdapter.notifyDataSetChanged();
    }
    
    protected void onStop() {
    	super.onStop();
    	SharedPreferences savedPlayerNames = getSharedPreferences(SAVED_PLAYER_NAMES, Context.MODE_PRIVATE);
		SharedPreferences.Editor savedPlayerNamesEditor = savedPlayerNames.edit();
		
		int playersLength = players.length;
		for (int i = 0; i < playersLength; i++) 
			savedPlayerNamesEditor.putString(Integer.toString(i), players[i]);
		savedPlayerNamesEditor.commit();
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode != KeyEvent.KEYCODE_BACK) return false;
    	if (backPressed) finish();
    	else {
    		backPressed = true;
    		exitToast.show();
    	}
    	return false;
    }
    
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = super.onCreateDialog(id);
        if (dialog != null) return dialog;
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
        case DicentActivity.DIALOG_CHANGE_PLAYER_NAME:
        	builder.setTitle(getResources().getString(R.string.changePlayerNameDialog));
        	builder.setView(changePlayerNameEditText);
        	builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) {
        			players[currentNameChangePlayerIndex] = changePlayerNameEditText.getText().toString();
            		playerAdapter.notifyDataSetChanged();
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
}
