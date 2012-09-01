package com.dicent;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerListAdapter extends BaseAdapter {
	private DicentState state = DicentState.instance();
	private boolean firstEd = true;
	private PlayerListActivity activity;
	
	private ArrayList<StartDiceSelectionListener> firstEdListeners = new ArrayList<StartDiceSelectionListener>(5);
	private ArrayList<StartDiceSelectionListener> secondEdAttackListeners = new ArrayList<StartDiceSelectionListener>(5);
	private ArrayList<StartDiceSelectionListener> secondEdDefenseListeners = new ArrayList<StartDiceSelectionListener>(5);
	
	public PlayerListAdapter(PlayerListActivity _activity) {
		activity = _activity;
		refreshDescentVersion();
		
		for (int i = 0; i < 5; i++) 
			firstEdListeners.add(new StartDiceSelectionListener(i, DicentActivity.MODE_FIRSTED));
		for (int i = 0; i < 5; i++) 
			secondEdAttackListeners.add(new StartDiceSelectionListener(i, DicentActivity.MODE_SECONDED_ATTACK));
		for (int i = 0; i < 5; i++) 
			secondEdDefenseListeners.add(new StartDiceSelectionListener(i, DicentActivity.MODE_SECONDED_DEFENSE));
	}
	
	@Override
	public int getCount() {
		return state.getPlayers().length;
	}

	@Override
	public Object getItem(int position) {
		return state.getPlayers()[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		int layoutId;
		if (firstEd) layoutId = R.layout.player_first_ed;
		else layoutId = R.layout.player_second_ed;
		LinearLayout layout = (LinearLayout)inflater.inflate(layoutId, null);
		TextView playerName = (TextView)layout.findViewById(R.id.playerName);
		playerName.setText(state.getPlayers()[position]);
		
		if (firstEd) layout.setOnClickListener(firstEdListeners.get(position));
		else {
			layout.findViewById(R.id.attack).setOnClickListener(secondEdAttackListeners.get(position));
			layout.findViewById(R.id.defense).setOnClickListener(secondEdDefenseListeners.get(position));
		}
		
		return layout;
	}
	
	public void preferencesChanged() {
		refreshDescentVersion();
		notifyDataSetChanged();
	}
	
	private void refreshDescentVersion() {
		firstEd = state.getDescentVersion().equals(DicentPreferencesActivity.DESCENT_FIRST_EDITION);
	}
	
	private class StartDiceSelectionListener implements View.OnClickListener {
		private int player;
		private int action;
		
		public StartDiceSelectionListener(int _player, int _action) {
			player = _player;
			action = _action;
		}
		
		@Override
		public void onClick(View v) {
			activity.startDiceSelection(player, action);
		}
	}
}
